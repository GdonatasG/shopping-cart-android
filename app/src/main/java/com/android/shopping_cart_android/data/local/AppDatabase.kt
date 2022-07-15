package com.android.shopping_cart_android.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [CartEntity::class, ProductEntity::class],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract val cartDao: CartDao
    abstract val productDao: ProductDao
    abstract val cartWithProductDao: CartWithProductDao
}