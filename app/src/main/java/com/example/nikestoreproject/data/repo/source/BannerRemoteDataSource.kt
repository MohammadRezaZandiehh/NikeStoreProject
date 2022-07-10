package com.example.nikestoreproject.data.repo.source

import com.example.nikestoreproject.data.model.Banner
import com.example.nikestoreproject.services.http.ApiService
import io.reactivex.Single

class BannerRemoteDataSource(val apiService: ApiService):BannerDataSource {
    override fun getBanners(): Single<List<Banner>> {
        return apiService.getBanner()
    }
}