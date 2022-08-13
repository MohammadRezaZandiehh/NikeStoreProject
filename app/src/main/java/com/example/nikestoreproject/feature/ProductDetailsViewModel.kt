package com.example.nikestoreproject.feature

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.example.nikestoreproject.common.*
import com.example.nikestoreproject.data.model.Comment
import com.example.nikestoreproject.data.model.Product
import com.example.nikestoreproject.data.repo.CartRepository
import com.example.nikestoreproject.data.repo.CommentRepository
import com.example.nikestoreproject.data.repo.ProductRepository
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers


class ProductDetailsViewModel(
    bundle: Bundle,
    commentRepository: CommentRepository,
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository
) : NikeViewModel() {

    val productLiveData = MutableLiveData<Product>()
    val commentsLiveData = MutableLiveData<List<Comment>>()

    init {
        productLiveData.value = bundle.getParcelable(EXTRA_KEY_DATA)
        progressBarLiveData.value = true
        commentRepository.getAll(productLiveData.value!!.id)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Comment>>(compositeDisposable) {
                override fun onSuccess(t: List<Comment>) {
                    commentsLiveData.value = t
                }
            })
    }

    fun onAddToCartBtn(): Completable =
        cartRepository.addToCart(productLiveData.value!!.id).ignoreElement()

    fun addToFavourite(product: Product) {
        if (product.isFavorite) {
            productRepository.addToFavourite(product)
                .subscribeOn(Schedulers.io())
                .subscribe(object : NikeCompletableObserver(compositeDisposable) {
                    override fun onComplete() {
                        product.isFavorite = false
                    }

                })
        }else{
            productRepository.addToFavourite(product)
                .subscribeOn(Schedulers.io())
                .subscribe(object : NikeCompletableObserver(compositeDisposable) {
                    override fun onComplete() {
                        product.isFavorite = true
                    }

                })
        }
    }
}