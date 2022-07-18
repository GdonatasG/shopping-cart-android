package com.android.shopping_cart_android.data

import com.android.shopping_cart_android.data.local.ProductDao
import com.android.shopping_cart_android.data.remote.ProductService
import com.android.shopping_cart_android.data.remote.toDomain
import com.android.shopping_cart_android.domain.IProductRepository
import com.android.shopping_cart_android.domain.Product
import com.android.shopping_cart_android.domain.toDatabase
import com.android.shopping_cart_android.domain.util.Resource

class ProductRepository(
    private val productService: ProductService,
    private val productDao: ProductDao
) : IProductRepository {
    override suspend fun getRemoteProducts(): Resource<List<Product>> {
        return try {
            Resource.Success(
                data = productService.getProducts().body()?.data?.map {
                    it.toDomain()
                }
            )
        } catch (cause: Exception) {
            cause.printStackTrace()
            Resource.Error(
                message = cause.message ?: "Unknown error"
            )
        }
    }

    override suspend fun updateOrInsert(product: Product) {
        return productDao.insert(productEntity = product.toDatabase())
    }
}