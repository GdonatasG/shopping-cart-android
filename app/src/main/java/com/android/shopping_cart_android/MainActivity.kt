package com.android.shopping_cart_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.shopping_cart_android.presentation.core.Screen
import com.android.shopping_cart_android.presentation.screen.ShoppingCartScreen
import com.android.shopping_cart_android.ui.theme.ShoppingcartandroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingcartandroidTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.ShoppingCart.route) {
                    composable(route = Screen.ShoppingCart.route){
                        ShoppingCartScreen(navController = navController)
                    }
                }
            }
        }
    }
}