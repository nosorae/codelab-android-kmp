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

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * 과일 정보를 담는 데이터 클래스 (Room Entity)
 *
 * [추가 설명]
 * - @Entity: Room에게 이 클래스가 데이터베이스 테이블임을 알려줍니다.
 * - indices: 인덱스를 생성하여 검색 성능을 향상시킵니다.
 * - Index(value = ["id"], unique = true): id 컬럼에 유니크 인덱스를 생성합니다 (중복 방지).
 * - data class: 코틀린의 데이터 클래스로, equals, hashCode, toString 등을 자동 생성합니다.
 */
@Entity(indices = [Index(value = ["id"], unique = true)])
data class Fruittie(
    /**
     * 과일의 고유 ID
     *
     * [추가 설명]
     * - @PrimaryKey: 이 필드가 테이블의 기본 키임을 나타냅니다.
     * - autoGenerate = true: DB가 자동으로 ID를 생성해줍니다 (1, 2, 3, ...).
     * - 기본값 0: 새 아이템 삽입 시 DB가 자동으로 ID를 할당합니다.
     */
    @PrimaryKey(autoGenerate = true) val id: Long = 0,

    /** 과일의 짧은 이름 (예: "Apple") */
    val name: String,

    /** 과일의 전체 학명 (예: "Malus domestica") */
    val fullName: String,

    /** 칼로리 정보 (문자열 형태) */
    val calories: String,
)
