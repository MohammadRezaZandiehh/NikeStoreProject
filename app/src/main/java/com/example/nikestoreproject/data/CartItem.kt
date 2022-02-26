package com.example.nikestoreproject.data

import com.example.nikestoreproject.data.Product

data class CartItem(
    val cart_item_id: Int,
    val count: Int,
    val product: Product
)