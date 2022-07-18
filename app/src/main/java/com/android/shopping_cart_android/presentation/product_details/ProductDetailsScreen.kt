package com.android.shopping_cart_android.presentation.product_details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
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
import androidx.navigation.NavController
import com.android.shopping_cart_android.domain.Product
import com.chargemap.compose.numberpicker.NumberPicker

@Composable
fun ProductDetailsScreen(
    navController: NavController,
    viewModel: ProductDetailsViewModel,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = viewModel.state.product?.name ?: "")
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

        viewModel.state.product?.let { product ->
            BuildProduct(product = product, onQuantityChanged = { quantity ->
                viewModel.onEvent(ProductDetailsEvent.QuantityChanged(quantity = quantity))
            })
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
private fun BuildProduct(product: Product, onQuantityChanged: (quantity: Int) -> Unit) {
    LazyColumn {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
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
                        maxLines = 5,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "${product.costPrice ?: product.retailPrice} / piece",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold, color = Color.Red
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                if (product.quantity > 0) {
                    NumberPicker(
                        value = product.quantity,
                        range = 1..5,
                        onValueChange = { quantity ->
                            onQuantityChanged(quantity)
                        }
                    )
                } else {
                    IconButton(
                        onClick = {
                            onQuantityChanged(1)
                        },
                    ) {
                        Icon(Icons.Default.AddCircle, contentDescription = "Add product")
                    }
                }

            }
        }
    }

}