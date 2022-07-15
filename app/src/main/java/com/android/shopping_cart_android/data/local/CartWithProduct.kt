package com.android.shopping_cart_android.data.local

import androidx.room.Embedded
import androidx.room.Relation

data class CartWithProduct(
    @Embedded
    val cartEntity: CartEntity,
    @Relation(parentColumn = "productId", entityColumn = "productId")
    val productEntity: ProductEntity,
)