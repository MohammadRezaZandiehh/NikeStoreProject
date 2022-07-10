package com.example.nikestoreproject.data.repo.order

import com.example.nikestoreproject.data.SubmitOrderResult
import com.example.nikestoreproject.data.model.Checkout
import io.reactivex.Single

interface OrderDataSource {

    fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Single<SubmitOrderResult>

    fun checkout(orderId: Int):Single<Checkout>
}