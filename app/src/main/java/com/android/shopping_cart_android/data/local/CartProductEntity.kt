package com.android.shopping_cart_android.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val productId: Int,
    val name: String,
    val description: String,
    val costPrice: Int? = null,
    val retailPrice: Int,
    val quantity: Int,
)