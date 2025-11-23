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
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Singleton
import kotlinx.serialization.json.Json

/**
 * 네트워크 관련 의존성을 제공하는 Hilt 모듈
 *
 * [추가 설명]
 * - object: 싱글톤 객체로 선언 (@Provides 메서드를 사용할 때 적합).
 * - 네트워크 클라이언트와 JSON 직렬화 설정을 제공합니다.
 */
@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    /**
     * JSON 직렬화/역직렬화를 위한 Json 인스턴스를 제공합니다
     *
     * [추가 설명]
     * - @Provides: Hilt에게 이 메서드가 의존성을 제공한다고 알려줍니다.
     * - @Singleton: 앱 전체에서 하나의 인스턴스만 생성됩니다.
     * - ignoreUnknownKeys = true: 서버 응답에 앱이 모르는 필드가 있어도 에러가 발생하지 않습니다.
     *   (서버 API가 업데이트되어도 앱이 깨지지 않음)
     */
    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    /**
     * HTTP 네트워크 요청을 위한 Ktor HttpClient를 제공합니다
     *
     * [추가 설명]
     * - Ktor: Kotlin 멀티플랫폼 HTTP 클라이언트 라이브러리
     * - ContentNegotiation: HTTP 요청/응답의 콘텐츠를 자동으로 직렬화/역직렬화합니다.
     * - json(json, contentType = ContentType.Any): 모든 응답을 JSON으로 파싱합니다.
     * - json 파라미터: Hilt가 위의 providesNetworkJson()에서 제공한 Json 인스턴스를 자동 주입합니다.
     */
    @Singleton
    @Provides
    fun provideHttpClient(json: Json): HttpClient = HttpClient {
        install(ContentNegotiation) {
            json(json, contentType = ContentType.Any)
        }
    }
}
