package com.example.nikestoreproject.data.repo.cart.ds

import com.example.nikestoreproject.data.model.AddToCartResponse
import com.example.nikestoreproject.data.model.CartItemCount
import com.example.nikestoreproject.data.model.CartResponse
import com.example.nikestoreproject.data.model.MessageResponse
import io.reactivex.Single

interface   CartDataSource {

    fun addToCart(productId: Int): Single<AddToCartResponse>
    fun get(): Single<CartResponse>
    fun remove(cartItemId: Int): Single<MessageResponse>
    fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse>
    fun getCartItemsCount(): Single<CartItemCount>
}