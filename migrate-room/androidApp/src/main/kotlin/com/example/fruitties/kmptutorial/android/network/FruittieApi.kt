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
package com.example.fruitties.kmptutorial.android.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

/**
 * [추가 설명]
 * - 과일 데이터를 제공하는 서버의 기본 URL
 * - private const: 이 파일 내에서만 사용 가능한 상수 (컴파일 타임에 인라인됨)
 */
private const val BASE_URL = "https://android.github.io/kotlin-multiplatform-samples/fruitties-api"

/**
 * 과일 데이터를 가져오는 API 인터페이스
 *
 * [추가 설명]
 * - 네트워크 작업을 추상화한 인터페이스
 * - 테스트 시 Fake 구현체로 교체 가능
 */
interface FruittieApi {
    /**
     * 서버에서 과일 데이터를 가져옵니다
     *
     * [추가 설명]
     * - suspend: 네트워크 작업이므로 코루틴에서 비동기로 실행됩니다.
     * - pageNumber: 페이지네이션을 위한 페이지 번호 (기본값 0)
     */
    suspend fun getData(pageNumber: Int = 0): FruittiesResponse
}

/**
 * FruittieApi의 실제 네트워크 구현체
 *
 * [추가 설명]
 * - Ktor HttpClient를 사용하여 실제 HTTP 요청을 수행합니다.
 * - @Inject: Hilt가 HttpClient를 자동으로 주입해줍니다.
 */
class FruittieNetworkApi @Inject constructor(private val client: HttpClient) : FruittieApi {

    /**
     * [추가 설명]
     * - URL 조합: BASE_URL + 페이지번호 + ".json" (예: .../0.json)
     * - try-catch: 네트워크 에러를 처리합니다.
     * - CancellationException: 코루틴 취소는 에러가 아니므로 다시 throw (중요!)
     * - 에러 발생 시 빈 응답을 반환하여 앱이 크래시되지 않도록 합니다.
     */
    override suspend fun getData(pageNumber: Int): FruittiesResponse {
        val url = "$BASE_URL/$pageNumber.json"
        return try {
            // [추가 설명] HTTP GET 요청을 보내고 응답 본문을 FruittiesResponse로 자동 변환
            client.get(url).body()
        } catch (e: Exception) {
            // [추가 설명] 코루틴이 취소된 경우 예외를 다시 던져야 코루틴 취소가 제대로 작동합니다
            if (e is CancellationException) throw e
            e.printStackTrace()

            // [추가 설명] 네트워크 에러 시 빈 응답 반환 (앱이 크래시되지 않음)
            FruittiesResponse(emptyList(), 0, 0)
        }
    }
}
