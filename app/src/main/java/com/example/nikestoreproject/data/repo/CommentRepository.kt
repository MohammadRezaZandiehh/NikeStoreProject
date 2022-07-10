package com.example.nikestoreproject.data.repo

import com.example.nikestoreproject.data.model.Comment
import io.reactivex.Single


interface CommentRepository {

    fun getAll(productId: Int): Single<List<Comment>>

    fun insert() : Single<Comment>
}