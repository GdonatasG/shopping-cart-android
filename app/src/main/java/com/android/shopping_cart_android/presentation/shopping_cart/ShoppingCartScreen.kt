package com.android.shopping_cart_android.presentation.shopping_cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.shopping_cart_android.domain.CartItem
import com.chargemap.compose.numberpicker.NumberPicker

@Composable
fun ShoppingCartScreen(navController: NavController, viewModel: ShoppingCartViewModel = hiltViewModel()) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Shopping Cart")
                }
            )
        }
    ) {
        if (viewModel.state.isLoading) {
            BuildLoading()
            return@Scaffold
        }

        if (viewModel.state.cart.isNotEmpty()) {
            BuildCartProductList(cart = viewModel.state.cart)
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
private fun BuildCartProductList(cart: List<CartItem>) {
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
                    var pickerStateValue by remember { mutableStateOf(cartItem.quantity) }
                    NumberPicker(
                        value = pickerStateValue,
                        range = 1..5,
                        onValueChange = {
                            pickerStateValue = it
                        }
                    )
                }
                Spacer(Modifier.weight(1f))
                Box {
                    IconButton(onClick = { /*TODO*/ }) {
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