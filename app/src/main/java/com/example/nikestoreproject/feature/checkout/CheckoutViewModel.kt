package com.example.nikestoreproject.feature.checkout

import androidx.lifecycle.MutableLiveData
import com.example.nikestoreproject.common.NikeCompletableObserver
import com.example.nikestoreproject.common.NikeSingleObserver
import com.example.nikestoreproject.common.NikeViewModel
import com.example.nikestoreproject.common.asyncNetworkRequest
import com.example.nikestoreproject.data.model.Checkout
import com.example.nikestoreproject.data.repo.order.OrderRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.annotations.Async

class CheckoutViewModel(orderId: Int, orderRepository: OrderRepository) : NikeViewModel() {
    val checkOutLiveData = MutableLiveData<Checkout>()

    init {
        orderRepository.checkout(orderId)
            .asyncNetworkRequest()
            .subscribe(object : NikeSingleObserver<Checkout>(compositeDisposable) {
                override fun onSuccess(t: Checkout) {
                    checkOutLiveData.value = t
                }
            })
    }
}