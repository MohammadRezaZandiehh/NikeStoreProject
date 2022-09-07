package com.example.nikestoreproject.data.model

import com.example.nikestoreproject.data.model.OrderItem

data class OrderHistoryItem(
    val id: Int,
    val payable: Int,
    val order_items: List<OrderItem>,

    )