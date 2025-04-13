package com.example.ass2

import kotlinx.coroutines.test.runTest
import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ass2.data.ITaskRepository
import com.example.ass2.data.ISubjectRepository
import com.example.ass2.data.IAdvancedFeatureRepository
import com.example.ass2.data.TaskEntity
import com.example.ass2.data.TaskEntity2
import com.example.ass2.network.AdvancedResponse
import com.example.ass2.ViewModel.OperationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

// ----- Fake Repository 实现 -----
// Fake TaskRepository 的实现（内存存储，模拟 id 自动生成）
class FakeTaskRepository : ITaskRepository {
    val tasks = mutableListOf<TaskEntity>()
    override suspend fun getAllTasks(): List<TaskEntity> = tasks.toList()
    override suspend fun insertTask(task: TaskEntity) {
        // 模拟 id 生成
        val newTask = task.copy(id = tasks.size + 1)
        tasks.add(newTask)
    }
    override suspend fun updateTask(task: TaskEntity) {
        val index = tasks.indexOfFirst { it.id == task.id }
        if (index != -1) tasks[index] = task
    }
    override suspend fun deleteTask(task: TaskEntity) {
        tasks.removeIf { it.id == task.id }
    }
}

// Fake SubjectRepository 的实现
class FakeSubjectRepository : ISubjectRepository {
    val subjects = mutableListOf<TaskEntity2>()
    override suspend fun getAllSubjects(): List<TaskEntity2> = subjects.toList()
    override suspend fun insertSubject(subject: TaskEntity2) {
        val newSubject = subject.copy(id = subjects.size + 1)
        subjects.add(newSubject)
    }
    override suspend fun deleteSubject(subject: TaskEntity2) {
        subjects.removeIf { it.id == subject.id }
    }
}

// Fake AdvancedFeatureRepository 的实现，用于模拟网络请求
class FakeAdvancedFeatureRepository : IAdvancedFeatureRepository {
    // 控制是否返回失败
    var shouldFail = false
    override suspend fun fetchAdvancedFeatures(): OperationResult<List<AdvancedResponse>> {
        return if (shouldFail) {
            OperationResult.Failure(Exception("Network Error"))
        } else {
            // 构造一条虚拟网络数据
            OperationResult.Success(listOf(AdvancedResponse(dummy = "dummyData")))
        }
    }
}

// ----- Testable ViewModel -----
// 为了方便测试，我们构造一个可注入 fake 仓库的 ViewModel，逻辑与原始 TaskViewModel 保持一致
class TestableTaskViewModel(
    application: Application,
    private val fakeTaskRepository: ITaskRepository,
    private val fakeSubjectRepository: ISubjectRepository,
    private val fakeAdvancedFeatureRepository: IAdvancedFeatureRepository
) : AndroidViewModel(application) {

    // -------------------------
    // Task（任务）相关
    // -------------------------
    private val _taskList = MutableStateFlow<List<TaskEntity>>(emptyList())
    val taskList: StateFlow<List<TaskEntity>> get() = _taskList

    // -------------------------
    // Subject（科目及分数）相关
    // -------------------------
    private val _subjectList = MutableStateFlow<List<TaskEntity2>>(emptyList())
    val subjectList: StateFlow<List<TaskEntity2>> get() = _subjectList

    // -------------------------
    // 网络高级功能相关
    // -------------------------
    var advancedFeatures: List<AdvancedResponse> = emptyList()
        private set

    init {
        refreshTasks()
        refreshSubjects()
    }

    // ----------- Task 相关操作 -----------
    fun refreshTasks() {
        viewModelScope.launch {
            _taskList.value = fakeTaskRepository.getAllTasks()
        }
    }

    fun addTask(title: String, deadline: String, description: String) {
        viewModelScope.launch {
            val newTask = TaskEntity(
                id = 0, // id 由 fake 仓库生成
                title = title,
                deadline = deadline,
                description = description,
                isCompleted = false
            )
            fakeTaskRepository.insertTask(newTask)
            refreshTasks()
        }
    }

    fun toggleTask(task: TaskEntity) {
        viewModelScope.launch {
            val updatedTask = task.copy(isCompleted = !task.isCompleted)
            fakeTaskRepository.updateTask(updatedTask)
            refreshTasks()
        }
    }

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            fakeTaskRepository.deleteTask(task)
            refreshTasks()
        }
    }

    // ----------- Subject（科目相关操作）-----------
    fun refreshSubjects() {
        viewModelScope.launch {
            _subjectList.value = fakeSubjectRepository.getAllSubjects()
        }
    }

    fun addSubject(subject: String, score: Double) {
        viewModelScope.launch {
            val newSubject = TaskEntity2(
                id = 0, // 由 fake 仓库生成 id
                subject = subject,
                score = score
            )
            fakeSubjectRepository.insertSubject(newSubject)
            refreshSubjects()
        }
    }

    fun deleteSubject(subject: TaskEntity2) {
        viewModelScope.launch {
            fakeSubjectRepository.deleteSubject(subject)
            refreshSubjects()
        }
    }

    // 使用 FakeAdvancedFeatureRepository 获取网络数据（高级功能）
    fun loadAdvancedFeatures(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = fakeAdvancedFeatureRepository.fetchAdvancedFeatures()
            result.fold(
                onSuccess = { data ->
                    advancedFeatures = data
                    onResult(true)
                },
                onFailure = { error ->
                    onResult(false)
                }
            )
        }
    }
}

