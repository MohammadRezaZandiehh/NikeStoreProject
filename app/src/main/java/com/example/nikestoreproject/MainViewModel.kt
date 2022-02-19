package com.example.nikestoreproject

import androidx.lifecycle.ViewModel

class MainViewModel(httpClient: HttpClient) : ViewModel() {
    var value = 0

    fun changeValue() {
        value += 2
    }
}