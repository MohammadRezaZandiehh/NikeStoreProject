package com.example.nikestoreproject.feature.product

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.example.nikestoreproject.NikeViewModel
import com.example.nikestoreproject.common.EXTRA_KEY_DATA
import com.example.nikestoreproject.common.NikeSingleObserver
import com.example.nikestoreproject.data.Comment
import com.example.nikestoreproject.data.Product
import com.example.nikestoreproject.data.repo.CommentRepository
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Schedulers.io

class ProductDetailsViewModel(bundle: Bundle, commentRepository: CommentRepository): NikeViewModel() {

    val productLiveData = MutableLiveData<Product>()
    val commentsLiveData = MutableLiveData<List<Comment>>()

    init {
        productLiveData.value = bundle.getParcelable(EXTRA_KEY_DATA)
        progressBarLiveData.value = true
        commentRepository.getAll(productLiveData.value!!.id)
//            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Comment>>(compositeDisposable) {
                override fun onSuccess(t: List<Comment>) {
                    commentsLiveData.value = t
                }
            })
    }
}