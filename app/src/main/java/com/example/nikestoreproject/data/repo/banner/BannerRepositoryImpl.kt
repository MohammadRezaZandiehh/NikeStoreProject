package com.example.nikestoreproject.data.repo.banner

import com.example.nikestoreproject.data.model.Banner
import com.example.nikestoreproject.data.repo.banner.ds.BannerDataSource
import io.reactivex.Single

class BannerRepositoryImpl (val bannerRemoteDataSource: BannerDataSource): BannerRepository {
    override fun getBanners(): Single<List<Banner>> {
        return bannerRemoteDataSource.getBanners()
    }
}