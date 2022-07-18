package com.android.shopping_cart_android.presentation.product_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.shopping_cart_android.domain.Product
import com.android.shopping_cart_android.presentation.core.Screen

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
                    viewModel.markErrorHandled()
                    viewModel.onEvent(ProductListEvent.LoadProducts)
                },
            )
            return@Scaffold
        }

        if (viewModel.state.products.isNotEmpty()) {
            BuildProductList(
                products = viewModel.state.products,
                onProductClick = { product ->
                    navController.navigate(Screen.ProductDetails.createRoute(productId = product.productId)) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
            return@Scaffold
        }

        BuildEmptyProductList()
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

@Composable
private fun BuildProductList(products: List<Product>, onProductClick: (product: Product) -> Unit) {
    LazyColumn {
        itemsIndexed(products) { index, product ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onProductClick(product)
                    }
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = product.name,
                        style = TextStyle(fontSize = 18.sp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = product.description,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = (product.costPrice ?: product.retailPrice).toString(),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold, color = Color.Red
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add to basket",
                    modifier = Modifier.size(30.dp)
                )
            }
            if (index < products.size - 1) {
                Divider()
            }
        }
    }
}

@Composable
private fun BuildEmptyProductList() {
    return Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "There are no products!")
    }
}