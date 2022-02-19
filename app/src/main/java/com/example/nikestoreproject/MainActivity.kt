package com.example.nikestoreproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    val httpClient: HttpClient by inject()
    val loadImage: LoadImage by inject()

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        httpClient.sendRequest()
        loadImage.loadImage("mamad")

        mainViewModel.changeValue()
        Log.i("MainActivity", "onCreate: value-> ${mainViewModel.value}")
    }
}