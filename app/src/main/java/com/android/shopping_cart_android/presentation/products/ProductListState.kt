package com.android.shopping_cart_android.presentation.products

import com.android.shopping_cart_android.domain.Product

data class ProductListState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: Exception? = null,
)