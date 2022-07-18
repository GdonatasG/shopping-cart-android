package com.android.shopping_cart_android.domain

import com.android.shopping_cart_android.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface IProductRepository {
    suspend fun getRemoteProducts(): Resource<List<Product>>
    fun watchProducts(): Flow<List<Product>>
    fun watchProduct(productId: Int): Flow<Product>
    suspend fun updateProductQuantity(productId: Int, quantity: Int)
    suspend fun updateOrInsert(product: Product)
}