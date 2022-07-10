package com.example.nikestoreproject.data.repo

import com.example.nikestoreproject.data.model.Banner
import io.reactivex.Single

interface BannerRepository {
    fun getBanners(): Single<List<Banner>>
}