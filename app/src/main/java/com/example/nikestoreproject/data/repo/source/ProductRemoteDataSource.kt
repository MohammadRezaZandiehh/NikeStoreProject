package com.example.nikestoreproject.data.repo.source

import com.example.nikestoreproject.data.model.Product
import com.example.nikestoreproject.services.http.ApiService
import io.reactivex.Completable
import io.reactivex.Single

class ProductRemoteDataSource(val apiService: ApiService) : ProductDataSource {
      override fun getProducts(sort:Int): Single<List<Product>> = apiService.getProducts(sort.toString())

    override suspend fun getProducts2(sort: Int): List<Product> = apiService.getProducts2(sort.toString())
    override fun getFavoriteProducts(): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun addToFavorites(product: Product): Completable {
        TODO("Not yet implemented")
    }

    override fun deleteFromFavorites(product: Product): Completable {
        TODO("Not yet implemented")
    }
}

/*
* remote yani a surver daryaft mikone.
* */