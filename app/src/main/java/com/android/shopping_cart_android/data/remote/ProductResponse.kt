package com.android.shopping_cart_android.data.remote

import com.android.shopping_cart_android.domain.Product
import com.google.gson.annotations.SerializedName

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
    @SerializedName(value = "cost_price")
    val costPrice: Int? = null,
    @SerializedName(value = "retail_price")
    val retailPrice: Int,
)