package com.android.shopping_cart_android.presentation.products

sealed class ProductListEvent {
    object LoadProducts : ProductListEvent()
}