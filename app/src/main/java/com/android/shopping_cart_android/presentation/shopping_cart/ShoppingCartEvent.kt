package com.android.shopping_cart_android.presentation.shopping_cart

import com.android.shopping_cart_android.domain.CartItem

sealed class ShoppingCartEvent {
    data class ProductQuantityChanged(val cartItem: CartItem) : ShoppingCartEvent()
}