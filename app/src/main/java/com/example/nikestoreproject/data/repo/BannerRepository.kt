package com.example.nikestoreproject.data.repo

import com.example.nikestoreproject.data.Banner
import com.example.nikestoreproject.data.Product
import io.reactivex.Single

interface BannerRepository {
    fun getBanners(): Single<List<Banner>>
}