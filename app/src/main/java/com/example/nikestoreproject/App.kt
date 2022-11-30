package com.example.nikestoreproject

import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import androidx.room.Room
import com.example.nikestoreproject.data.db.AppDatabase
import com.example.nikestoreproject.data.repo.banner.ds.BannerRemoteDataSource
import com.example.nikestoreproject.data.repo.banner.BannerRepository
import com.example.nikestoreproject.data.repo.banner.BannerRepositoryImpl
import com.example.nikestoreproject.data.repo.cart.ds.CartRemoteDataSource
import com.example.nikestoreproject.data.repo.cart.CartRepository
import com.example.nikestoreproject.data.repo.cart.CartRepositoryImpl
import com.example.nikestoreproject.data.repo.comment.ds.CommentRemoteDataSource
import com.example.nikestoreproject.data.repo.comment.CommentRepository
import com.example.nikestoreproject.data.repo.comment.CommentRepositoryImpl
import com.example.nikestoreproject.data.repo.order.ds.OrderRemoteDataSource
import com.example.nikestoreproject.data.repo.order.OrderRepository
import com.example.nikestoreproject.data.repo.order.OrderRepositoryImpl
import com.example.nikestoreproject.data.repo.product.ds.ProductRemoteDataSource
import com.example.nikestoreproject.data.repo.product.ProductRepository
import com.example.nikestoreproject.data.repo.product.ProductRepositoryImpl
import com.example.nikestoreproject.data.repo.user.ds.UserLocalDataSource
import com.example.nikestoreproject.data.repo.user.ds.UserRemoteDataSource
import com.example.nikestoreproject.data.repo.user.UserRepository
import com.example.nikestoreproject.data.repo.user.UserRepositoryImpl
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
            single { Room.databaseBuilder(this@App, AppDatabase::class.java, "db_app").build() }  /*b soorat e single database ro sakhtim.*/
            factory<ProductRepository> {
                ProductRepositoryImpl(
                    ProductRemoteDataSource(get()),
                    get <AppDatabase>().productDao()                /* get <AppDatabase>(): appDatabase inject shod va ye instance azash barmigarde.
                                                                        .productDao : az jense productLocalDataSource bod va dar ProductRepositoryImpl ham moalefeye dovom bayad productLocalDataSource mizashtim --> injoori inject shod .*/
                )
            }
            factory { (viewType: Int) -> ProductListAdapter(viewType, get()) }
            factory<BannerRepository> { BannerRepositoryImpl(BannerRemoteDataSource(get())) }
            factory<CommentRepository> { CommentRepositoryImpl(CommentRemoteDataSource(get())) }
            factory<CartRepository> { CartRepositoryImpl(CartRemoteDataSource(get())) }

            single<SharedPreferences> { this@App.getSharedPreferences("app_settings", MODE_PRIVATE) }
            single<UserRepository> { UserRepositoryImpl(UserLocalDataSource(get()), UserRemoteDataSource(get())) }
            single { UserLocalDataSource(get()) }
            single<OrderRepository> { OrderRepositoryImpl(OrderRemoteDataSource(get())) }


            viewModel { HomeViewModel(get(), get()) }
            viewModel { (bundle: Bundle) -> ProductDetailsViewModel(bundle, get(), get()) }
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