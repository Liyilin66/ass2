package com.example.ass2.data

import androidx.room.*

@Dao
interface TaskDao2 {
    @Query("SELECT * FROM subjects")
    suspend fun getAllSubjects(): List<TaskEntity2>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubject(subject: TaskEntity2)

    @Update
    suspend fun updateSubject(subject: TaskEntity2)

    @Delete
    suspend fun deleteSubject(subject: TaskEntity2)
}
