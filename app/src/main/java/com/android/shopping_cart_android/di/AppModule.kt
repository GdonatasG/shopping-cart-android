package com.android.shopping_cart_android.di

import android.app.Application
import androidx.room.Room
import com.android.shopping_cart_android.data.ProductRepository
import com.android.shopping_cart_android.data.local.AppDatabase
import com.android.shopping_cart_android.data.remote.ProductService
import com.android.shopping_cart_android.domain.IProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideProductRepository(productService: ProductService, database: AppDatabase): IProductRepository {
        return ProductRepository(
            productService = productService,
            productDao = database.productDao,
        )
    }

    @Provides
    @Singleton
    fun provideProductService(): ProductService {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return Retrofit.Builder()
            .baseUrl("https://run.mocky.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ProductService::class.java)
    }
}