package com.android.shopping_cart_android.presentation.shopping_cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.shopping_cart_android.domain.CartItem
import com.android.shopping_cart_android.domain.use_case.GetCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingCartViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
) : ViewModel() {
    private var _state by mutableStateOf(ShoppingCartState())
    val state: ShoppingCartState
        get() = _state

    init {
        viewModelScope.launch {
            _state = _state.copy(isLoading = true)
            getCartUseCase().collectLatest {
                println(it.toString())
                val withCalculatedPrices: List<CartItem> = calculatePrices(cart = it)
                val totalSum: Int = calculateTotalBasketSum(cart = withCalculatedPrices)
                _state = _state.copy(cart = withCalculatedPrices, isLoading = false, totalSum = totalSum)
            }
        }
    }

    private fun calculatePrices(cart: List<CartItem>): List<CartItem> {
        return cart.map { cartItem ->
            cartItem.copy(
                totalPrice = (cartItem.product.costPrice ?: cartItem.product.retailPrice).times(cartItem.quantity)
            )

        }
    }

    private fun calculateTotalBasketSum(cart: List<CartItem>): Int {
        var totalSum = 0

        cart.forEach { cartItem ->
            totalSum += ((cartItem.product.costPrice ?: cartItem.product.retailPrice).times(cartItem.quantity))
        }

        return totalSum
    }

    fun onEvent(event: ShoppingCartEvent) {
        when (event) {
            is ShoppingCartEvent.ProductQuantityChanged -> {
                // TODO: add implementation
            }
        }
    }

}