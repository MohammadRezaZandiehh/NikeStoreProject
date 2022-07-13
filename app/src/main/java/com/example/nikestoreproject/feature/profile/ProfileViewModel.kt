package com.example.nikestoreproject.feature.profile

import com.example.nikestoreproject.common.NikeViewModel
import com.example.nikestoreproject.data.TokenContainer
import com.example.nikestoreproject.data.repo.UserRepository

class ProfileViewModel(val userRepository: UserRepository): NikeViewModel() {
    val username: String
        get() = userRepository.getUserName()

    val isSignedIn: Boolean
        get() = TokenContainer.token != null

    fun signOut() = userRepository.signOut()
}