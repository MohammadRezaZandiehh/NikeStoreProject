package com.example.nikestoreproject

import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import com.example.nikestoreproject.data.repo.*
import com.example.nikestoreproject.data.repo.source.*
import com.example.nikestoreproject.feature.ProductDetailsViewModel
import com.example.nikestoreproject.feature.auth.AuthViewModel
import com.example.nikestoreproject.feature.cart.CartViewModel
import com.example.nikestoreproject.feature.common.ProductListAdapter
import com.example.nikestoreproject.feature.list.ProductListViewModel
import com.example.nikestoreproject.services.FrescoImageLoadingService
import com.example.nikestoreproject.services.ImageLoadingService
import com.example.nikestoreproject.services.http.createApiServiceInstance
import com.facebook.drawee.backends.pipeline.Fresco
import com.example.nikestoreproject.feature.home.HomeViewModel
import com.sevenlearn.nikestore.feature.product.comment.CommentListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.android.ext.android.get
import org.koin.dsl.module
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        Fresco.initialize(this)

        val myModules = module {
            single { createApiServiceInstance() }
            single<ImageLoadingService> { FrescoImageLoadingService() }
            factory<ProductRepository> {
                ProductRepositoryImpl(
                    ProductRemoteDataSource(get()),
                    ProductLocalDataSource()
                )
            }
            factory { (viewType: Int) -> ProductListAdapter(viewType, get()) }
            factory<BannerRepository> { BannerRepositoryImpl(BannerRemoteDataSource(get())) }
            factory<CommentRepository> { CommentRepositoryImpl(CommentRemoteDataSource(get())) }
            factory<CartRepository> { CartRepositoryImpl(CartRemoteDataSource(get())) }

            single<SharedPreferences> { this@App.getSharedPreferences("app_settings", MODE_PRIVATE) }
            single<UserRepository> { UserRepositoryImpl(UserLocalDataSource(get()), UserRemoteDataSource(get())) }
            single { UserLocalDataSource(get()) }

            viewModel { HomeViewModel(get(), get()) }
            viewModel { (bundle: Bundle) -> ProductDetailsViewModel(bundle, get(), get()) }
            viewModel { (productId: Int) -> CommentListViewModel(productId, get()) }
            viewModel { (sort: Int) -> ProductListViewModel(sort, get()) }
            viewModel { AuthViewModel(get()) }
            viewModel { CartViewModel(get()) }
        }

        startKoin {
            androidContext(this@App)
            modules(myModules)
        }

        val userRepository: UserRepository = get()
        userRepository.loadToken()
    }
}

/*
* masalan alan vase ProductRepository, az factory estefade kardim chomn az ProductRepository gharar nist dar hame ye safahate app use konim.*/