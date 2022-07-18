package com.android.shopping_cart_android.presentation.products

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun ProductListScreen(navController: NavController, viewModel: ProductListViewModel = hiltViewModel()) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Products")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
    ) {
        if (viewModel.state.isLoading) {
            BuildLoading()
            return@Scaffold
        }

        if (viewModel.state.error != null) {
            BuildInitialError(
                cause = viewModel.state.error,
                onRetry = {
                    viewModel.onEvent(ProductListEvent.LoadProducts)
                },
            )
            return@Scaffold
        }
    }
}

@Composable
private fun BuildLoading() {
    return Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun BuildInitialError(cause: Exception?, onRetry: () -> Unit) {
    return Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = cause?.message ?: "An error occurred while loading products!")
        Spacer(
            modifier = Modifier.height(5.dp)
        )
        Button(onClick = onRetry) {
            Text(text = "Try again")
        }
    }
}