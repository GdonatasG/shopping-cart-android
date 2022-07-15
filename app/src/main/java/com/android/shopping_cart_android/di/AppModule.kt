package com.android.shopping_cart_android.di

import android.app.Application
import androidx.room.Room
import com.android.shopping_cart_android.data.CartRepository
import com.android.shopping_cart_android.data.local.AppDatabase
import com.android.shopping_cart_android.domain.ICartRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [UseCaseModule::class])
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "database.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideCartRepository(database: AppDatabase): ICartRepository {
        return CartRepository(
            cartDao = database.cartDao,
        )
    }
}