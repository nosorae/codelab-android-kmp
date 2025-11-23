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

import com.example.fruitties.kmptutorial.android.database.CartDao
import com.example.fruitties.kmptutorial.android.model.CartItemWithFruittie
import com.example.fruitties.kmptutorial.android.model.Fruittie
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

/**
 * 장바구니 데이터를 관리하는 Repository 인터페이스
 *
 * [추가 설명]
 * - ViewModel과 데이터 소스(DB) 사이의 중재자 역할을 합니다.
 */
interface CartRepository {
    /**
     * 장바구니 아이템 목록 (실시간 업데이트)
     *
     * [추가 설명]
     * - Flow로 제공되어 DB 변경사항을 실시간으로 UI에 반영합니다.
     */
    val cartData: Flow<List<CartItemWithFruittie>>

    /**
     * 장바구니에 과일을 추가합니다
     *
     * [추가 설명]
     * - 이미 있는 과일이면 수량만 증가시킵니다.
     */
    suspend fun addToCart(fruittie: Fruittie)
}

/**
 * CartRepository의 기본 구현체
 *
 * [추가 설명]
 * - @Inject: Hilt가 자동으로 CartDao를 주입해줍니다.
 * - 실제 DB 작업은 CartDao에 위임합니다 (Repository 패턴).
 */
class DefaultCartRepository @Inject constructor(private val cartDao: CartDao) : CartRepository {
    /**
     * [추가 설명]
     * - CartDao.getAll()을 그대로 노출합니다.
     * - 필요하다면 여기서 데이터 변환이나 추가 로직을 수행할 수 있습니다.
     */
    override val cartData: Flow<List<CartItemWithFruittie>> = cartDao.getAll()

    /**
     * [추가 설명]
     * - insertOrIncreaseCount는 내부적으로 @Transaction을 사용하여 안전하게 처리됩니다.
     */
    override suspend fun addToCart(fruittie: Fruittie) = cartDao.insertOrIncreaseCount(fruittie)
}
