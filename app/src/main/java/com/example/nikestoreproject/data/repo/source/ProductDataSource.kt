package com.example.nikestoreproject.data.repo.source

import com.example.nikestoreproject.data.model.Product
import io.reactivex.Completable
import io.reactivex.Single

interface ProductDataSource {

/*    fun getProducts(sort:Int): Single<List<Product>>

    suspend fun getProducts2(sort:Int): List<Product>

    fun getFavourite (): Single<List<Product>>

    fun addToFavourite (product: Product): Completable

    fun deleteFromFavourite(product: Product): Completable*/

    fun getProducts(sort: Int): Single<List<Product>>

    suspend fun getProducts2(sort: Int): List<Product>

    fun getFavoriteProducts(): Single<List<Product>>

    fun addToFavorites(product: Product): Completable

    fun deleteFromFavorites(product: Product): Completable
}

/*
* dataSource --> data az koja dare miad.
* repository --> makhzan e dade ha.
* */