package com.sevenlearn.nikestore.feature.main

import androidx.lifecycle.MutableLiveData
import com.example.nikestoreproject.NikeViewModel
import com.example.nikestoreproject.common.NikeSingleObserver
import com.example.nikestoreproject.data.Banner
import com.example.nikestoreproject.data.Product
import com.example.nikestoreproject.data.SORT_LATEST
import com.example.nikestoreproject.data.SORT_POPULAR
import com.example.nikestoreproject.data.repo.BannerRepository
import com.example.nikestoreproject.data.repo.ProductRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(productRepository: ProductRepository, bannerSlider: BannerRepository) : NikeViewModel() {
    val productsLiveData = MutableLiveData<List<Product>>()
    val popularProductsLiveData = MutableLiveData<List<Product>>()
    val bannerSliderLiveData = MutableLiveData<List<Banner>>()

    init {
        progressBarLiveData.value = true

        productRepository.getProducts(SORT_LATEST)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    productsLiveData.value = t
                }

            })

        productRepository.getProducts(SORT_POPULAR)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    popularProductsLiveData.value = t
                }

            })


        bannerSlider.getBannerSlider()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeSingleObserver<List<Banner>>(compositeDisposable) {
                override fun onSuccess(t: List<Banner>) {
                    bannerSliderLiveData.value = t
                }
            })
    }
}


/*
* progressBarLiveData.value = true --> az value use kardim chon to thread e asli hasti.
* .doFinally { progressBarLiveData.value = false } --> che javab mosbat bashe che manfi ...
*  */