package com.example.ass2.data

// 任务实体
data class TaskEntity(
    val id: Int,
    val title: String,
    val deadline: String,
    val description: String,
    val isCompleted: Boolean
)

// 科目及分数实体
data class TaskEntity2(
    val id: Int,
    val subject: String,
    val score: Double
)

// 定义任务仓库接口
interface ITaskRepository {
    suspend fun getAllTasks(): List<TaskEntity>
    suspend fun insertTask(task: TaskEntity)
    suspend fun updateTask(task: TaskEntity)
    suspend fun deleteTask(task: TaskEntity)
}

// 定义科目仓库接口
interface ISubjectRepository {
    suspend fun getAllSubjects(): List<TaskEntity2>
    suspend fun insertSubject(subject: TaskEntity2)
    suspend fun deleteSubject(subject: TaskEntity2)
}
