package com.example.nikestoreproject.common

import com.example.nikestoreproject.R
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.HttpException
import timber.log.Timber

class NikeExceptionMapper {

    companion object {
        fun map(throwable: Throwable): NikeException {
            if (throwable is HttpException) {
                try {
                    val errorJsonObject = JSONObject(throwable.response()?.errorBody()!!.string())
                    val errorMessage=errorJsonObject.getString("message")
                    return NikeException(if (throwable.code()==401) NikeException.Type.AUTH else NikeException.Type.SIMPLE,serverMessage = errorMessage)                  // we get message that has been sent from server
                } catch (exception: Exception) {
                    Timber.e(exception)
                }
            }
            return NikeException(NikeException.Type.SIMPLE, R.string.onKnownError)                  //if our exception didn't serverException or any thing like that we return a message like : onKnownError
        }
    }
}