package com.example.nikestoreproject.services

import com.sevenlearn.nikestore.view.NikeImageView

interface ImageLoadingService {
    fun load(imageView: NikeImageView, imageUrl: String)
}