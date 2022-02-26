package com.example.nikestoreproject.feature

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.example.nikestoreproject.common.EXTRA_KEY_DATA
import com.example.nikestoreproject.common.EXTRA_KEY_ID
import com.example.nikestoreproject.common.NikeSingleObserver
import com.example.nikestoreproject.common.asyncNetworkRequest
import com.example.nikestoreproject.data.Comment
import com.example.nikestoreproject.data.Product
import com.example.nikestoreproject.data.repo.CartRepository
import com.example.nikestoreproject.data.repo.CommentRepository
import com.sevenlearn.nikestore.common.NikeViewModel
import io.reactivex.Completable

class ProductDetailsViewModel(bundle: Bundle, commentRepository: CommentRepository, val cartRepository: CartRepository): NikeViewModel() {

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

    fun onAddToCartBtn(): Completable = cartRepository.addToCart(productLiveData.value!!.id).ignoreElement()

}

/* khorooji e addToCart --> Single ast ---> ignoreElement() : tabdil e Single b Completable
* chera az Complatable use kardim ?? --->  chon dg b view k niaz nadashtim k -------> "daghighe 20 mige"*/