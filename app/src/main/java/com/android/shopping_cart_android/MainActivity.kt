package com.android.shopping_cart_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.android.shopping_cart_android.di.productDetailsViewModel
import com.android.shopping_cart_android.presentation.core.Screen
import com.android.shopping_cart_android.presentation.product_details.ProductDetailsScreen
import com.android.shopping_cart_android.presentation.product_list.ProductListScreen
import com.android.shopping_cart_android.presentation.shopping_cart.ShoppingCartScreen
import com.android.shopping_cart_android.ui.theme.ShoppingcartandroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ShoppingcartandroidTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "main") {
                    navigation(startDestination = Screen.ShoppingCart.route, route = "main") {
                        composable(route = Screen.ShoppingCart.route) {
                            ShoppingCartScreen(navController = navController)
                        }
                        composable(route = Screen.ProductList.route) {
                            ProductListScreen(navController = navController)
                        }
                        composable(route = Screen.ProductDetails.route) { backStackEntry ->
                            val productId = backStackEntry.arguments?.getString("productId")?.toInt()
                            requireNotNull(productId) { "Forgot to pass productId to ProductDetailsScreen" }
                            ProductDetailsScreen(
                                navController = navController, productDetailsViewModel(
                                    backStackEntry,
                                    productId = productId
                                )
                            )
                        }
                    }

                }
            }
        }
    }
}