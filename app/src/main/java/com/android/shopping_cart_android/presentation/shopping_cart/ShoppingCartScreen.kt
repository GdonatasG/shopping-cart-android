package com.android.shopping_cart_android.presentation.shopping_cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
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
import com.chargemap.compose.numberpicker.NumberPicker

@Composable
fun ShoppingCartScreen(navController: NavController, viewModel: ShoppingCartViewModel = hiltViewModel()) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Shopping Cart")
                },
                actions = {
                    Text(text = "Full price:")
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = viewModel.state.totalSum.toString(),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold, color = Color.Red
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    IconButton(onClick = { viewModel.onEvent(ShoppingCartEvent.CartCleared) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Clear cart")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.ProductList.route) {
                    launchSingleTop = true
                    restoreState = true
                }
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add product")
            }
        }
    ) {
        if (viewModel.state.isLoading) {
            BuildLoading()
            return@Scaffold
        }

        if (viewModel.state.cartProducts.isNotEmpty()) {
            BuildCartProductList(
                cartProducts = viewModel.state.cartProducts,
                onQuantityChanged = { index, quantity ->
                    viewModel.onEvent(
                        ShoppingCartEvent.ProductQuantityChanged(
                            index = index,
                            quantity = quantity
                        )
                    )
                },
                onItemRemoved = { index ->
                    viewModel.onEvent(
                        ShoppingCartEvent.ProductRemoved(index = index)
                    )
                }
            )
            return@Scaffold
        }

        BuildEmptyCart()
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
private fun BuildEmptyCart() {
    return Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Basket is empty. Search for your favorite products!")
    }
}

@Composable
private fun BuildCartProductList(
    cartProducts: List<Product>,
    onQuantityChanged: (index: Int, quantity: Int) -> Unit,
    onItemRemoved: (index: Int) -> Unit,
) {
    LazyColumn {
        itemsIndexed(cartProducts) { index, product ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
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
                    if (product.totalPrice != null) {
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = product.totalPrice.toString(),
                            style = TextStyle(
                                fontWeight = FontWeight.Bold, color = Color.Red
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                NumberPicker(
                    value = product.quantity,
                    range = 1..5,
                    onValueChange = { value ->
                        onQuantityChanged(index, value)
                    }
                )
                Spacer(modifier = Modifier.height(5.dp))
                IconButton(onClick = { onItemRemoved(index) }) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = "Remove product")
                }
            }
            if (index < cartProducts.size - 1) {
                Divider()
            }
        }
    }
}