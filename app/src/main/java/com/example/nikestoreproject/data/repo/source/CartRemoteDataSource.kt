package com.example.nikestoreproject.data.repo.source

import com.example.nikestoreproject.data.AddToCartResponse
import com.example.nikestoreproject.services.http.ApiService
import com.google.gson.JsonObject
import com.example.nikestoreproject.data.CartItemCount
import com.example.nikestoreproject.data.CartResponse
import com.example.nikestoreproject.data.MessageResponse
import io.reactivex.Single

class CartRemoteDataSource(val apiService: ApiService) : CartDataSource {
    override fun addToCart(productId: Int): Single<AddToCartResponse> = apiService.addToCart(
        JsonObject().apply {
            addProperty("product_id", productId)
        }
    )

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