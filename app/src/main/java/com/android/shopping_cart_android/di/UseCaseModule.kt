package com.android.shopping_cart_android.di

import com.android.shopping_cart_android.domain.ICartRepository
import com.android.shopping_cart_android.domain.use_case.GetCartUseCase
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
    fun provideGetCartUseCase(cartRepository: ICartRepository): GetCartUseCase {
        return GetCartUseCase(cartRepository = cartRepository)
    }
}