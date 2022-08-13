package com.example.nikestoreproject.feature.list

import androidx.lifecycle.MutableLiveData
import com.example.nikestoreproject.R
import com.example.nikestoreproject.common.NikeCompletableObserver
import com.example.nikestoreproject.common.NikeSingleObserver
import com.example.nikestoreproject.common.asyncNetworkRequest
import com.example.nikestoreproject.data.repo.ProductRepository
import com.example.nikestoreproject.common.NikeViewModel
import com.example.nikestoreproject.data.model.Product
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ProductListViewModel(var sort: Int, val productRepository: ProductRepository) :
    NikeViewModel() {
    val productsLiveData = MutableLiveData<List<Product>>()
    val selectedSortTitleLiveData = MutableLiveData<Int>()
    val sortTitles = arrayOf(
        R.string.sortLatest,
        R.string.sortPopular,
        R.string.sortPriceHighToLow,
        R.string.sortPriceLowToHigh
    )

    init {
        getProducts()
        selectedSortTitleLiveData.value = sortTitles[sort]
    }

    fun getProducts() {
        progressBarLiveData.value = true
        productRepository.getProducts(sort)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    productsLiveData.value = t
                }
            })
    }

    fun onSelectedSortChangedByUser(sort: Int) {
        this.sort = sort
        this.selectedSortTitleLiveData.value = sortTitles[sort]
        getProducts()
    }

    fun addProductToFavourite(product: Product) {
        if (product.isFavorite) {
            productRepository.deleteFromFavourite(product)
                .subscribeOn(Schedulers.io())
                .subscribe(object : NikeCompletableObserver(compositeDisposable) {
                    override fun onComplete() {
                        Timber.i("product is deleted from ProductListActivity")
                        product.isFavorite = false
                    }
                })
        } else {
            productRepository.addToFavourite(product)
                .subscribeOn(Schedulers.io())
                .subscribe(object : NikeCompletableObserver(compositeDisposable) {
                    override fun onComplete() {
                        Timber.i("product is added from ProductListActivity")
                        product.isFavorite = true
                    }
                })
        }
    }
/*
    fun addProductToFavourite(product: Product) {
        if (product.isFavorite)
            productRepository.deleteFromFavourite(product)
                .subscribeOn(Schedulers.io())
                .subscribe(object : NikeCompletableObserver(compositeDisposable) {
                    override fun onComplete() {
                        product.isFavorite = false
                    }
                })
        else
            productRepository.addToFavourite(product)
                .subscribeOn(Schedulers.io())
                .subscribe(object : NikeCompletableObserver(compositeDisposable) {
                    override fun onComplete() {
                        product.isFavorite = true
                    }
                })
    }
*/

}