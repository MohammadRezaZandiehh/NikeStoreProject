package com.sevenlearn.nikestore.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestoreproject.NikeFragment
import com.example.nikestoreproject.R
import com.example.nikestoreproject.common.convertDpToPixel
import com.example.nikestoreproject.data.Product
import com.example.nikestoreproject.feature.main.BannerSliderAdapter
import com.example.nikestoreproject.feature.main.ProductListAdapter
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class MainFragment : NikeFragment() {
    val mainViewModel: MainViewModel by viewModel()
    val productListAdapter: ProductListAdapter by inject()
    val productListAdapter2: ProductListAdapter by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        latestProductsRv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        mostPopularProductsRv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        latestProductsRv.adapter = productListAdapter
        mostPopularProductsRv.adapter = productListAdapter2


        mainViewModel.productsLiveData.observe(viewLifecycleOwner) {
            Timber.i(it.toString())
            productListAdapter.products = it as ArrayList<Product>
        }

        mainViewModel.popularProductsLiveData.observe(viewLifecycleOwner){
            productListAdapter2.products = it as ArrayList<Product>
        }

        mainViewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            setProgressIndicator(it)
        }
        mainViewModel.bannerSliderLiveData.observe(viewLifecycleOwner){
            Timber.i(it.toString())

            val bannerSliderAdapter = BannerSliderAdapter(this, it)
            bannerSliderViewPager.adapter = bannerSliderAdapter

            val viewPagerHeight = (((bannerSliderViewPager.measuredWidth - convertDpToPixel(
                32f,
                requireContext()
            )) * 173) / 328).toInt()

            val layoutParams = bannerSliderViewPager.layoutParams
            layoutParams.height = viewPagerHeight
            bannerSliderViewPager.layoutParams = layoutParams

            sliderIndicator.setViewPager2(bannerSliderViewPager)

//******
//auto movement / action slider
            val timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    if (bannerSliderViewPager.currentItem < bannerSliderAdapter.itemCount - 1)
                        bannerSliderViewPager.setCurrentItem(
                            bannerSliderViewPager.currentItem + 1,
                            true
                        )
                    else
                        bannerSliderViewPager.setCurrentItem(0, true)
                }
            }, 3000, 3000)
//******
        }
    }
}