// ----- 测试类 -----
@OptIn(ExperimentalCoroutinesApi::class)
class TaskViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: TestableTaskViewModel
    private lateinit var fakeTaskRepository: FakeTaskRepository
    private lateinit var fakeSubjectRepository: FakeSubjectRepository
    private lateinit var fakeAdvancedFeatureRepository: FakeAdvancedFeatureRepository

    // 设置测试专用的协程调度器
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeTaskRepository = FakeTaskRepository()
        fakeSubjectRepository = FakeSubjectRepository()
        fakeAdvancedFeatureRepository = FakeAdvancedFeatureRepository()

        // 直接创建一个 Application 实例，而不是使用 ApplicationProvider
        val context = Application()
        viewModel = TestableTaskViewModel(context, fakeTaskRepository, fakeSubjectRepository, fakeAdvancedFeatureRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testAddTask() = runTest {
        val initialSize = viewModel.taskList.value.size
        viewModel.addTask("Unit Test Task", "2025-12-31", "Testing task addition")
        testDispatcher.scheduler.advanceUntilIdle()
        val tasks = viewModel.taskList.value
        assertEquals(initialSize + 1, tasks.size)
        assertEquals("Unit Test Task", tasks.last().title)
    }

    @Test
    fun testToggleTask() = runTest {
        viewModel.addTask("Toggle Task", "2025-12-31", "Testing toggle")
        testDispatcher.scheduler.advanceUntilIdle()
        val task = viewModel.taskList.value.last()
        val initialStatus = task.isCompleted
        viewModel.toggleTask(task)
        testDispatcher.scheduler.advanceUntilIdle()
        val updatedTask = viewModel.taskList.value.last()
        assertEquals(!initialStatus, updatedTask.isCompleted)
    }

    @Test
    fun testDeleteTask() = runTest {
        viewModel.addTask("Delete Task", "2025-12-31", "Testing delete")
        testDispatcher.scheduler.advanceUntilIdle()
        val sizeAfterAdd = viewModel.taskList.value.size
        val task = viewModel.taskList.value.last()
        viewModel.deleteTask(task)
        testDispatcher.scheduler.advanceUntilIdle()
        val sizeAfterDelete = viewModel.taskList.value.size
        assertEquals(sizeAfterAdd - 1, sizeAfterDelete)
    }

    @Test
    fun testAddAndDeleteSubject() = runTest {
        viewModel.addSubject("Math", 95.0)
        testDispatcher.scheduler.advanceUntilIdle()
        val subjects = viewModel.subjectList.value
        assertEquals(1, subjects.size)
        val subject = subjects.last()
        assertEquals("Math", subject.subject)
        viewModel.deleteSubject(subject)
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(0, viewModel.subjectList.value.size)
    }

    @Test
    fun testLoadAdvancedFeaturesSuccess() = runTest {
        fakeAdvancedFeatureRepository.shouldFail = false
        var resultStatus = false
        viewModel.loadAdvancedFeatures { status ->
            resultStatus = status
        }
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(resultStatus)
        assertFalse(viewModel.advancedFeatures.isEmpty())
    }

    @Test
    fun testLoadAdvancedFeaturesFailure() = runTest {
        fakeAdvancedFeatureRepository.shouldFail = true
        var resultStatus = true
        viewModel.loadAdvancedFeatures { status ->
            resultStatus = status
        }
        testDispatcher.scheduler.advanceUntilIdle()
        assertFalse(resultStatus)
    }
}
