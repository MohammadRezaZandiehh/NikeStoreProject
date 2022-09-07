package com.example.nikestoreproject.feature.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nikestoreproject.common.NikeCompletableObserver
import com.example.nikestoreproject.common.NikeSingleObserver
import com.example.nikestoreproject.common.asyncNetworkRequest
import com.example.nikestoreproject.data.*
import com.example.nikestoreproject.data.repo.BannerRepository
import com.example.nikestoreproject.data.repo.ProductRepository
import com.example.nikestoreproject.common.NikeViewModel
import com.example.nikestoreproject.data.model.Banner
import com.example.nikestoreproject.data.model.Product
import com.example.nikestoreproject.data.model.SORT_LATEST
import com.example.nikestoreproject.data.model.SORT_POPULAR
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val productRepository: ProductRepository,
    bannerRepository: BannerRepository
) : NikeViewModel() {

    val productsLiveData = MutableLiveData<List<Product>>()
    val popularProductsLiveData = MutableLiveData<List<Product>>()
    val bannersLiveData = MutableLiveData<List<Banner>>()

    init {
        progressBarLiveData.value = true
        productRepository.getProducts(SORT_LATEST)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    productsLiveData.value = t
                }
            })

        viewModelScope.launch {
            val product = productRepository.getProducts2(SORT_POPULAR)
            this@HomeViewModel.popularProductsLiveData.value = product
        }

        bannerRepository.getBanners()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeSingleObserver<List<Banner>>(compositeDisposable) {
                override fun onSuccess(t: List<Banner>) {
                    bannersLiveData.value = t
                }
            })
    }

    fun addProductToFavorites(product: Product) {
        if (product.isFavorite)
            productRepository.deleteFromFavorites(product)
                .subscribeOn(Schedulers.io())
                .subscribe(object : NikeCompletableObserver(compositeDisposable) {
                    override fun onComplete() {
                        product.isFavorite = false
                    }
                })
        else
            productRepository.addToFavorites(product)
                .subscribeOn(Schedulers.io())
                .subscribe(object : NikeCompletableObserver(compositeDisposable) {
                    override fun onComplete() {
                        product.isFavorite = true
                    }
                })
    }
}


/*
* progressBarLiveData.value = true --> az value use kardim chon to thread e asli hasti.
* .doFinally { progressBarLiveData.value = false } --> che javab mosbat bashe che manfi ...
*  */