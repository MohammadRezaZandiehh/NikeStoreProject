package com.example.nikestoreproject

import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import androidx.room.Room
import com.example.nikestoreproject.data.db.AppDatabase
import com.example.nikestoreproject.data.repo.*
import com.example.nikestoreproject.data.repo.order.OrderDataSource
import com.example.nikestoreproject.data.repo.order.OrderRemoteDataSource
import com.example.nikestoreproject.data.repo.order.OrderRepository
import com.example.nikestoreproject.data.repo.order.OrderRepositoryImpl
import com.example.nikestoreproject.data.repo.source.*
import com.example.nikestoreproject.feature.ProductDetailsViewModel
import com.example.nikestoreproject.feature.auth.AuthViewModel
import com.example.nikestoreproject.feature.cart.CartViewModel
import com.example.nikestoreproject.feature.checkout.CheckoutViewModel
import com.example.nikestoreproject.feature.common.ProductListAdapter
import com.example.nikestoreproject.feature.favourite.FavoriteProductsViewModel
import com.example.nikestoreproject.feature.list.ProductListViewModel
import com.example.nikestoreproject.services.FrescoImageLoadingService
import com.example.nikestoreproject.services.ImageLoadingService
import com.example.nikestoreproject.services.http.createApiServiceInstance
import com.facebook.drawee.backends.pipeline.Fresco
import com.example.nikestoreproject.feature.home.HomeViewModel
import com.example.nikestoreproject.feature.main.MainViewModel
import com.example.nikestoreproject.feature.order.OrderHistoryViewModel
import com.example.nikestoreproject.feature.profile.ProfileViewModel
import com.example.nikestoreproject.feature.shipping.ShippingActivity
import com.example.nikestoreproject.feature.shipping.ShippingViewModel
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
            single { Room.databaseBuilder(this@App,AppDatabase::class.java,"db_app").build() }
            factory<ProductRepository> { ProductRepositoryImpl(ProductRemoteDataSource(get()), get<AppDatabase>().productDao()) }
            factory { (viewType: Int) -> ProductListAdapter(viewType, get()) }
            factory<BannerRepository> { BannerRepositoryImpl(BannerRemoteDataSource(get())) }
            factory<CommentRepository> { CommentRepositoryImpl(CommentRemoteDataSource(get())) }
            factory<CartRepository> { CartRepositoryImpl(CartRemoteDataSource(get())) }

            single<SharedPreferences> { this@App.getSharedPreferences("app_settings", MODE_PRIVATE) }
            single<UserRepository> { UserRepositoryImpl(UserLocalDataSource(get()), UserRemoteDataSource(get())) }
            single { UserLocalDataSource(get()) }
            single<OrderRepository> { OrderRepositoryImpl(OrderRemoteDataSource(get())) }


            viewModel { HomeViewModel(get(), get()) }
            viewModel { (bundle: Bundle) -> ProductDetailsViewModel(bundle, get(), get(), get()) }
            viewModel { (productId: Int) -> CommentListViewModel(productId, get()) }
            viewModel { (sort: Int) -> ProductListViewModel(sort, get()) }
            viewModel { AuthViewModel(get()) }
            viewModel { CartViewModel(get()) }
            viewModel { MainViewModel(get()) }
            viewModel { ShippingViewModel(get()) }
            viewModel { (orderId: Int) -> CheckoutViewModel(orderId, get()) }
            viewModel { ProfileViewModel(get()) }
            viewModel { FavoriteProductsViewModel(get()) }
            viewModel { OrderHistoryViewModel(get()) }
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