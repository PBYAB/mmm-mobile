package com.example.mmm_mobile.models


data class Product(
    val id: Long,

    val name: String,

    val barcode: String,

    val nutriScore: Int,

    val novaGroup: Int,

    val quantity: String,

    val image: String,
)
