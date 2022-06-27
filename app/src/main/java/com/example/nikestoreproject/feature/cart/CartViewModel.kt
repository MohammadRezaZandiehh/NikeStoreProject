package com.example.nikestoreproject.feature.cart

import androidx.lifecycle.MutableLiveData
import com.example.nikestoreproject.R
import com.example.nikestoreproject.common.NikeSingleObserver
import com.example.nikestoreproject.common.asyncNetworkRequest
import com.example.nikestoreproject.data.*
import com.example.nikestoreproject.data.repo.CartRepository
import com.sevenlearn.nikestore.common.NikeViewModel
import io.reactivex.Completable
import timber.log.Timber

class CartViewModel(val cartRepository: CartRepository) : NikeViewModel() {
    val cartItemsLiveData = MutableLiveData<List<CartItem>>()
    val purchaseDetailLiveData = MutableLiveData<PurchaseDetail>()
    val emptyStateLiveData = MutableLiveData<EmptyState>()

    init {
        refresh()
    }


    fun getCartItem() {
        if (!TokenContainer.token.isNullOrEmpty()) {
            progressBarLiveData.value = true
            cartRepository.get()
                .asyncNetworkRequest()
                .doFinally { progressBarLiveData.value = false }
                .subscribe(object : NikeSingleObserver<CartResponse>(compositeDisposable) {
                    override fun onSuccess(t: CartResponse) {
                        if (t.cart_items.isNotEmpty()) {
                            cartItemsLiveData.value = t.cart_items
                            purchaseDetailLiveData.value =
                                PurchaseDetail(t.total_price, t.shipping_cost, t.payable_price)
                        } else
                            emptyStateLiveData.value = EmptyState(true, R.string.cartEmptyState)
                    }
                })
        } else
            emptyStateLiveData.value = EmptyState(true, R.string.cartEmptyStateLoginRequired, true)

    }


    fun removeItemFromCart(cartItem: CartItem): Completable =
        cartRepository.remove(cartItem.cart_item_id)
            .doAfterSuccess {
                Timber.i("Cart Items Count after remove-> ${cartItemsLiveData.value?.size}")
                calculateAndPublishPurchaseDetail()
                cartItemsLiveData.value?.let {
                    if (it.isEmpty())
                        emptyStateLiveData.postValue(EmptyState(true, R.string.cartEmptyState))
                }

            }.ignoreElement()

    fun increaseCartItemCount(cartItem: CartItem): Completable =
        cartRepository.changeCount(cartItem.cart_item_id, ++cartItem.count)
            .doAfterSuccess {
                calculateAndPublishPurchaseDetail()
            }.ignoreElement()

    fun decreaseCartItemCount(cartItem: CartItem): Completable =
        cartRepository.changeCount(cartItem.cart_item_id, --cartItem.count)
            .doAfterSuccess {
                calculateAndPublishPurchaseDetail()
            }.ignoreElement()

    fun refresh() {
        getCartItem()
    }

    fun calculateAndPublishPurchaseDetail() {
        cartItemsLiveData.value?.let { cartItem ->
            purchaseDetailLiveData.value?.let { purchaseDetail ->
                var totalPrice = 0
                var payablePrice = 0

                cartItem.forEach {
                    totalPrice += it.product.price * it.count
                    payablePrice += (it.product.price - it.product.discount) * it.count
                }

                purchaseDetail.totalPrice = totalPrice
                purchaseDetail.payable_price = payablePrice
                purchaseDetailLiveData.postValue(purchaseDetail)
            }
        }
    }
}