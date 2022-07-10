package com.example.nikestoreproject.data.repo.source

import com.example.nikestoreproject.data.model.Product
import io.reactivex.Completable
import io.reactivex.Single

class ProductLocalDataSource : ProductDataSource{
    override fun getProducts(sort:Int): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProducts2(sort: Int): List<Product> {
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