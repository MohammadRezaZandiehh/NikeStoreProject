package com.example.nikestoreproject.feature.product

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nikestoreproject.R
import com.example.nikestoreproject.common.formatPrice
import com.example.nikestoreproject.services.ImageLoadingService
import kotlinx.android.synthetic.main.activity_product_details.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class ProductDetailsActivity : AppCompatActivity() {
    val productDetailViewModel: ProductDetailsViewModel by viewModel { parametersOf(intent.extras) }
    val imageLoadingService: ImageLoadingService by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)
        productDetailViewModel.productLiveData.observe(this) {
            imageLoadingService.load(productIv, it.image)
            titleTv.text = it.title
            previousPriceTv.text = formatPrice(it.previous_price)
            previousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            currentPriceTv.text = formatPrice(it.price)
            toolbarTitleTv.text = it.title
        }

        productDetailViewModel.commentsLiveData.observe(this){
            Timber.i(it.toString())

        }
    }
}