package com.example.nikestoreproject.data.repo

import com.example.nikestoreproject.data.AddToCartResponse
import com.example.nikestoreproject.data.CartItemCount
import com.example.nikestoreproject.data.CartResponse
import com.example.nikestoreproject.data.MessageResponse
import io.reactivex.Single

interface CartRepository {

    fun addToCart(productId: Int): Single<AddToCartResponse>
    fun get(): Single<CartResponse>
    fun remove(cartItem: Int): Single<MessageResponse>
    fun changeCount(cartItem: Int, count: Int): Single<AddToCartResponse>
    fun getCartItemCount(): Single<CartItemCount>
}