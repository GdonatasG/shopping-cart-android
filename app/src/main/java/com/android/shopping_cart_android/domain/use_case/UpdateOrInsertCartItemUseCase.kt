package com.android.shopping_cart_android.domain.use_case

import com.android.shopping_cart_android.domain.CartItem
import com.android.shopping_cart_android.domain.ICartRepository

class UpdateOrInsertCartItemUseCase(
    private val cartRepository: ICartRepository,
) {
    suspend operator fun invoke(cartItem: CartItem) {
        return cartRepository.updateOrInsert(cartItem = cartItem)
    }
}