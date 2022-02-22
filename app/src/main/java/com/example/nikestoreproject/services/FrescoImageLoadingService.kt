package com.example.nikestoreproject.services

import com.facebook.drawee.view.SimpleDraweeView
import com.sevenlearn.nikestore.view.NikeImageView
import java.lang.IllegalStateException

class FrescoImageLoadingService :ImageLoadingService{
    override fun load(imageView: NikeImageView, imageUrl: String) {
        if (imageView is SimpleDraweeView)
            imageView.setImageURI(imageUrl)
        else
            throw IllegalStateException("ImageView must be instance of SimpleDraweeView")
    }
}

/*
* if (imageView is SimpleDraweeView)  --> miad mige k check kon bbin in imageView instance i az SimpleDraweeView hast ya na ..
*
* throw IllegalStateException("ImageView must be instance of SimpleDraweeView")  --->
* neshan dadane yek exception .
*
* */