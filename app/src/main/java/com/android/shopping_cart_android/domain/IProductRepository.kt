package com.android.shopping_cart_android.domain

import com.android.shopping_cart_android.domain.util.Resource

interface IProductRepository {
    suspend fun getRemoteProducts(): Resource<List<Product>>
    suspend fun updateOrInsert(product: Product)
}