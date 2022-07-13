package com.example.nikestoreproject.checkout

import android.net.Uri
import android.os.Bundle
import com.example.nikestoreproject.R
import com.example.nikestoreproject.common.EXTRA_KEY_ID
import com.example.nikestoreproject.common.NikeActivity
import com.example.nikestoreproject.common.formatPrice
import com.example.nikestoreproject.feature.checkout.CheckoutViewModel
import com.example.nikestoreproject.feature.shipping.ShippingViewModel
import kotlinx.android.synthetic.main.activity_check_out.*
import kotlinx.android.synthetic.main.activity_shipping.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CheckOutActivity : NikeActivity() {

    val viewModel: CheckoutViewModel by viewModel {                 /*tamin kardne parameter e order_id : */
        val uri: Uri? = intent.data
        if (uri != null)
            parametersOf(uri.getQueryParameter("order_id")?.toInt())
        else
            parametersOf(intent.extras?.getInt(EXTRA_KEY_ID))
                                                                                                    /*age data khali bashe yani az safhe shipping varede checkOutActivity shodim
                                                                                                    vali age por bashe yani az browser omade :) */
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)

        viewModel.checkOutLiveData.observe(this){
            orderPriceTv.text = formatPrice(it.payable_price)
            orderStatusTv.text = it.payment_status
            purchaseStatusTv.text =
                if (it.purchase_success) "خرید با موفقیت انجام شد" else "خرید ناموفق"
        }

    }
}