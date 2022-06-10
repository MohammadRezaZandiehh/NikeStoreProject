package com.example.nikestoreproject.feature.auth

import com.example.nikestoreproject.data.repo.UserRepository
import com.sevenlearn.nikestore.common.NikeView
import com.sevenlearn.nikestore.common.NikeViewModel
import io.reactivex.Completable

class AuthViewModel(private val userRepository: UserRepository): NikeViewModel() {

    fun logIn(email: String, password: String):Completable{
        progressBarLiveData.value = true
        return userRepository.login(email, password).doFinally {
            progressBarLiveData.postValue(false)
        }
    }

    fun signUp(email: String, password: String):Completable{
        progressBarLiveData.value = true
        return userRepository.signUp(email, password).doFinally {
            progressBarLiveData.value = false
        }
    }
}