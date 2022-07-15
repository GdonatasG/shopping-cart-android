package com.android.shopping_cart_android.domain

data class Product(
    val productId: Int,
    val name: String,
    val description: String,
    val costPrice: Int? = null,
    val retailPrice: Int,
)
