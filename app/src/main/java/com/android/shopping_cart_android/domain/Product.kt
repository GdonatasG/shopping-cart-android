package com.android.shopping_cart_android.domain

import com.android.shopping_cart_android.data.local.ProductEntity

fun Product.toDatabase(): ProductEntity {
    return ProductEntity(
        productId = productId,
        name = name,
        description = description,
        costPrice = costPrice,
        retailPrice = retailPrice,
    )
}

data class Product(
    val productId: Int,
    val name: String,
    val description: String,
    val costPrice: Int? = null,
    val retailPrice: Int,
)
