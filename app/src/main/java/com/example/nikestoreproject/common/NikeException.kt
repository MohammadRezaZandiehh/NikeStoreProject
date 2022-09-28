package com.example.nikestoreproject.common

import androidx.annotation.StringRes

class NikeException(
    val type: Type,
    @StringRes val userFriendlyMessage: Int = 0,
    val serverMessage: String? = null
) : Throwable() {

    enum class Type {
        SIMPLE, DIALOG, AUTH
    }
}

/*
* userFriendlyMessage --> dar khode barname in message ha ro misazim.
* serverMessage --> hame ye in message ha az server miad.
*  */