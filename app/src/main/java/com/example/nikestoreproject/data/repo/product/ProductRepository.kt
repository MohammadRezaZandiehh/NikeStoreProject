package com.example.nikestoreproject.data.repo.product

import com.example.nikestoreproject.data.model.Product
import io.reactivex.Completable
import io.reactivex.Single

interface ProductRepository {

/*    fun getProducts(sort: Int): Single<List<Product>>

    suspend fun getProducts2(sort: Int): List<Product>

    fun getFavourite(): Single<List<Product>>

    fun addToFavourite(product: Product): Completable

    fun deleteFromFavourite(product: Product): Completable*/

    fun getProducts(sort:Int): Single<List<Product>>

    suspend fun getProducts2(sort: Int): List<Product>

    fun getFavoriteProducts(): Single<List<Product>>

    fun addToFavorites(product: Product): Completable

    fun deleteFromFavorites(product: Product): Completable
}

/*
* completable --> faghat bbinim ye chizi ezafe (ya hazf) shode ya na.
* */