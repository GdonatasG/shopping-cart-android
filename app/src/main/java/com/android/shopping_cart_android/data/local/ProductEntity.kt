package com.android.shopping_cart_android.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.shopping_cart_android.domain.Product

fun ProductEntity.toDomain(): Product {
    return Product(
        productId = productId,
        name = name,
        description = description,
        costPrice = costPrice,
        retailPrice = retailPrice,
        quantity = quantity,
    )
}

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey
    val productId: Int,
    val name: String,
    val description: String,
    val costPrice: Int? = null,
    val retailPrice: Int,
    val quantity: Int = 0,
)