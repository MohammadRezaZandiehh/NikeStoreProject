package com.sevenlearn.nikestore.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nikestoreproject.NikeFragment
import com.example.nikestoreproject.R
import com.example.nikestoreproject.common.convertDpToPixel
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.*

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