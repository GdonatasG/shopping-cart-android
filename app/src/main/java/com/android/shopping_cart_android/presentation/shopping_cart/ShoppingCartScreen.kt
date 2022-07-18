package com.android.shopping_cart_android.presentation.shopping_cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
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
import com.android.shopping_cart_android.domain.CartItem
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

        if (viewModel.state.cart.isNotEmpty()) {
            BuildCartProductList(
                cart = viewModel.state.cart,
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
    cart: List<CartItem>,
    onQuantityChanged: (index: Int, quantity: Int) -> Unit,
    onItemRemoved: (index: Int) -> Unit,
) {
    LazyColumn {
        itemsIndexed(cart) { index, cartItem ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillParentMaxWidth(0.5f)
                        .align(Alignment.CenterVertically)
                ) {
                    Column {
                        Text(
                            text = cartItem.product.name,
                            style = TextStyle(fontSize = 18.sp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = cartItem.product.description,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                        if (cartItem.totalPrice != null) {
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = cartItem.totalPrice.toString(),
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold, color = Color.Red
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(modifier = Modifier.fillParentMaxWidth(0.3f)) {
                    NumberPicker(
                        value = cartItem.quantity,
                        range = 1..5,
                        onValueChange = { value ->
                            onQuantityChanged(index, value)
                        }
                    )
                }
                Spacer(Modifier.weight(1f))
                Box {
                    IconButton(onClick = { onItemRemoved(index) }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "Remove product")
                    }
                }
            }
            if (index < cart.size - 1) {
                Divider()
            }
        }
    }
}