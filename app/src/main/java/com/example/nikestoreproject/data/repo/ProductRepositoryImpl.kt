package com.example.nikestoreproject.data.repo

import com.example.nikestoreproject.data.Product
import com.example.nikestoreproject.data.repo.source.ProductDataSource
import com.example.nikestoreproject.data.repo.source.ProductLocalDataSource
import com.example.nikestoreproject.services.http.ApiService
import io.reactivex.Completable
import io.reactivex.Single

class ProductRepositoryImpl(
    val remoteDataSource: ProductDataSource,
    val localDataSource: ProductLocalDataSource
) : ProductRepository {
    override fun getProducts(sort:Int): Single<List<Product>> = remoteDataSource.getProducts(sort)

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
* chera getProduct() az remote data migire va na az local??? chon vazehe k gheymata hichvaghat nabayad cash beshe bere
* too local chon gheymata momkene taghir konan.
* */