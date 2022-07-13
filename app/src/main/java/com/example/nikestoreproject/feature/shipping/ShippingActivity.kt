package com.example.nikestoreproject.feature.shipping

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.nikestoreproject.R
import com.example.nikestoreproject.checkout.CheckOutActivity
import com.example.nikestoreproject.common.*
import com.example.nikestoreproject.data.SubmitOrderResult
import com.example.nikestoreproject.data.model.PurchaseDetail
import com.example.nikestoreproject.feature.cart.CartItemAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_shipping.*
import org.koin.android.viewmodel.ext.android.viewModel

class ShippingActivity : NikeActivity() {

    val viewModel: ShippingViewModel by viewModel()
    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipping)

        val purchaseDetail = intent.getParcelableExtra<PurchaseDetail>(EXTRA_KEY_DATA)
            ?: throw IllegalStateException("purchase detail cannot be null")

        val viewHolder = CartItemAdapter.PurchaseDetailViewHolder(purchaseDetailView)
        viewHolder.bind(
            purchaseDetail.totalPrice,
            purchaseDetail.shipping_cost,
            purchaseDetail.payable_price
        )

        val onClickListener = View.OnClickListener {
            viewModel.submitOrder(
                firstNameEt.text.toString(),
                lastNameEt.text.toString(),
                postalCodeEt.text.toString(),
                phoneNumberEt.text.toString(),
                addressEt.text.toString(),
                if (it.id == R.id.onlinePaymentBtn) PAYMENT_METHOD_ONLINE else PAYMENT_METHOD_COD
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NikeSingleObserver<SubmitOrderResult>(compositeDisposable) {
                    override fun onSuccess(t: SubmitOrderResult) {
                        if (t.bank_gateway_url.isNotEmpty()) {
                            openUrlInCustomTab(this@ShippingActivity, t.bank_gateway_url)
                        } else {
                            startActivity(
                                Intent(this@ShippingActivity, CheckOutActivity::class.java).apply {
                                    putExtra(EXTRA_KEY_ID, t.order_id)
                                }
                            )
                        }
                        finish()

                    }
                })
        }

        onlinePaymentBtn.setOnClickListener(onClickListener)
        codBtn.setOnClickListener(onClickListener)
    }
}