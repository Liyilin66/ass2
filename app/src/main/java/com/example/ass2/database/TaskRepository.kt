package com.example.ass2.data

class TaskRepository(private val dao: TaskDao) {

    suspend fun getAllTasks(): List<TaskEntity> = dao.getAllTasks()

    suspend fun insertTask(task: TaskEntity) = dao.insertTask(task)

    suspend fun updateTask(task: TaskEntity) = dao.updateTask(task)

    suspend fun deleteTask(task: TaskEntity) = dao.deleteTask(task)
}

class SubjectRepository(private val dao: TaskDao2) {
    suspend fun getAllSubjects(): List<TaskEntity2> = dao.getAllSubjects()

    suspend fun insertSubject(subject: TaskEntity2) = dao.insertSubject(subject)

    suspend fun updateSubject(subject: TaskEntity2) = dao.updateSubject(subject)

    suspend fun deleteSubject(subject: TaskEntity2) = dao.deleteSubject(subject)
}
