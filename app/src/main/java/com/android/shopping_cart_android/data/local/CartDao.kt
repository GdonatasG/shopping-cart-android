package com.android.shopping_cart_android.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartEntity: CartEntity)

    @Delete
    suspend fun delete(cartEntity: CartEntity)

    @Transaction
    @Query("SELECT * FROM cart JOIN products on cart.productId = products.productId")
    fun getCart(): Flow<List<CartWithProduct>>
}