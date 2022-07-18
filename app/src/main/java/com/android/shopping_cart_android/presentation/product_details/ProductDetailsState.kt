package com.android.shopping_cart_android.presentation.product_details

import com.android.shopping_cart_android.domain.Product

data class ProductDetailsState(
    val product: Product? = null,
    val isLoading: Boolean = false,
)
