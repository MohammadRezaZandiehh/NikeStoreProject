package com.example.nikestoreproject.feature.favourite

import androidx.lifecycle.MutableLiveData
import com.example.nikestoreproject.common.NikeCompletableObserver
import com.example.nikestoreproject.common.NikeSingleObserver
import com.example.nikestoreproject.common.NikeViewModel
import com.example.nikestoreproject.data.model.Product
import com.example.nikestoreproject.data.repo.product.ProductRepository
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class FavoriteProductsViewModel(private val productRepository: ProductRepository) :
    NikeViewModel() {

    val productsLiveData = MutableLiveData<List<Product>>()

    init {
        productRepository.getFavoriteProducts()
            .subscribeOn(Schedulers.io())
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    productsLiveData.postValue(t)
                }
            })
    }

    fun removeFromFavorites(product: Product) {
        productRepository.deleteFromFavorites(product)
            .subscribeOn(Schedulers.io())
            .subscribe(object : NikeCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                    Timber.i("removeFromFavorites compeleted")
                }
            })
    }
}