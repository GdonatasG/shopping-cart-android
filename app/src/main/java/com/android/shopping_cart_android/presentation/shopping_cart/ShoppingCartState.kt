package com.android.shopping_cart_android.presentation.shopping_cart

import com.android.shopping_cart_android.domain.CartItem

data class ShoppingCartState(
    val cart: List<CartItem> = emptyList(),
    val isLoading: Boolean = false,
    val totalSum: Int = 0,
)