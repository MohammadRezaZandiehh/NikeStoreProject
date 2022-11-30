package com.example.nikestoreproject.data.repo.cart

import com.example.nikestoreproject.data.model.AddToCartResponse
import com.example.nikestoreproject.data.model.CartItemCount
import com.example.nikestoreproject.data.model.CartResponse
import com.example.nikestoreproject.data.model.MessageResponse
import io.reactivex.Single

interface CartRepository {

    fun addToCart(productId: Int): Single<AddToCartResponse>
    fun get(): Single<CartResponse>
    fun remove(cartItem: Int): Single<MessageResponse>
    fun changeCount(cartItem: Int, count: Int): Single<AddToCartResponse>
    fun getCartItemCount(): Single<CartItemCount>
}