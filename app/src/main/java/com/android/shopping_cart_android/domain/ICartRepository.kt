package com.android.shopping_cart_android.domain

import kotlinx.coroutines.flow.Flow

interface ICartRepository {
    fun getCart(): Flow<List<CartItem>>
    suspend fun removeCartItem(cartItem: CartItem)
    suspend fun updateOrInsert(cartItem: CartItem)
}