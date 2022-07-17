package com.android.shopping_cart_android.data.remote

import com.android.shopping_cart_android.domain.Product

fun ProductResponse.toDomain(): Product {
    return Product(
        productId = id.toInt(),
        name = name,
        description = description,
        costPrice = costPrice,
        retailPrice = retailPrice,
    )
}

data class ProductResponse(
    val id: String,
    val name: String,
    val description: String,
    val costPrice: Int? = null,
    val retailPrice: Int,
)