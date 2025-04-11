package com.example.ass2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subjects")
data class TaskEntity2(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val subject: String,
    val score: Double
)
