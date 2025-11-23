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
package com.example.fruitties.kmptutorial.android.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fruitties.kmptutorial.android.model.CartItemWithFruittie
import com.example.fruitties.kmptutorial.android.model.Fruittie
import com.example.fruitties.kmptutorial.android.repository.CartRepository
import com.example.fruitties.kmptutorial.android.repository.FruittieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * 메인 화면의 ViewModel
 *
 * [추가 설명]
 * - @HiltViewModel: Hilt가 이 ViewModel을 자동으로 생성하고 필요한 의존성을 주입해줍니다.
 * - ViewModel: 화면 회전 등 설정 변경 시에도 데이터가 유지되는 안드로이드 아키텍처 컴포넌트입니다.
 * - @Inject: Hilt가 Repository들을 자동으로 주입해줍니다.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    fruittieRepository: FruittieRepository,
) : ViewModel() {

    /**
     * 과일 목록의 UI 상태를 관리하는 StateFlow
     *
     * [추가 설명]
     * - StateFlow: 항상 최신 값을 가지고 있는 상태 홀더로, Compose UI가 이 값을 구독합니다.
     * - map: Repository의 데이터를 UI 상태로 변환합니다.
     * - stateIn: Flow를 StateFlow로 변환합니다.
     * - WhileSubscribed(5000): 구독자가 없어진 후 5초 동안 데이터를 유지합니다 (화면 전환 시 데이터 유지).
     */
    val uiState: StateFlow<HomeUiState> =
        fruittieRepository.getData().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState(),
            )

    /**
     * 장바구니의 UI 상태를 관리하는 StateFlow
     *
     * [추가 설명]
     * - cartData는 Room DB를 실시간으로 관찰하므로, DB가 변경되면 자동으로 UI가 업데이트됩니다.
     */
    val cartUiState: StateFlow<CartUiState> =
        cartRepository.cartData.map { CartUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = CartUiState(),
            )

    /**
     * 장바구니에 과일을 추가합니다
     *
     * [추가 설명]
     * - viewModelScope.launch: ViewModel의 생명주기에 맞춰 코루틴을 실행합니다 (ViewModel이 제거되면 자동 취소).
     * - Repository를 통해 DB에 데이터를 추가하면, cartData Flow가 자동으로 변경을 감지하고 UI를 업데이트합니다.
     */
    fun addItemToCart(fruittie: Fruittie) {
        viewModelScope.launch {
            cartRepository.addToCart(fruittie)
        }
    }
}

/**
 * ListScreen의 UI 상태
 *
 * [추가 설명]
 * - Compose UI에서 화면에 표시할 과일 목록(Fruittie)을 담고 있는 데이터 클래스입니다.
 * - ViewModel에서 관리되며, StateFlow를 통해 UI에 전달됩니다.
 * - itemList가 비어있으면 빈 리스트가 표시됩니다.
 */
data class HomeUiState(val itemList: List<Fruittie> = listOf())

/**
 * 장바구니(Cart)의 UI 상태
 *
 * [추가 설명]
 * - 장바구니에 담긴 아이템들의 정보를 담고 있는 데이터 클래스입니다.
 * - CartItemWithFruittie는 장바구니 아이템(수량)과 과일 정보를 함께 담고 있습니다.
 * - Room의 @Relation을 사용하여 Fruittie와 CartItem을 조인한 결과입니다.
 */
data class CartUiState(val itemList: List<CartItemWithFruittie> = listOf())

private const val TIMEOUT_MILLIS = 5_000L
