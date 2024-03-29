package com.example.nikestoreproject.data.repo.comment.ds

import com.example.nikestoreproject.data.model.Comment
import com.example.nikestoreproject.services.http.ApiService
import io.reactivex.Single

class CommentRemoteDataSource(val apiService: ApiService): CommentDataSource {
    override fun getAll(productId: Int): Single<List<Comment>> = apiService.getComments(productId)

    override fun insert(): Single<Comment> {
        TODO("Not yet implemented")
    }}