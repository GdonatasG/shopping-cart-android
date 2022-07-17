package com.android.shopping_cart_android.domain.use_case

import com.android.shopping_cart_android.domain.CartItem
import com.android.shopping_cart_android.domain.ICartRepository
import kotlinx.coroutines.flow.Flow

class GetCartUseCase(
    private val cartRepository: ICartRepository,
) {
    operator fun invoke(): Flow<List<CartItem>> {
        return cartRepository.getCart()
    }
}