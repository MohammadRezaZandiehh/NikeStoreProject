package com.example.nikestoreproject.data

import androidx.annotation.StringRes

data class EmptyState(
    val mustShow: Boolean,
    @StringRes val messageRecId: Int = 0,
    val mustShowCallToActionBtn:Boolean = false
)
