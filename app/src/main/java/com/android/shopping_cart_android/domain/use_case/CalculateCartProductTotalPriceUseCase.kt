package com.android.shopping_cart_android.domain.use_case

import com.android.shopping_cart_android.domain.Product

class CalculateCartProductTotalPriceUseCase {
    operator fun invoke(cartProduct: Product): Int {
        return (cartProduct.costPrice ?: cartProduct.retailPrice).times(cartProduct.quantity)
    }
}