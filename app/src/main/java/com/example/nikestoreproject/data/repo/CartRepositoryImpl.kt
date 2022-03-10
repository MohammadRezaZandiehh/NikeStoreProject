package com.example.nikestoreproject.data.repo

import com.example.nikestoreproject.data.AddToCartResponse
import com.example.nikestoreproject.data.repo.source.CartDataSource
import com.example.nikestoreproject.data.CartItemCount
import com.example.nikestoreproject.data.CartResponse
import com.example.nikestoreproject.data.MessageResponse
import io.reactivex.Single

class CartRepositoryImpl(val cartDataSource: CartDataSource): CartRepository {
    override fun addToCart(productId: Int): Single<AddToCartResponse> = cartDataSource.addToCart(productId)

    override fun get(): Single<CartResponse> {
        TODO("Not yet implemented")
    }

    override fun remove(cartItem: Int): Single<MessageResponse> {
        TODO("Not yet implemented")
    }

    override fun changeCount(cartItem: Int, count: Int): Single<AddToCartResponse> {
        TODO("Not yet implemented")
    }

    override fun getCartItemCount(): Single<CartItemCount> {
        TODO("Not yet implemented")
    }
}