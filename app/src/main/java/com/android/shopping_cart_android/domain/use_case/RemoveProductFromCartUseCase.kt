package com.android.shopping_cart_android.domain.use_case

import com.android.shopping_cart_android.domain.IProductRepository


class RemoveProductFromCartUseCase(
    private val productRepository: IProductRepository,
) {
    suspend operator fun invoke(productId: Int) {
        return productRepository.updateProductQuantity(productId = productId, quantity = 0)
    }
}