package com.example.nikestoreproject.services

import com.example.nikestoreproject.view.NikeImageView

interface ImageLoadingService {
    fun load(imageView: NikeImageView, imageUrl: String)
}