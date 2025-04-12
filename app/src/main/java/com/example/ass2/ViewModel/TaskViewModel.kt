package com.example.ass2.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ass2.data.TaskDatabase
import com.example.ass2.data.TaskEntity
import com.example.ass2.data.TaskRepository
import com.example.ass2.data.SubjectDatabase
import com.example.ass2.data.TaskEntity2
import com.example.ass2.data.SubjectRepository
import com.example.ass2.data.AdvancedFeatureRepository  // 新增网络仓库的引用
import com.example.ass2.network.AdvancedResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(val exception: Exception) : Result<Nothing>()
}

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    // -------------------------
    // Task（任务）相关
    // -------------------------
    private val taskRepository: TaskRepository
    private val _taskList = MutableStateFlow<List<TaskEntity>>(emptyList())
    val taskList: StateFlow<List<TaskEntity>> get() = _taskList

    // -------------------------
    // Subject（科目及分数）相关
    // -------------------------
    private val subjectRepository: SubjectRepository
    private val _subjectList = MutableStateFlow<List<TaskEntity2>>(emptyList())
    val subjectList: StateFlow<List<TaskEntity2>> get() = _subjectList

    // -------------------------
    // 网络高级功能相关
    // -------------------------
    private val advancedFeatureRepository = AdvancedFeatureRepository()
    var advancedFeatures: List<AdvancedResponse> = emptyList()
        private set

    init {
        // 初始化 Task 数据库和 Repository
        val taskDb = TaskDatabase.getDatabase(application)
        taskRepository = TaskRepository(taskDb.taskDao())
        refreshTasks()

        // 初始化 Subject 数据库和 Repository
        val subjectDb = SubjectDatabase.getDatabase(application)
        subjectRepository = SubjectRepository(subjectDb.taskDao2())
        refreshSubjects()
    }

    // ----------- Task 相关操作 -----------
    fun refreshTasks() {
        viewModelScope.launch {
            _taskList.value = taskRepository.getAllTasks()
        }
    }

    fun addTask(title: String, deadline: String, description: String) {
        viewModelScope.launch {
            val newTask = TaskEntity(
                title = title,
                deadline = deadline,
                description = description,
                isCompleted = false
            )
            taskRepository.insertTask(newTask)
            refreshTasks()
        }
    }

    fun toggleTask(task: TaskEntity) {
        viewModelScope.launch {
            val updatedTask = task.copy(isCompleted = !task.isCompleted)
            taskRepository.updateTask(updatedTask)
            refreshTasks()
        }
    }

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
            refreshTasks()
        }
    }

    fun deleteSubject(subject: TaskEntity2) {
        viewModelScope.launch {
            subjectRepository.deleteSubject(subject)
            refreshSubjects()
        }
    }

    // 使用 AdvancedFeatureRepository 获取网络数据（高级功能）
    // Using fold to handle success and failure
    fun loadAdvancedFeatures(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = advancedFeatureRepository.fetchAdvancedFeatures()
            result.fold(
                onSuccess = { data ->
                    advancedFeatures = data
                    onResult(true)
                    Log.d("TaskViewModel", "Loaded advanced features: $data")
                },
                onFailure = { error ->
                    Log.e("TaskViewModel", "Failed to load advanced features", error)
                    onResult(false)
                }
            )
        }
    }


    // ----------- Subject（科目相关操作）-----------
    fun refreshSubjects() {
        viewModelScope.launch {
            _subjectList.value = subjectRepository.getAllSubjects()
        }
    }

    fun addSubject(subject: String, score: Double) {
        viewModelScope.launch {
            subjectRepository.insertSubject(TaskEntity2(subject = subject, score = score))
            refreshSubjects()
        }
    }
}
