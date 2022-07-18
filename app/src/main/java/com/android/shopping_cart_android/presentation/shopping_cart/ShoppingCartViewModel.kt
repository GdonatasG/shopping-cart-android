package com.android.shopping_cart_android.presentation.shopping_cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.shopping_cart_android.domain.Product
import com.android.shopping_cart_android.domain.use_case.CalculateCartProductTotalPriceUseCase
import com.android.shopping_cart_android.domain.use_case.WatchProductsUseCase
import com.android.shopping_cart_android.domain.use_case.RemoveProductFromCartUseCase
import com.android.shopping_cart_android.domain.use_case.UpdateProductQuantityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingCartViewModel @Inject constructor(
    private val getCartUseCase: WatchProductsUseCase,
    private val updateProductQuantityUseCase: UpdateProductQuantityUseCase,
    private val removeProductFromCartUseCase: RemoveProductFromCartUseCase,
    private val calculateCartProductTotalPriceUseCase: CalculateCartProductTotalPriceUseCase,
) : ViewModel() {
    private var _state by mutableStateOf(ShoppingCartState())
    val state: ShoppingCartState
        get() = _state

    init {
        viewModelScope.launch {
            _state = _state.copy(isLoading = true)
            getCartUseCase().collectLatest { products ->
                val withCalculatedPrices: List<Product> = calculatePrices(cartProducts = products)
                val totalSum: Int = calculateTotalBasketSum(cartProducts = withCalculatedPrices)
                _state = _state.copy(cartProducts = withCalculatedPrices, isLoading = false, totalSum = totalSum)
            }
        }
    }

    private fun calculatePrices(cartProducts: List<Product>): List<Product> {
        return cartProducts.map { product ->
            product.copy(
                totalPrice = calculateCartProductTotalPriceUseCase(cartProduct = product)
            )

        }
    }

    private fun calculateTotalBasketSum(cartProducts: List<Product>): Int {
        var totalSum = 0

        cartProducts.forEach { cartProduct ->
            totalSum += calculateCartProductTotalPriceUseCase(cartProduct = cartProduct)
        }

        return totalSum
    }

    fun onEvent(event: ShoppingCartEvent) {
        when (event) {
            is ShoppingCartEvent.ProductQuantityChanged -> {
                viewModelScope.launch {
                    val product: Product = state.cartProducts[event.index]
                    updateProductQuantityUseCase(productId = product.productId, quantity = event.quantity)
                }

            }
            is ShoppingCartEvent.ProductRemoved -> {
                viewModelScope.launch {
                    val product: Product = state.cartProducts[event.index]
                    removeProductFromCartUseCase(productId = product.productId)
                }
            }
        }
    }

}