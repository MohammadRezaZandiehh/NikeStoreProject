package com.example.nikestoreproject.data.repo

import com.example.nikestoreproject.data.model.Product
import com.example.nikestoreproject.data.repo.source.ProductDataSource
import com.example.nikestoreproject.data.repo.source.ProductLocalDataSource
import com.example.nikestoreproject.services.http.ApiService
import io.reactivex.Completable
import io.reactivex.Single

class ProductRepositoryImpl(
    val remoteDataSource: ProductDataSource,
    val localDataSource: ProductLocalDataSource
) : ProductRepository {
    override fun getProducts(sort: Int): Single<List<Product>> =
        localDataSource.getFavoriteProducts()
            .flatMap { favoriteProducts ->
                remoteDataSource.getProducts(sort).doOnSuccess {
                    val favoriteProductIds = favoriteProducts.map {
                        it.id
                    }
                    it.forEach { product ->
                        if (favoriteProductIds.contains(product.id))
                            product.isFavorite = true
                    }
                }
            }

    override suspend fun getProducts2(sort: Int): List<Product> =
        remoteDataSource.getProducts2(sort)

    override fun getFavourite(): Single<List<Product>> = localDataSource.getFavoriteProducts()

    override fun addToFavourite(product: Product): Completable =
        localDataSource.addToFavourite(product)

    override fun deleteFromFavourite(product: Product): Completable =
        localDataSource.deleteFromFavourite(product)

}


/*
* chera getProduct() az remote data migire va na az local??? chon vazehe k gheymata hichvaghat nabayad cash beshe bere
* too local chon gheymata momkene taghir konan.
* */