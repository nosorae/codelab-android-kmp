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
package com.example.fruitties.kmptutorial.android.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation

/**
 * 장바구니 아이템 정보를 담는 데이터 클래스 (Room Entity)
 *
 * [추가 설명]
 * - @Entity: Room 데이터베이스의 테이블로 사용됩니다.
 * - foreignKeys: 외래 키 제약 조건을 정의합니다.
 *   - entity = Fruittie::class: 참조할 부모 테이블
 *   - parentColumns/childColumns: 조인할 컬럼들
 *   - onDelete = CASCADE: 부모(Fruittie)가 삭제되면 자식(CartItem)도 자동 삭제됩니다.
 */
@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Fruittie::class,
            parentColumns = ["id"],
            childColumns = ["id"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class CartItem(
    /**
     * 장바구니 아이템의 ID (Fruittie의 ID와 동일)
     *
     * [추가 설명]
     * - @PrimaryKey: 기본 키이면서 동시에 Fruittie 테이블의 외래 키입니다.
     * - 같은 과일은 하나의 장바구니 아이템으로만 존재합니다 (수량으로 관리).
     */
    @PrimaryKey val id: Long,

    /**
     * 장바구니에 담긴 수량
     *
     * [추가 설명]
     * - 기본값 1: 처음 추가될 때는 1개로 시작합니다.
     */
    val count: Int = 1,
)

/**
 * 장바구니 아이템과 과일 정보를 함께 담는 클래스
 *
 * [추가 설명]
 * - Room의 JOIN 결과를 담는 데이터 클래스입니다.
 * - @Relation과 @Embedded를 사용하여 두 테이블의 데이터를 하나로 합칩니다.
 * - 이 클래스는 DB 테이블이 아닙니다 (쿼리 결과를 담는 용도).
 */
data class CartItemWithFruittie(
    /**
     * 과일 상세 정보
     *
     * [추가 설명]
     * - @Relation: Room이 자동으로 Fruittie 데이터를 조인해줍니다.
     * - parentColumn = "id": CartItem의 id 컬럼
     * - entityColumn = "id": Fruittie의 id 컬럼
     */
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
    )
    val fruittie: Fruittie,

    /**
     * 장바구니 아이템 정보 (수량 등)
     *
     * [추가 설명]
     * - @Embedded: CartItem의 모든 필드를 이 클래스에 포함시킵니다.
     * - 결과적으로 id와 count 필드를 직접 접근할 수 있습니다 (cartItem.count).
     */
    @Embedded val cartItem: CartItem,
)
