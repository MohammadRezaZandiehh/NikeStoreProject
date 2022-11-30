package com.example.nikestoreproject.data.repo.banner.ds

import com.example.nikestoreproject.data.model.Banner
import io.reactivex.Single

interface BannerDataSource {
    fun getBanners(): Single<List<Banner>>
}