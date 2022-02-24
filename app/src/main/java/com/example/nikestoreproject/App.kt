package com.example.nikestoreproject

import android.app.Application
import android.os.Bundle
import com.example.nikestoreproject.data.repo.*
import com.example.nikestoreproject.data.repo.source.*
import com.example.nikestoreproject.feature.main.ProductListAdapter
import com.example.nikestoreproject.feature.product.ProductDetailsViewModel
import com.example.nikestoreproject.services.FrescoImageLoadingService
import com.example.nikestoreproject.services.ImageLoadingService
import com.example.nikestoreproject.services.http.ApiService
import com.example.nikestoreproject.services.http.createApiServiceInstance
import com.facebook.drawee.backends.pipeline.Fresco
import com.sevenlearn.nikestore.feature.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        Fresco.initialize(this)

        val myModules = module {
            single <ApiService>{ createApiServiceInstance() }
            single <ImageLoadingService> { FrescoImageLoadingService() }
            factory <ProductRepository> { ProductRepositoryImpl(ProductRemoteDataSource(get()), ProductLocalDataSource()) }
            factory <BannerRepository> { BannerRepositoryImpl(BannerRemoteDataSource(get())) }
            factory <ProductListAdapter>{ ProductListAdapter(get()) }
            factory <CommentRepository> { CommentRepositoryImpl(CommentRemoteDataSource(get())) }
            viewModel { MainViewModel(get(), get()) }
            viewModel { (bundle: Bundle) -> ProductDetailsViewModel(bundle, get())}
        }

        startKoin {
            androidContext(this@App)
            modules(myModules)
        }

    }
}

/*
* masalan alan vase ProductRepository, az factory estefade kardim chomn az ProductRepository gharar nist dar hame ye safahate app use konim.*/