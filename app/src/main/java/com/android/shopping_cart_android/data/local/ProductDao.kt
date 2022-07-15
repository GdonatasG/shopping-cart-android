package com.android.shopping_cart_android.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy


@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(productEntity: ProductEntity)
}