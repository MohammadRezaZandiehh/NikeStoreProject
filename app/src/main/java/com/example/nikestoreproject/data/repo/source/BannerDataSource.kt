package com.example.nikestoreproject.data.repo.source

import com.example.nikestoreproject.data.model.Banner
import io.reactivex.Single

interface BannerDataSource {
    fun getBanners(): Single<List<Banner>>
}