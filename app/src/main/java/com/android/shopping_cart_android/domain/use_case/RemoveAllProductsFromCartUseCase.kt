package com.android.shopping_cart_android.domain.use_case

import com.android.shopping_cart_android.domain.IProductRepository

class RemoveAllProductsFromCartUseCase(
    private val productRepository: IProductRepository,
) {
    suspend operator fun invoke() {
        return productRepository.removeAllProductsFromCart()
    }
}