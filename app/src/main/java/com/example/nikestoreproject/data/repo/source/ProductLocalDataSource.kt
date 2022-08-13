package com.example.nikestoreproject.data.repo.source

import androidx.room.*
import com.example.nikestoreproject.data.model.Product
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ProductLocalDataSource : ProductDataSource {
    override fun getProducts(sort: Int): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProducts2(sort: Int): List<Product> {
        TODO("Not yet implemented")
    }

    @Query("select * FROM products")
    override fun getFavoriteProducts(): Single<List<Product>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun addToFavourite(product: Product): Completable

    @Delete
    override fun deleteFromFavourite(product: Product): Completable

}