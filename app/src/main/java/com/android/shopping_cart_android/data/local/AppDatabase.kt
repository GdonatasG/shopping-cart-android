package com.android.shopping_cart_android.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [ProductEntity::class],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract val productDao: ProductDao
}