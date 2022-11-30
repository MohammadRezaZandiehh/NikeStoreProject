package com.example.nikestoreproject.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nikestoreproject.data.model.Product
import com.example.nikestoreproject.data.repo.product.ds.ProductLocalDataSource

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductLocalDataSource
}