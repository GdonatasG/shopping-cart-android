package com.android.shopping_cart_android.domain

import com.android.shopping_cart_android.data.local.CartEntity

fun CartItem.toDatabase(): CartEntity {
    return CartEntity(
        productId = product.productId,
        quantity = quantity,
    )
}

data class CartItem(
    val product: Product,
    val quantity: Int,
    val totalPrice: Int? = null,
)
