package com.android.shopping_cart_android.data

import com.android.shopping_cart_android.data.local.CartDao
import com.android.shopping_cart_android.data.local.toDomain
import com.android.shopping_cart_android.domain.CartItem
import com.android.shopping_cart_android.domain.ICartRepository
import com.android.shopping_cart_android.domain.toDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CartRepository(
    private val cartDao: CartDao,
) : ICartRepository {
    override fun getCart(): Flow<List<CartItem>> {
        return cartDao.getCart().map { cartWithProductList ->
            cartWithProductList.map {
                it.toDomain()
            }
        }
    }

    override suspend fun removeCartItem(cartItem: CartItem) {
        return cartDao.delete(cartEntity = cartItem.toDatabase())
    }

    override suspend fun updateCartItem(cartItem: CartItem) {
        return cartDao.insert(cartEntity = cartItem.toDatabase())
    }
}