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
package com.example.fruitties.kmptutorial.android.di

import android.content.Context
import androidx.room.Room
import com.example.fruitties.kmptutorial.android.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Room 데이터베이스 관련 의존성을 제공하는 Hilt 모듈
 *
 * [추가 설명]
 * - 데이터베이스와 DAO 인스턴스를 생성하고 제공합니다.
 */
@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    /**
     * Room 데이터베이스 인스턴스를 제공합니다
     *
     * [추가 설명]
     * - @ApplicationContext: Hilt가 앱의 Application Context를 자동으로 주입해줍니다.
     * - Room.databaseBuilder: Room 데이터베이스를 생성하는 빌더
     *   - context: 데이터베이스 파일을 저장할 위치를 결정하는데 필요
     *   - AppDatabase::class.java: 생성할 데이터베이스 클래스
     *   - "sharedfruits.db": 디스크에 저장될 DB 파일 이름
     * - .build(): 실제 데이터베이스 인스턴스를 생성합니다.
     */
    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase = Room
        .databaseBuilder(context, AppDatabase::class.java, "sharedfruits.db")
        .build()

    /**
     * FruittieDao 인스턴스를 제공합니다
     *
     * [추가 설명]
     * - appDatabase 파라미터: Hilt가 위의 providesAppDatabase()에서 제공한 DB를 자동 주입
     * - @Singleton이 없음: DAO는 필요할 때마다 새로 생성됩니다 (가볍기 때문에 문제 없음)
     */
    @Provides
    fun providesFruittieDao(appDatabase: AppDatabase) = appDatabase.fruittieDao()

    /**
     * CartDao 인스턴스를 제공합니다
     */
    @Provides
    fun providesCartDao(appDatabase: AppDatabase) = appDatabase.cartDao()
}
