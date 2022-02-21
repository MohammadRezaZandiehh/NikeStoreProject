package com.sevenlearn.nikestore.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nikestoreproject.NikeFragment
import com.example.nikestoreproject.R
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainFragment : NikeFragment() {
    val mainViewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.productsLiveData.observe(viewLifecycleOwner) {
            Timber.i(it.toString())
        }
        mainViewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            setProgressIndicator(true)
        }
        mainViewModel.bannerSliderLiveData.observe(viewLifecycleOwner){
            Timber.i(it.toString())
            val bannerSliderAdapter = BannerSliderAdapter(this, it)
            bannerSliderViewPager.adapter = bannerSliderAdapter

//            val viewPagerHeight = (((bannerSliderViewPager.measuredWidth - convertDpToPixel(32f, requireContext())) * 173) / 328).toInt()

            val viewPagerHeight = (bannerSliderViewPager.measuredWidth * 173) / 328
            val layoutParams = bannerSliderViewPager.layoutParams
            layoutParams.height = viewPagerHeight
            bannerSliderViewPager.layoutParams = layoutParams

//            sliderIndicator.setViewPager2(bannerSliderViewPager)

        }
    }
}