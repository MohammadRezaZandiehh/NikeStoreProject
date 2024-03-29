package com.example.nikestoreproject.data.repo.user.ds

import android.content.SharedPreferences
import com.example.nikestoreproject.data.model.MessageResponse
import com.example.nikestoreproject.data.TokenResponse
import com.example.nikestoreproject.data.TokenContainer
import io.reactivex.Single

class UserLocalDataSource (val sharedPreferences: SharedPreferences): UserDataSource {
    override fun login(username: String, password: String): Single<TokenResponse> {
        TODO("Not yet implemented")
    }

    override fun signUp(username: String, password: String): Single<MessageResponse> {
        TODO("Not yet implemented")
    }

    override fun loadToken() {
        TokenContainer.update(
            sharedPreferences.getString("access_token", null),
            sharedPreferences.getString("refresh_token", null)
        )
    }

    override fun saveToken(token: String, refreshToken: String) {
        sharedPreferences.edit().apply {
            putString("access_token", token)
            putString("refresh_token", refreshToken)
        }.apply()
    }

    override fun saveUsername(username: String) {
        sharedPreferences.edit().apply {
            putString("username", username)
        }.apply()
    }

    override fun getUserName(): String = sharedPreferences.getString("username", "") ?: ""

    override fun signOut() = sharedPreferences.edit().apply {
        clear()
    }.apply()
}