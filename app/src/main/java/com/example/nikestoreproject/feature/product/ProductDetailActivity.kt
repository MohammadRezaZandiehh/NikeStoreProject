package com.example.nikestoreproject.feature.product

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestoreproject.R
import com.example.nikestoreproject.common.EXTRA_KEY_ID
import com.example.nikestoreproject.common.NikeCompletableObserver
import com.example.nikestoreproject.common.formatPrice
import com.example.nikestoreproject.data.model.Comment
import com.example.nikestoreproject.feature.ProductDetailsViewModel
import com.example.nikestoreproject.feature.product.comment.CommentListActivity
import com.example.nikestoreproject.services.ImageLoadingService
import com.example.nikestoreproject.view.scroll.ObservableScrollViewCallbacks
import com.example.nikestoreproject.view.scroll.ScrollState
import com.example.nikestoreproject.common.NikeActivity
import com.example.nikestoreproject.data.model.Product
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_product_details.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class ProductDetailActivity : NikeActivity() {
    private val productDetailViewModel: ProductDetailsViewModel by viewModel { parametersOf(intent.extras) }
    private val imageLoadingService: ImageLoadingService by inject()
    private val compositeDisposable = CompositeDisposable()

    //    private val commentAdapter : CommentAdapter by inject()
    private val commentAdapter = CommentAdapter()


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

            if (it.isFavorite)
                favoriteBtn.setImageResource(R.drawable.ic_favorite_fill)
            else
                favoriteBtn.setImageResource(R.drawable.ic_favorites)

            favoriteBtn.setOnClickListener { view ->
                clickedOnFavouriteBtn(it)
            }
        }

        productDetailViewModel.progressBarLiveData.observe(this) {
            setProgressIndicator(it)
        }
        productDetailViewModel.commentsLiveData.observe(this) {
            Timber.i(it.toString())
            commentAdapter.comments = it as ArrayList<Comment>
            if (it.size > 3) {
                viewAllCommentsBtn.visibility = View.VISIBLE

                viewAllCommentsBtn.setOnClickListener {
                    startActivity(
                        Intent(this, CommentListActivity::class.java).putExtra(
                            EXTRA_KEY_ID,
                            productDetailViewModel.productLiveData.value!!.id
                        )
                    )
                }
            }
        }

        initViews()

    }

    private fun initViews() {
        commentsRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        commentsRv.adapter = commentAdapter
        commentsRv.isNestedScrollingEnabled = false

        productIv.post {
            val productIvHeight = productIv.height
            val toolbar = toolbarView
            val productImageView = productIv
            observableScrollView.addScrollViewCallbacks(object : ObservableScrollViewCallbacks {
                override fun onScrollChanged(
                    scrollY: Int,
                    firstScroll: Boolean,
                    dragging: Boolean
                ) {
                    Timber.i("productIv height is -> $productIvHeight")
                    toolbar.alpha = scrollY.toFloat() / productIvHeight.toFloat()
                    productImageView.translationY = scrollY.toFloat() / 2
                }

                override fun onDownMotionEvent() {
                }

                override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {
                }

            })
        }

        addToCard.setOnClickListener {
            productDetailViewModel.onAddToCartBtn()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NikeCompletableObserver(compositeDisposable) {
                    override fun onComplete() {
                        showSnackBar(getString(R.string.success_addToCart))
                    }
                })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    private fun clickedOnFavouriteBtn(product: Product){
//        product.isFavorite = !product.isFavorite
        productDetailViewModel.addToFavourite(product)
    }
}


/*
* startActivity(Intent( this, CommentListActivity::class.java).putExtra(EXTRA_KEY_ID, productDetailViewModel.productLiveData.value!!.id))
* khate bala dar ghesmate : detail_Fifth_17th_session ------> bi nahaaaaayat kare khaffani kard.
* k chjoori omad productId ro a ProductDetailViewModel pass dad b intent ta " edame ye comment haye yek mahsool e khas " namayesh dade shavad .*/