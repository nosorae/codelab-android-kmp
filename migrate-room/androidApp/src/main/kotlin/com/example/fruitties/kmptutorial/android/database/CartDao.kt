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
import androidx.room.Transaction
import androidx.room.Update
import com.example.fruitties.kmptutorial.android.model.CartItem
import com.example.fruitties.kmptutorial.android.model.CartItemWithFruittie
import com.example.fruitties.kmptutorial.android.model.Fruittie
import kotlinx.coroutines.flow.Flow

/**
 * CartItem 테이블에 대한 데이터베이스 작업을 정의하는 DAO
 *
 * [추가 설명]
 * - 장바구니 아이템의 추가, 수정, 조회 기능을 제공합니다.
 */
@Dao
interface CartDao {

    /**
     * 장바구니 아이템을 DB에 삽입합니다
     *
     * [추가 설명]
     * - OnConflictStrategy.IGNORE: 이미 같은 ID가 있으면 삽입을 무시합니다 (에러 발생 안 함).
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cartItem: CartItem)

    /**
     * 기존 장바구니 아이템을 업데이트합니다
     *
     * [추가 설명]
     * - @Update: Room이 자동으로 UPDATE SQL을 생성해줍니다.
     * - Primary Key를 기준으로 해당 행을 찾아 업데이트합니다.
     */
    @Update
    suspend fun update(cartItem: CartItem)

    /**
     * 모든 장바구니 아이템을 과일 정보와 함께 가져옵니다
     *
     * [추가 설명]
     * - @Transaction: 여러 쿼리를 하나의 트랜잭션으로 묶어 데이터 일관성을 보장합니다.
     * - CartItemWithFruittie: CartItem과 Fruittie를 조인한 결과 (장바구니 정보 + 과일 상세정보).
     * - Room이 @Relation 어노테이션을 보고 자동으로 JOIN 쿼리를 생성해줍니다.
     */
    @Transaction
    @Query("SELECT * FROM cartitem")
    fun getAll(): Flow<List<CartItemWithFruittie>>

    /**
     * ID로 장바구니 아이템을 찾습니다
     *
     * [추가 설명]
     * - :id는 함수 파라미터의 id 값으로 자동 바인딩됩니다.
     * - 반환값이 nullable(CartItem?): 해당 ID가 없으면 null을 반환합니다.
     */
    @Query("SELECT * FROM cartitem WHERE id = :id")
    suspend fun findById(id: Long): CartItem?

    @Transaction
    suspend fun insertOrIncreaseCount(fruittie: Fruittie) {
        val cartItemInDb = findById(fruittie.id)
        if (cartItemInDb == null) {
            // 새로운 아이템 - 장바구니에 처음 추가되는 경우
            // [추가 설명] 초기 수량은 CartItem의 기본값인 1로 설정됩니다
            insert(CartItem(fruittie.id))
        } else {
            // 기존 아이템 - 이미 장바구니에 있는 경우
            // [추가 설명] copy()를 사용하여 기존 객체를 복사하고 count만 1 증가시킵니다
            update(cartItemInDb.copy(count = cartItemInDb.count + 1))
        }
    }
}
