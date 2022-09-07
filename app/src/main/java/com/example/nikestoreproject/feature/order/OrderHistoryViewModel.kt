package com.example.nikestoreproject.feature.order

import androidx.lifecycle.MutableLiveData
import com.example.nikestoreproject.common.NikeSingleObserver
import com.example.nikestoreproject.common.NikeViewModel
import com.example.nikestoreproject.common.asyncNetworkRequest
import com.example.nikestoreproject.data.model.OrderHistoryItem
import com.example.nikestoreproject.data.repo.order.OrderRepository

class OrderHistoryViewModel(orderRepository: OrderRepository) : NikeViewModel() {
    val orders = MutableLiveData<List<OrderHistoryItem>>()

    init {
        progressBarLiveData.value = true
        orderRepository.list().asyncNetworkRequest().doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<OrderHistoryItem>>(compositeDisposable){
                override fun onSuccess(t: List<OrderHistoryItem>) {
                    orders.value=t
                }
            })
    }
}