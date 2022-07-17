package com.android.shopping_cart_android.presentation.shopping_cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.shopping_cart_android.domain.CartItem
import com.android.shopping_cart_android.domain.use_case.CalculateCartItemPriceUseCase
import com.android.shopping_cart_android.domain.use_case.GetCartUseCase
import com.android.shopping_cart_android.domain.use_case.RemoveCartItemUseCase
import com.android.shopping_cart_android.domain.use_case.UpdateOrInsertCartItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingCartViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val updateOrInsertCartItemUseCase: UpdateOrInsertCartItemUseCase,
    private val removeCartItemUseCase: RemoveCartItemUseCase,
    private val calculateCartItemPriceUseCase: CalculateCartItemPriceUseCase,
) : ViewModel() {
    private var _state by mutableStateOf(ShoppingCartState())
    val state: ShoppingCartState
        get() = _state

    init {
        viewModelScope.launch {
            _state = _state.copy(isLoading = true)
            getCartUseCase().collectLatest {
                val withCalculatedPrices: List<CartItem> = calculatePrices(cart = it)
                val totalSum: Int = calculateTotalBasketSum(cart = withCalculatedPrices)
                _state = _state.copy(cart = withCalculatedPrices, isLoading = false, totalSum = totalSum)
            }
        }
    }

    private fun calculatePrices(cart: List<CartItem>): List<CartItem> {
        return cart.map { cartItem ->
            cartItem.copy(
                totalPrice = calculateCartItemPriceUseCase(cartItem = cartItem)
            )

        }
    }

    private fun calculateTotalBasketSum(cart: List<CartItem>): Int {
        var totalSum = 0

        cart.forEach { cartItem ->
            totalSum += calculateCartItemPriceUseCase(cartItem = cartItem)
        }

        return totalSum
    }

    fun onEvent(event: ShoppingCartEvent) {
        when (event) {
            is ShoppingCartEvent.ProductQuantityChanged -> {
                viewModelScope.launch {
                    val updatedCartItem: CartItem = state.cart[event.index].copy(quantity = event.quantity)
                    updateOrInsertCartItemUseCase(cartItem = updatedCartItem)
                }

            }
            is ShoppingCartEvent.ProductRemoved -> {
                viewModelScope.launch {
                    removeCartItemUseCase(cartItem = state.cart[event.index])
                }
            }
        }
    }

}