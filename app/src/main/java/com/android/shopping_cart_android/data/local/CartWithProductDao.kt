package com.android.shopping_cart_android.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface CartWithProductDao {

    @Transaction
    @Query("SELECT * FROM cart")
    fun getCart(): Flow<List<CartWithProduct>>
}