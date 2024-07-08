package com.sample.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sample.domain.model.Model

@Database(entities = [Model::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun modelDao(): ModelDao
}