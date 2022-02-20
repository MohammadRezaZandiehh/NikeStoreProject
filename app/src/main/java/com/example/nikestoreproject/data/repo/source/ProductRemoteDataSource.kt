package com.example.nikestoreproject.data.repo.source

import com.example.nikestoreproject.data.Product
import com.example.nikestoreproject.services.http.ApiService
import io.reactivex.Completable
import io.reactivex.Single

class ProductRemoteDataSource(val apiService: ApiService) : ProductDataSource {
      override fun getProducts(): Single<List<Product>> = apiService.getProducts()

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

/*
* remote yani a surver daryaft mikone.
* */