package com.android.shopping_cart_android.presentation.product_list

sealed class ProductListEvent {
    object LoadProducts : ProductListEvent()
}