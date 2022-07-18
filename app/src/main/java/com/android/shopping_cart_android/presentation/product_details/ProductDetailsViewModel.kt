package com.android.shopping_cart_android.presentation.product_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.android.shopping_cart_android.domain.use_case.UpdateProductQuantityUseCase
import com.android.shopping_cart_android.domain.use_case.WatchProductUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProductDetailsViewModel @AssistedInject constructor(
    private val watchProductUseCase: WatchProductUseCase,
    private val updateProductQuantityUseCase: UpdateProductQuantityUseCase,
    @Assisted val productId: Int,
) : ViewModel() {
    private var _state by mutableStateOf(ProductDetailsState())
    val state: ProductDetailsState
        get() = _state

    init {
        viewModelScope.launch {
            _state = _state.copy(isLoading = true)
            watchProductUseCase(productId = productId).collectLatest { product ->
                _state = _state.copy(product = product, isLoading = false)
            }
        }
    }

    fun onEvent(event: ProductDetailsEvent) {
        when (event) {
            is ProductDetailsEvent.QuantityChanged -> {
                viewModelScope.launch {
                    updateProductQuantityUseCase(productId = productId, quantity = event.quantity)
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(productId: Int): ProductDetailsViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            productId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(productId) as T
            }
        }
    }
}