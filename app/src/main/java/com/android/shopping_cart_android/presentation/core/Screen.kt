package com.android.shopping_cart_android.presentation.core

sealed class Screen(val route: String) {
    object ShoppingCart : Screen("shoppingCart")
    object ProductList : Screen("productList")
    object ProductDetails : Screen("productDetails/{productId}") {
        fun createRoute(productId: Int) = "productDetails/$productId"
    }
}