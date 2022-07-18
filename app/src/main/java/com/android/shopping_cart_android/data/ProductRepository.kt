package com.android.shopping_cart_android.data

import com.android.shopping_cart_android.data.local.ProductDao
import com.android.shopping_cart_android.data.local.toDomain
import com.android.shopping_cart_android.data.remote.ProductService
import com.android.shopping_cart_android.data.remote.toDomain
import com.android.shopping_cart_android.domain.IProductRepository
import com.android.shopping_cart_android.domain.Product
import com.android.shopping_cart_android.domain.toDatabase
import com.android.shopping_cart_android.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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

    override fun watchProducts(): Flow<List<Product>> {
        return productDao.watchProducts().map { products ->
            products.map { product ->
                product.toDomain()
            }
        }
    }

    override fun watchProduct(productId: Int): Flow<Product> {
        return productDao.watchProduct(productId = productId).map { product ->
            product.toDomain()
        }
    }

    override suspend fun updateProductQuantity(productId: Int, quantity: Int) {
        return productDao.updateProductQuantity(productId = productId, quantity = quantity)
    }

    override suspend fun updateOrInsert(product: Product) {
        return productDao.insertOrUpdate(product.toDatabase())
    }

    override suspend fun removeAllProductsFromCart() {
        return productDao.removeAllProductsFromCart()
    }
}