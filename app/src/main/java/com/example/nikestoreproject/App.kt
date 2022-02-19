package com.example.nikestoreproject

import android.app.Application
import android.util.Log
import com.example.feature.main.MainViewModel
import com.example.nikestoreproject.data.repo.ProductRepository
import com.example.nikestoreproject.data.repo.ProductRepositoryImpl
import com.example.nikestoreproject.data.repo.source.ProductLocalDataSource
import com.example.nikestoreproject.data.repo.source.ProductRemoteDataSource
import com.example.nikestoreproject.services.http.createApiServiceInstance
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant()

        val myModules = module {
            single { createApiServiceInstance() }
            factory<ProductRepository> {
                ProductRepositoryImpl(
                    ProductRemoteDataSource(get()),
                    ProductLocalDataSource()
                )
            }
            viewModel { MainViewModel(get()) }
        }

        startKoin {
            androidContext(this@App)
            modules(myModules)
        }

    }
}

/*
* masalan alan vase ProductRepository, az factory estefade kardim chomn az ProductRepository gharar nist dar hame ye safahate app use konim.*/