package com.example.nikestoreproject.data.repo.source

import com.example.nikestoreproject.data.model.AddToCartResponse
import com.example.nikestoreproject.services.http.ApiService
import com.google.gson.JsonObject
import com.example.nikestoreproject.data.model.CartItemCount
import com.example.nikestoreproject.data.model.CartResponse
import com.example.nikestoreproject.data.model.MessageResponse
import io.reactivex.Single

class CartRemoteDataSource(val apiService: ApiService) : CartDataSource {
    override fun addToCart(productId: Int): Single<AddToCartResponse> {
        return apiService.addToCart(JsonObject().apply {
            addProperty("product_id", productId)
        })
    }


    override fun get(): Single<CartResponse> = apiService.getCart()

    override fun remove(cartItemId: Int): Single<MessageResponse> {
        return apiService.removeItemFromCart(JsonObject().apply {
            addProperty("cart_item_id", cartItemId)
        })
    }

    override fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse> {
        return apiService.changeCount(JsonObject().apply {
            addProperty("cart_item_id", cartItemId)
            addProperty("count", count)
        })
    }

    override fun getCartItemsCount(): Single<CartItemCount> = apiService.getCartItemsCount()
}