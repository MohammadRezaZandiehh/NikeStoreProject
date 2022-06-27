package com.example.nikestoreproject.feature.main

import android.R
import com.example.nikestoreproject.common.NikeSingleObserver
import com.example.nikestoreproject.data.CartItemCount
import com.example.nikestoreproject.data.TokenContainer
import com.example.nikestoreproject.data.repo.CartRepository
import com.sevenlearn.nikestore.common.NikeViewModel
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class MainViewModel(private val cartRepository: CartRepository): NikeViewModel() {
    fun getCartItemCart(){
        if (!TokenContainer.token.isNullOrEmpty()){
            cartRepository.getCartItemCount()
                .subscribeOn(Schedulers.io())
                .subscribe(object : NikeSingleObserver<CartItemCount>(compositeDisposable) {
                    override fun onSuccess(t: CartItemCount) {
                        EventBus.getDefault().postSticky(t)
                    }


                })
        }
    }
}