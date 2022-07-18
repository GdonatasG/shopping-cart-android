package com.android.shopping_cart_android.presentation.shopping_cart

import com.android.shopping_cart_android.domain.Product

data class ShoppingCartState(
    val cartProducts: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val totalSum: Int = 0,
)