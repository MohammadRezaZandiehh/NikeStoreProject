package com.example.nikestoreproject.data.repo.source

import com.example.nikestoreproject.data.Product
import io.reactivex.Completable
import io.reactivex.Single

class ProductLocalDataSource : ProductDataSource{
    override fun getProducts(): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun getFavourite(): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun addToFavourite(): Completable {
        TODO("Not yet implemented")
    }

    override fun deleteFromFavourite(): Completable {
        TODO("Not yet implemented")
    }

}