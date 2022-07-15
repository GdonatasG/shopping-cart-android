package com.android.shopping_cart_android.data.local

import androidx.room.Embedded
import androidx.room.Relation
import com.android.shopping_cart_android.domain.CartItem

fun CartWithProduct.toDomain(): CartItem {
    return CartItem(
        product = productEntity.toDomain(),
        quantity = cartEntity.quantity,
    )
}

data class CartWithProduct(
    @Embedded
    val cartEntity: CartEntity,
    @Relation(parentColumn = "productId", entityColumn = "productId")
    val productEntity: ProductEntity,
)