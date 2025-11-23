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

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fruitties.kmptutorial.android.model.CartItem
import com.example.fruitties.kmptutorial.android.model.Fruittie

/**
 * 앱의 Room 데이터베이스 클래스
 *
 * [추가 설명]
 * - @Database: Room 라이브러리에게 이것이 데이터베이스 클래스임을 알려줍니다.
 * - entities: 이 DB에 포함될 테이블들 (각 Entity 클래스가 하나의 테이블이 됩니다).
 * - version: DB 스키마 버전 (스키마 변경 시 증가시켜야 합니다).
 * - RoomDatabase: Room의 기본 데이터베이스 클래스를 상속받습니다.
 * - abstract class: Room이 컴파일 타임에 실제 구현체를 자동 생성해줍니다.
 */
@Database(
    entities = [Fruittie::class, CartItem::class],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    /**
     * Fruittie 테이블에 접근하는 DAO를 반환합니다
     *
     * [추가 설명]
     * - DAO (Data Access Object): 데이터베이스 작업을 수행하는 메서드들을 정의한 인터페이스입니다.
     */
    abstract fun fruittieDao(): FruittieDao

    /**
     * CartItem 테이블에 접근하는 DAO를 반환합니다
     */
    abstract fun cartDao(): CartDao
}
