package com.example.nikestoreproject.feature.favourite

import androidx.lifecycle.MutableLiveData
import com.example.nikestoreproject.common.NikeViewModel
import com.example.nikestoreproject.data.model.Product
import com.example.nikestoreproject.data.repo.ProductRepository

class FavoriteProductsViewModel(private val productRepository: ProductRepository) :
    NikeViewModel() {

    val productsLiveData = MutableLiveData<List<Product>>()

}