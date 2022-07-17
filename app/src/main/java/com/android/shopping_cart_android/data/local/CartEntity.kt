package com.android.shopping_cart_android.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "cart",
)
data class CartEntity(
    @PrimaryKey
    val productId: Int,
    val quantity: Int = 1,
)