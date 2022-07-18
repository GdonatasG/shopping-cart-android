package com.android.shopping_cart_android.presentation.product_details

sealed class ProductDetailsEvent {
    data class QuantityChanged(val quantity: Int) : ProductDetailsEvent()
}