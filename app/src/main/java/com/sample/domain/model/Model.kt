package com.sample.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Model(
    @PrimaryKey val id: Int,
    val name: String
)