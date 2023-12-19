package com.example.mmm_mobile

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ProductDetailScreen(productId: Long?) {

    Text(text = "Product ID: $productId")

}