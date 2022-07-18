package com.android.shopping_cart_android.di

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.shopping_cart_android.presentation.product_details.ProductDetailsViewModel
import dagger.hilt.android.EntryPointAccessors

@Composable
fun productDetailsViewModel(
    viewModelStoreOwner: ViewModelStoreOwner,
    productId: Int
): ProductDetailsViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    ).productDetailsViewModelFactory()

    return viewModel(
        viewModelStoreOwner = viewModelStoreOwner,
        factory = ProductDetailsViewModel.provideFactory(factory, productId = productId)
    )
}