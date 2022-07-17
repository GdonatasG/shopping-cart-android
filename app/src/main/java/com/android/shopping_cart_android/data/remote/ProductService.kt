package com.android.shopping_cart_android.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface ProductService {
    @GET("v3/18532d04-e842-402e-ac43-10154fbc1f3e")
    suspend fun getProducts(): Response<ProductListResponse>
}