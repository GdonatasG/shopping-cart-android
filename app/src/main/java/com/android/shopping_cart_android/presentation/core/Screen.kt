package com.android.shopping_cart_android.presentation.core

sealed class Screen(val route: String) {
    object ShoppingCart: Screen("shoppingCart")
    object Products: Screen("products")
}