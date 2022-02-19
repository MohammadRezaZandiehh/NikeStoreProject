package com.example.nikestoreproject

import android.app.Application
import android.util.Log
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module


interface HttpClient {
    fun sendRequest()
}

interface LoadImage {
    fun loadImage(imageUrl: String)
}

class Retrofit : HttpClient {
    override fun sendRequest() {
        Log.d("Retrofit", "sendRequest")
    }
}

class Picasso : LoadImage {
    override fun loadImage(imageUrl: String) {
        Log.d("Picasso", "loadImage")
    }
}


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val myModule = module {
            single<HttpClient> { Retrofit() }
            factory<LoadImage> { Picasso() }
            viewModel { MainViewModel(get()) }
        }

        startKoin {
            androidContext(this@App)
            modules(myModule)
        }
    }
}
/*
* darvaze ye voroodi e ma b app hamin class mibashad.
* va onCreate() avalin jaei ast k vaghti class e App call mishe , seda zade mishe.
*
* dar module --> agar az "factory" estefade konim, har bar miad ye instance masalan az retrofit vasamoon misaze
* vali
* age az "single" estefade konim, mesle design pattern singleton kar khahad kard.
* yani dar toole hayate application faghat 1 instance vasamoon misaze. */