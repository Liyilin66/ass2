package com.example.ass2.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ass2.data.TaskDatabase
import com.example.ass2.data.TaskEntity
import com.example.ass2.data.TaskRepository
import com.example.ass2.data.SubjectDatabase
import com.example.ass2.data.TaskEntity2
import com.example.ass2.data.SubjectRepository
import com.example.ass2.network.AdvancedResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.ass2.api.ApiClient

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

    // 网络加载的高级功能数据
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
    // 从数据库中刷新任务列表
    fun refreshTasks() {
        viewModelScope.launch {
            _taskList.value = taskRepository.getAllTasks()
        }
    }

    // 添加任务
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

    // 切换任务完成状态
    fun toggleTask(task: TaskEntity) {
        viewModelScope.launch {
            val updatedTask = task.copy(isCompleted = !task.isCompleted)
            taskRepository.updateTask(updatedTask)
            refreshTasks()
        }
    }

    // 删除任务
    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
            refreshTasks()
        }
    }
    // 在 TaskViewModel 中添加 Subject 相关的删除功能
    fun deleteSubject(subject: TaskEntity2) {
        viewModelScope.launch {
            subjectRepository.deleteSubject(subject)
            refreshSubjects()
        }
    }

    // 加载网络数据（高级功能）
    fun loadAdvancedFeatures(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = ApiClient.service.getAdvancedFeatures()
                if (response.isSuccessful && response.body() != null) {
                    advancedFeatures = response.body()!!
                    onResult(true)
                } else {
                    onResult(false)
                }
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    // ----------- Subject（科目）相关操作 -----------
    // 刷新科目数据
    fun refreshSubjects() {
        viewModelScope.launch {
            _subjectList.value = subjectRepository.getAllSubjects()
        }
    }

    // 添加科目（根据需要可扩展更新、删除等操作）
    fun addSubject(subject: String, score: Double) {
        viewModelScope.launch {
            subjectRepository.insertSubject(TaskEntity2(subject = subject, score = score))
            refreshSubjects()
        }
    }
}
