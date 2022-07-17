package com.android.shopping_cart_android.presentation.shopping_cart

sealed class ShoppingCartEvent {
    data class ProductQuantityChanged(val index: Int, val quantity: Int) : ShoppingCartEvent()
}