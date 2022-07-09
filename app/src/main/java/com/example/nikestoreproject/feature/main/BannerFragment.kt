package com.example.nikestoreproject.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nikestoreproject.R
import com.example.nikestoreproject.common.EXTRA_KEY_DATA
import com.example.nikestoreproject.data.Banner
import com.example.nikestoreproject.services.ImageLoadingService
import com.sevenlearn.nikestore.view.NikeImageView
import org.koin.android.ext.android.inject
import java.lang.IllegalStateException

class BannerFragment : Fragment() {
    val imageLoadingService: ImageLoadingService by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val imageView =
            inflater.inflate(R.layout.fragment_banner, container, false) as NikeImageView
        val banner =
            requireArguments().getParcelable<Banner>(EXTRA_KEY_DATA) ?: throw IllegalStateException(
                "Banner cannot be null"
            )
        imageLoadingService.load(imageView, banner.image)
        return imageView
    }

    companion object {
        fun newInstance(banner: Banner): BannerFragment {
//            return BannerFragment().apply {
//                arguments = Bundle().apply {
//                    putParcelable(EXTRA_KEY_DATA, banner)
//                }
//            }

/*
*
* raveshe bala tmaiz tar o kotlini tar ast. vali ---> raveshe paeini ghabele fahm tar ast.*/

            val bannerFragment = BannerFragment()
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_KEY_DATA, banner)
            bannerFragment.arguments = bundle
            return bannerFragment
        }
    }
}

/* kollamn apply{} dar kotlin kare hamon object sakhtn roi mikone fk konam .*/