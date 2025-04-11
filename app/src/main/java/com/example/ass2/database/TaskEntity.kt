package com.example.ass2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val deadline: String,
    val description: String,
    val isCompleted: Boolean = false
)
