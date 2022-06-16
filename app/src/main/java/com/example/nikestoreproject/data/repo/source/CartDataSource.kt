package com.example.nikestoreproject.data.repo.source

import com.example.nikestoreproject.data.AddToCartResponse
import com.example.nikestoreproject.data.CartItemCount
import com.example.nikestoreproject.data.CartResponse
import com.example.nikestoreproject.data.MessageResponse
import io.reactivex.Single

interface   CartDataSource {

    fun addToCart(productId: Int): Single<AddToCartResponse>
    fun get(): Single<CartResponse>
    fun remove(cartItemId: Int): Single<MessageResponse>
    fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse>
    fun getCartItemsCount(): Single<CartItemCount>
}