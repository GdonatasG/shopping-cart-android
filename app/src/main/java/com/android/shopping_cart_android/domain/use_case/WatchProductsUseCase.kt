package com.android.shopping_cart_android.domain.use_case

import com.android.shopping_cart_android.domain.IProductRepository
import com.android.shopping_cart_android.domain.Product
import kotlinx.coroutines.flow.Flow

class WatchProductsUseCase(
    private val productRepository: IProductRepository,
) {
    operator fun invoke(): Flow<List<Product>> {
        return productRepository.watchProducts()
    }
}