package com.android.shopping_cart_android.presentation.product_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.shopping_cart_android.domain.use_case.GetRemoteProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getRemoteProductsUseCase: GetRemoteProductsUseCase,
) : ViewModel() {
    private var _state by mutableStateOf(ProductListState())
    val state: ProductListState
        get() = _state


    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _state = state.copy(isLoading = true)
            try {
                _state = _state.copy(products = getRemoteProductsUseCase() ?: emptyList())
            } catch (cause: Exception) {
                _state = _state.copy(error = cause)
            } finally {
                _state = _state.copy(isLoading = false)
            }
        }
    }

    fun markErrorHandled() {
        _state = _state.copy(error = null)
    }

    fun onEvent(event: ProductListEvent) {
        when (event) {
            ProductListEvent.LoadProducts -> {
                loadProducts()
            }
        }
    }
}