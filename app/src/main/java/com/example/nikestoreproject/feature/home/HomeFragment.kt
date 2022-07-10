package com.example.nikestoreproject.feature.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestoreproject.R
import com.example.nikestoreproject.common.EXTRA_KEY_DATA
import com.example.nikestoreproject.common.convertDpToPixel

import com.example.nikestoreproject.feature.common.ProductListAdapter
import com.example.nikestoreproject.feature.common.VIEW_TYPE_ROUND
import com.example.nikestoreproject.feature.list.ProductListActivity
import com.example.nikestoreproject.feature.product.ProductDetailActivity
import com.example.nikestoreproject.common.NikeFragment
import com.example.nikestoreproject.data.model.Product
import com.example.nikestoreproject.data.model.SORT_LATEST
import com.sevenlearn.nikestore.feature.main.BannerSliderAdapter
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber
import kotlin.collections.ArrayList

class HomeFragment : NikeFragment(), ProductListAdapter.OnProductClickListener {

    private val homeViewModel: HomeViewModel by viewModel()
    private val productListAdapter: ProductListAdapter by inject { parametersOf(VIEW_TYPE_ROUND) }
    private val productListAdapter2: ProductListAdapter by inject { parametersOf(VIEW_TYPE_ROUND) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        latestProductsRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        mostPopularProductsRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        latestProductsRv.adapter = productListAdapter
        mostPopularProductsRv.adapter = productListAdapter2
        productListAdapter.onProductClickListener = this
        productListAdapter2.onProductClickListener = this

        homeViewModel.productsLiveData.observe(viewLifecycleOwner) {
            Timber.i(it.toString())
            productListAdapter.products = it as ArrayList<Product>
        }

/*        homeViewModel.popularProductsLiveData.observe(viewLifecycleOwner) {
            productListAdapter2.products = it as ArrayList<Product>
        }*/

        homeViewModel.popularProductsLiveData2.observe(viewLifecycleOwner){
            productListAdapter2.products = it as ArrayList<Product>
        }

        viewLatestProductsBtn.setOnClickListener {
            startActivity(Intent(requireContext(), ProductListActivity::class.java).apply {
                putExtra(EXTRA_KEY_DATA, SORT_LATEST)
            })
        }

        homeViewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            setProgressIndicator(it)
        }

        homeViewModel.bannersLiveData.observe(viewLifecycleOwner) {
            Timber.i(it.toString())
            bannerSliderViewPager.post {
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

            }
        }
        ////******
//auto movement / action slider
/*        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                if (bannerSliderViewPager.adapter != null) {

                    if (bannerSliderViewPager.currentItem < bannerSliderViewPager.adapter!!.itemCount - 1)
                        bannerSliderViewPager.setCurrentItem(
                            bannerSliderViewPager.currentItem + 1,
                            true
                        )
                    else
                        bannerSliderViewPager.setCurrentItem(0, true)
                }
            }
        }, 3000, 3000)*/
//******


    }

    override fun onProductClick(product: Product) {
        startActivity(Intent(requireContext(), ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, product)
        })
    }
}