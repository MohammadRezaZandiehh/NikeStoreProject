package com.example.nikestoreproject.data.repo.order

import com.example.nikestoreproject.data.SubmitOrderResult
import com.example.nikestoreproject.data.model.Checkout
import com.example.nikestoreproject.data.model.OrderHistoryItem
import io.reactivex.Single

interface OrderRepository {

    fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Single<SubmitOrderResult>

    fun checkout(orderId: Int):Single<Checkout>

    fun list():Single<List<OrderHistoryItem>>

}