package com.android.shopping_cart_android.domain.use_case

import com.android.shopping_cart_android.domain.CartItem

class CalculateCartItemPriceUseCase {
    operator fun invoke(cartItem: CartItem): Int {
        return (cartItem.product.costPrice ?: cartItem.product.retailPrice).times(cartItem.quantity)
    }
}