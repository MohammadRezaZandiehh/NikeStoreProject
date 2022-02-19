package com.example.nikestoreproject.data.repo

import com.example.nikestoreproject.data.Product
import io.reactivex.Completable
import io.reactivex.Single

interface ProductRepository {

    fun getProducts(): Single<List<Product>>

    fun getFavourite ():Single<List<Product>>

    fun addToFavourite ():Completable

    fun deleteFromFavourite(): Completable
}

/*
* completable --> faghat bbinim ye chizi ezafe (ya hazf) shode ya na.
* */