package com.example.nikestoreproject.data.repo.source

import com.example.nikestoreproject.data.model.MessageResponse
import com.example.nikestoreproject.data.TokenResponse
import com.example.nikestoreproject.services.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.Single


const val CLIENT_ID = 2
const val CLIENT_SECRET = "kyj1c9sVcksqGU4scMX7nLDalkjp2WoqQEf8PKAC"

class UserRemoteDataSource(val apiService: ApiService) : UserDataSource {
    override fun login(username: String, password: String): Single<TokenResponse> {
        return apiService.logIn(JsonObject().apply {
            addProperty("username", username)
            addProperty("password", password)
            addProperty("grant_type", "password")
            addProperty("client_id", CLIENT_ID)
            addProperty("client_secret", CLIENT_SECRET)
        })
    }

    override fun signUp(username: String, password: String): Single<MessageResponse> {
        return apiService.signUp(JsonObject().apply {
            addProperty("username", username)
            addProperty("password", password)
        })
    }

    override fun loadToken() {
        TODO("Not yet implemented")
    }

    override fun saveToken(token: String, refreshToken: String) {
        TODO("Not yet implemented")
    }

    override fun saveUsername(username: String) {
        TODO("Not yet implemented")
    }

    override fun getUserName(): String {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        TODO("Not yet implemented")
    }


}