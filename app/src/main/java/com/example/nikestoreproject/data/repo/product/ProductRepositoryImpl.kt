package com.example.nikestoreproject.data.repo.product

import com.example.nikestoreproject.data.model.Product
import com.example.nikestoreproject.data.repo.product.ds.ProductDataSource
import com.example.nikestoreproject.data.repo.product.ds.ProductLocalDataSource
import io.reactivex.Completable
import io.reactivex.Single

/*class ProductRepositoryImpl(
    val remoteDataSource: ProductDataSource,
    val localDataSource: ProductLocalDataSource
) : ProductRepository {
    override fun getProducts(sort:Int): Single<List<Product>> =
        localDataSource.getFavourite()
            .flatMap { favoriteProducts ->
                remoteDataSource.getProducts(sort).doOnSuccess {
                    val favoriteProductIds = favoriteProducts.map {
                        it.id
                    }
                    it.forEach { product ->
                        if (favoriteProductIds.contains(product.id))
                            product.isFavourite = true
                    }
                }
            }
    override suspend fun getProducts2(sort: Int): List<Product> = remoteDataSource.getProducts2(sort)
    override fun getFavourite(): Single<List<Product>> = localDataSource.getFavourite()
    override fun addToFavourite(product: Product): Completable = localDataSource.addToFavourite(product)

    override fun deleteFromFavourite(product: Product): Completable = localDataSource.deleteFromFavourite(product)


}*/
class ProductRepositoryImpl(
    private val remoteDataSource: ProductDataSource,
    private val localDataSource: ProductLocalDataSource
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

    override fun getFavoriteProducts(): Single<List<Product>> =
        localDataSource.getFavoriteProducts()

    override fun addToFavorites(product: Product): Completable =
        localDataSource.addToFavorites(product)

    override fun deleteFromFavorites(product: Product): Completable =
        localDataSource.deleteFromFavorites(product)

}

/*
* chera getProduct() az remote data migire va na az local??? chon vazehe k gheymata hichvaghat nabayad cash beshe bere
* too local chon gheymata momkene taghir konan.
* */