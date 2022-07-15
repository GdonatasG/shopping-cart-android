package com.android.shopping_cart_android.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey
    val productId: Int,
    val name: String,
    val description: String,
    val costPrice: Int? = null,
    val retailPrice: Int,
)