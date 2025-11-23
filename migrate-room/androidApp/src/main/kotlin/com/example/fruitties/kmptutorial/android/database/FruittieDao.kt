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
package com.example.fruitties.kmptutorial.android.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fruitties.kmptutorial.android.model.Fruittie
import kotlinx.coroutines.flow.Flow

/**
 * Fruittie 테이블에 대한 데이터베이스 작업을 정의하는 DAO
 *
 * [추가 설명]
 * - @Dao: Room에게 이것이 Database Access Object임을 알려줍니다.
 * - Room이 이 인터페이스의 구현체를 자동으로 생성해줍니다.
 */
@Dao
interface FruittieDao {

    /**
     * 과일 목록을 DB에 삽입합니다
     *
     * [추가 설명]
     * - @Insert: Room이 자동으로 INSERT SQL을 생성해줍니다.
     * - OnConflictStrategy.REPLACE: 이미 같은 ID가 있으면 기존 데이터를 새 데이터로 교체합니다.
     * - suspend: 코루틴 함수로, 메인 스레드를 블록하지 않고 DB 작업을 수행합니다.
     * - 반환값: 삽입된 각 행의 ID 리스트
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(fruitties: List<Fruittie>): List<Long>

    /**
     * 모든 과일 데이터를 가져옵니다
     *
     * [추가 설명]
     * - @Query: 직접 SQL 쿼리를 작성할 수 있습니다.
     * - Flow 반환: DB 변경사항을 실시간으로 관찰합니다 (DB가 업데이트되면 자동으로 새 데이터 방출).
     * - suspend가 아님: Flow는 이미 비동기이므로 suspend 키워드가 필요 없습니다.
     */
    @Query("SELECT * FROM Fruittie")
    fun getAll(): Flow<List<Fruittie>>

    /**
     * Fruittie 테이블의 행 개수를 반환합니다
     *
     * [추가 설명]
     * - 서버에서 데이터를 가져올지 결정하기 위해 사용됩니다 (테이블이 비어있는지 확인).
     */
    @Query("SELECT COUNT(*) as count FROM Fruittie")
    suspend fun count(): Int
}
