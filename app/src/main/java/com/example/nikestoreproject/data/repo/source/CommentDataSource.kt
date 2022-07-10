package com.example.nikestoreproject.data.repo.source

import com.example.nikestoreproject.data.model.Comment
import io.reactivex.Single


interface CommentDataSource {

    fun getAll(productId: Int): Single<List<Comment>>

    fun insert() : Single<Comment>
}