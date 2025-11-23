/*
 * Copyright 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.fruitties.kmptutorial.android.repository

import com.example.fruitties.kmptutorial.android.database.FruittieDao
import com.example.fruitties.kmptutorial.android.model.Fruittie
import com.example.fruitties.kmptutorial.android.network.FruittieApi
import com.example.fruitties.kmptutorial.android.network.toModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * 과일(Fruittie) 데이터를 관리하는 Repository 인터페이스
 *
 * [추가 설명]
 * - Repository 패턴: 데이터 소스(네트워크, DB 등)를 추상화하여 ViewModel이 데이터의 출처를 몰라도 되게 합니다.
 * - 이를 통해 테스트가 쉬워지고, 데이터 소스 변경 시 ViewModel 수정이 필요 없습니다.
 */
interface FruittieRepository {
    fun getData(): Flow<List<Fruittie>>
}

/**
 * FruittieRepository의 기본 구현체
 *
 * [추가 설명]
 * - @Inject: Hilt가 이 클래스의 인스턴스를 자동으로 생성하고 주입해줍니다.
 * - api: 서버에서 데이터를 가져오는 네트워크 클라이언트
 * - fruittieDao: 로컬 DB(Room)에 접근하는 DAO(Data Access Object)
 * - scope: 코루틴을 실행할 범위를 지정 (주로 앱이 살아있는 동안 유지됨)
 */
class DefaultFruittieRepository @Inject constructor(
    private val api: FruittieApi,
    private val fruittieDao: FruittieDao,
    private val scope: CoroutineScope,
) : FruittieRepository {
    /**
     * 과일 데이터를 가져옵니다 (로컬 DB 우선, 비어있으면 서버에서 가져옴)
     *
     * [추가 설명]
     * - Flow: 데이터 스트림으로, DB가 업데이트되면 자동으로 UI에 반영됩니다.
     * - 오프라인 우선 전략: 로컬 DB를 먼저 반환하고, 백그라운드에서 서버 데이터로 갱신합니다.
     */
    override fun getData(): Flow<List<Fruittie>> {
        // [추가 설명] 별도의 코루틴에서 DB가 비어있으면 서버 데이터로 채웁니다
        scope.launch {
            if (fruittieDao.count() < 1) {
                refreshData()
            }
        }
        // [추가 설명] 즉시 로컬 DB 데이터를 반환 (Flow이므로 DB 변경사항을 계속 감지)
        return loadData()
    }

    /**
     * 로컬 DB에서 모든 과일 데이터를 가져옵니다
     *
     * [추가 설명]
     * - Flow를 반환하므로 DB가 변경될 때마다 새로운 데이터가 자동으로 전달됩니다.
     */
    private fun loadData(): Flow<List<Fruittie>> = fruittieDao.getAll()

    /**
     * 서버에서 데이터를 가져와 로컬 DB에 저장합니다
     *
     * [추가 설명]
     * - suspend 함수: 코루틴 내에서만 호출 가능하며, 네트워크 작업을 메인 스레드를 막지 않고 실행합니다.
     * - map { it.toModel() }: 서버 응답 형식을 앱에서 사용하는 모델로 변환합니다.
     */
    private suspend fun refreshData() {
        val response = api.getData()
        fruittieDao.insert(response.feed.map { it.toModel() })
    }
}
