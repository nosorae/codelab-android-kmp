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

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * 코루틴 스코프를 제공하는 Hilt 모듈
 *
 * [추가 설명]
 * - 앱 전체에서 사용할 수 있는 코루틴 스코프를 제공합니다.
 * - Repository 등에서 백그라운드 작업을 실행할 때 사용됩니다.
 */
@Module
@InstallIn(SingletonComponent::class)
internal object CoroutineScopeModule {
    /**
     * 앱 레벨의 CoroutineScope를 제공합니다
     *
     * [추가 설명]
     * - CoroutineScope: 코루틴의 생명주기를 관리하는 범위
     * - Dispatchers.Default: CPU 집약적인 작업에 최적화된 스레드 풀 (일반적인 백그라운드 작업용)
     * - SupervisorJob(): 자식 코루틴 중 하나가 실패해도 다른 코루틴들은 계속 실행됩니다.
     *   (일반 Job은 자식 하나가 실패하면 모든 자식이 취소됨)
     * - '+' 연산자: CoroutineContext 요소들을 결합합니다.
     */
    @Provides
    @Singleton
    fun providesCoroutineScope(): CoroutineScope =
        CoroutineScope(Dispatchers.Default + SupervisorJob())
}
