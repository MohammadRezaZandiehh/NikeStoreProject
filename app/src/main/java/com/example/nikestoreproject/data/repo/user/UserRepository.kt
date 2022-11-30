package com.example.nikestoreproject.data.repo.user

import io.reactivex.Completable

interface UserRepository {

    fun login(username: String, password: String): Completable
    fun signUp(username: String, password: String): Completable
    fun loadToken()/*daghighe 15 ye tozihi dad k in method chie*/
    fun getUserName():String
    fun signOut()
}