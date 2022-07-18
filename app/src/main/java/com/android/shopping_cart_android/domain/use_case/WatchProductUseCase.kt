package com.android.shopping_cart_android.domain.use_case


import com.android.shopping_cart_android.domain.IProductRepository
import com.android.shopping_cart_android.domain.Product
import kotlinx.coroutines.flow.Flow

class WatchProductUseCase(
    private val productRepository: IProductRepository,
) {
    operator fun invoke(productId: Int): Flow<Product> {
        return productRepository.watchProduct(productId = productId)
    }
}