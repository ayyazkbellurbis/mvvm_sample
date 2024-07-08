package com.sample.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sample.domain.model.Model

@Dao
interface ModelDao {
    @Query("SELECT * FROM model WHERE id = :id")
    suspend fun getModelById(id: String): Model?

    @Insert
    suspend fun insertModel(model: Model)
}