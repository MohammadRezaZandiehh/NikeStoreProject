package com.example.nikestoreproject.data.repo

import com.example.nikestoreproject.data.AddToCartResponse
import com.example.nikestoreproject.data.CartItemCount
import com.example.nikestoreproject.data.CartResponse
import com.example.nikestoreproject.data.MessageResponse
import com.example.nikestoreproject.data.repo.source.CartDataSource
import io.reactivex.Single

class CartRepositoryImpl(val remoteDataSource: CartDataSource) : CartRepository {
    override fun addToCart(productId: Int): Single<AddToCartResponse> =
        remoteDataSource.addToCart(productId)

    override fun get(): Single<CartResponse> {
        TODO("Not yet implemented")
    }

    override fun remove(cartItemId: Int): Single<MessageResponse> {
        TODO("Not yet implemented")
    }

    override fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse> {
        TODO("Not yet implemented")
    }

    override fun getCartItemsCount(): Single<CartItemCount> {
        TODO("Not yet implemented")
    }
}