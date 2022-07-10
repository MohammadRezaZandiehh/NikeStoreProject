package com.example.nikestoreproject.data.repo

import com.example.nikestoreproject.data.model.Comment
import com.example.nikestoreproject.data.repo.source.CommentDataSource
import io.reactivex.Single

class CommentRepositoryImpl (val commentDataSource: CommentDataSource): CommentRepository{
    override fun getAll(productId: Int): Single<List<Comment>> = commentDataSource.getAll(productId)

    override fun insert(): Single<Comment> {
        TODO("Not yet implemented")
    }
}