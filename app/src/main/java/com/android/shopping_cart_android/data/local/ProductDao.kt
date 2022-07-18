package com.android.shopping_cart_android.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(productEntity: ProductEntity): Long

    @Query(
        "UPDATE products SET name = :name, description = :description, " +
                "costPrice = :costPrice, retailPrice = :retailPrice WHERE productId = :productId"
    )
    suspend fun update(productId: Int, name: String, description: String, costPrice: Int?, retailPrice: Int)

    @Transaction
    suspend fun insertOrUpdate(productEntity: ProductEntity) {
        val id = insert(productEntity = productEntity)
        if (id == -1L) {
            update(
                productId = productEntity.productId,
                name = productEntity.name,
                description = productEntity.description,
                costPrice = productEntity.costPrice,
                retailPrice = productEntity.retailPrice,
            )
        }
    }

    @Query("UPDATE products SET quantity = :quantity WHERE productId = :productId")
    suspend fun updateProductQuantity(productId: Int, quantity: Int)

    @Query("UPDATE products SET quantity = 0")
    suspend fun removeAllProductsFromCart()

    @Query("SELECT * FROM products WHERE products.quantity > 0")
    fun watchProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE products.productId = :productId")
    fun watchProduct(productId: Int): Flow<ProductEntity>
}