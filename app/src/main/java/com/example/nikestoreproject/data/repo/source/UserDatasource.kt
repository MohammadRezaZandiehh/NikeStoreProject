package com.example.nikestoreproject.data.repo.source

import com.example.nikestoreproject.data.MessageResponse
import com.example.nikestoreproject.data.TokenResponse
import io.reactivex.Single

interface UserDataSource {

    fun login(username: String, password: String): Single<TokenResponse>
    fun signUp(username: String, password: String): Single<MessageResponse>
    fun loadToken()      /*daghighe 15 ye tozihi dad k in method chie*/
    fun saveToken(token: String, refreshToken: String)    /*save token in sharedPreference*/
}