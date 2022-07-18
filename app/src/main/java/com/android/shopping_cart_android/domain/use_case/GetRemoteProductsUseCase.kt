package com.android.shopping_cart_android.domain.use_case

import com.android.shopping_cart_android.domain.IProductRepository
import com.android.shopping_cart_android.domain.Product
import com.android.shopping_cart_android.domain.util.Resource
import java.lang.Exception

class GetRemoteProductsUseCase(
    private val productRepository: IProductRepository,
) {
    suspend operator fun invoke(): List<Product>? {
        when (val result = productRepository.getRemoteProducts()) {
            is Resource.Success -> {
                result.data?.let { products ->
                    products.forEach { product ->
                        productRepository.updateOrInsert(product = product)
                    }
                }
                return result.data
            }
            is Resource.Error -> {
                throw Exception(result.message)
            }
        }
    }
}