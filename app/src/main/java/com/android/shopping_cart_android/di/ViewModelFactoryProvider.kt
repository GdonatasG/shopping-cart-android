package com.android.shopping_cart_android.di

import com.android.shopping_cart_android.presentation.product_details.ProductDetailsViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {
    fun productDetailsViewModelFactory(): ProductDetailsViewModel.Factory
}