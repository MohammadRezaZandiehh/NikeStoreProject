package com.example.nikestoreproject.data.repo

import com.example.nikestoreproject.data.TokenResponse
import com.example.nikestoreproject.data.repo.source.UserDataSource
import io.reactivex.Completable


class UserRepositoryImpl(
    val userLocalDataSource: UserDataSource,
    val userRemoteDataSource: UserDataSource
) : UserRepository{

    override fun login(username: String, password: String): Completable {
        TODO("Not yet implemented")
    }

    override fun signUp(username: String, password: String): Completable {
        TODO("Not yet implemented")
    }

    override fun loadToken() {
        TODO("Not yet implemented")
    }

    fun onSuccessfulLogin(tokenResponse: TokenResponse) {
        TokenContainer.update(tokenResponse.access_token, tokenResponse.refresh_token)
        userLocalDataSource.saveToken(tokenResponse.access_token, tokenResponse.refresh_token)
    }
}