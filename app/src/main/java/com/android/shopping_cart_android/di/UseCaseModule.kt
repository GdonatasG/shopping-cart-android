package com.android.shopping_cart_android.di

import com.android.shopping_cart_android.domain.IProductRepository
import com.android.shopping_cart_android.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideWatchProductsUseCase(productRepository: IProductRepository): WatchProductsUseCase {
        return WatchProductsUseCase(productRepository = productRepository)
    }

    @Singleton
    @Provides
    fun provideUpdateCartItemUseCase(productRepository: IProductRepository): UpdateProductQuantityUseCase {
        return UpdateProductQuantityUseCase(productRepository = productRepository)
    }

    @Singleton
    @Provides
    fun provideRemoveCartItemUseCase(productRepository: IProductRepository): RemoveProductFromCartUseCase {
        return RemoveProductFromCartUseCase(productRepository = productRepository)
    }

    @Singleton
    @Provides
    fun provideCalculateCartProductTotalPriceUseCase(): CalculateCartProductTotalPriceUseCase {
        return CalculateCartProductTotalPriceUseCase()
    }

    @Singleton
    @Provides
    fun provideGetRemoteProductsUseCase(productRepository: IProductRepository): GetRemoteProductsUseCase {
        return GetRemoteProductsUseCase(productRepository = productRepository)
    }

    @Singleton
    @Provides
    fun provideWatchProductUseCase(productRepository: IProductRepository): WatchProductUseCase {
        return WatchProductUseCase(productRepository = productRepository)
    }

    @Singleton
    @Provides
    fun provideRemoveAllProductsFromCartUseCase(productRepository: IProductRepository): RemoveAllProductsFromCartUseCase {
        return RemoveAllProductsFromCartUseCase(productRepository = productRepository)
    }
}