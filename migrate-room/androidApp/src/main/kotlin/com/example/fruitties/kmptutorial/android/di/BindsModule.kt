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

import com.example.fruitties.kmptutorial.android.network.FruittieApi
import com.example.fruitties.kmptutorial.android.network.FruittieNetworkApi
import com.example.fruitties.kmptutorial.android.repository.CartRepository
import com.example.fruitties.kmptutorial.android.repository.DefaultCartRepository
import com.example.fruitties.kmptutorial.android.repository.DefaultFruittieRepository
import com.example.fruitties.kmptutorial.android.repository.FruittieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Hilt 의존성 주입을 위한 바인딩 모듈
 *
 * [추가 설명]
 * - @Module: Hilt에게 이것이 의존성 제공 모듈임을 알려줍니다.
 * - @InstallIn(SingletonComponent::class): 앱 전체 생명주기 동안 싱글톤으로 유지됩니다.
 * - interface: @Binds는 인터페이스에서만 사용 가능합니다 (추상 메서드 필요).
 * - @Binds vs @Provides: @Binds는 단순 인터페이스-구현체 연결에 사용되며, 더 효율적입니다.
 */
@Module
@InstallIn(SingletonComponent::class)
internal interface BindsModule {
    /**
     * CartRepository 인터페이스에 DefaultCartRepository 구현체를 바인딩합니다
     *
     * [추가 설명]
     * - @Binds: Hilt에게 CartRepository가 필요할 때 DefaultCartRepository를 주입하라고 알려줍니다.
     * - 이를 통해 ViewModel은 인터페이스에만 의존하게 되어 테스트가 쉬워집니다.
     */
    @Binds
    fun bindsCartRepository(defaultCartRepository: DefaultCartRepository): CartRepository

    /**
     * FruittieRepository 인터페이스에 DefaultFruittieRepository 구현체를 바인딩합니다
     */
    @Binds
    fun bindsFruittieRepository(
        defaultFruittieRepository: DefaultFruittieRepository,
    ): FruittieRepository

    /**
     * FruittieApi 인터페이스에 FruittieNetworkApi 구현체를 바인딩합니다
     *
     * [추가 설명]
     * - 네트워크 레이어도 인터페이스로 추상화하여 테스트 시 Mock 객체로 교체할 수 있습니다.
     */
    @Binds
    fun bindsFruittieApi(fruittieNetworkApi: FruittieNetworkApi): FruittieApi
}
