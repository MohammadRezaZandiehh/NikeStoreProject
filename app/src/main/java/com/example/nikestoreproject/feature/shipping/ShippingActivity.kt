package com.example.nikestoreproject.feature.shipping

import android.os.Bundle
import android.system.Os.bind
import com.example.nikestoreproject.R
import com.example.nikestoreproject.common.EXTRA_KEY_DATA
import com.example.nikestoreproject.common.NikeActivity
import com.example.nikestoreproject.data.PurchaseDetail
import com.example.nikestoreproject.feature.cart.CartItemAdapter
import kotlinx.android.synthetic.main.activity_shipping.*
import org.koin.java.KoinJavaComponent.bind

class ShippingActivity: NikeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipping)

        val purchaseDetail = intent.getParcelableExtra<PurchaseDetail>(EXTRA_KEY_DATA)
            ?: throw IllegalStateException("purchase detail cannot be null")

        val viewHolder = CartItemAdapter.PurchaseDetailViewHolder(purchaseDetailView)
            viewHolder.bind(purchaseDetail.totalPrice, purchaseDetail.shipping_cost, purchaseDetail.payable_price)


    }
}