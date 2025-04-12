@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.ass2.Screens

import androidx.compose.ui.text.TextStyle
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ass2.Models.PriorityTask
import com.example.ass2.ViewModel.TaskViewModel
import com.example.ass2.data.TaskEntity
import com.example.ass2.data.TaskEntity2
import com.example.ass2.network.AdvancedResponse
import java.util.Calendar

// 分类前缀常量，用于区分不同任务类型
private const val CATEGORY_URGENT_IMPORTANT = "Urgent & Important:"
private const val CATEGORY_URGENT_NOT_IMPORTANT = "Urgent but Not Important:"
private const val CATEGORY_IMPORTANT_NOT_URGENT = "Important Not Urgent:"
private const val CATEGORY_STUDY_REVIEW = "Study & Review:"

// ------------------ 主界面及子组件 ------------------

@Composable
fun StudyManagementScreen(
    onNavigateToUrgent: () -> Unit,
    onNavigateToNotUrgent: () -> Unit,
    onNavigateToImportant: () -> Unit,
    onNavigateToStudyAndReview: () -> Unit,
    onNavigateToStudyAdvice: () -> Unit,  // 新增
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF512DA8),
                        Color(0xFF7E57C2),
                        Color(0xFFAB47BC)
                    )
                )
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                "📚 Study & Time Manager",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 40.8.sp,
                textAlign = TextAlign.Center
            )
        }
        item { Spacer(modifier = Modifier.height(20.dp)) }
        item { HeaderSection() }
        item { Spacer(modifier = Modifier.height(20.dp)) }
        item { SectionTitle("📌 Priority Matrix") }
        item {
            PriorityMatrix(
                onNavigateToUrgent = onNavigateToUrgent,
                onNavigateToNotUrgent = onNavigateToNotUrgent,
                onNavigateToImportant = onNavigateToImportant,
                onNavigateToStudyAndReview = onNavigateToStudyAndReview,
                onNavigateToStudyAdvice = onNavigateToStudyAdvice  // 新增
            )
        }
    }
}

@Composable
fun HeaderSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Good morning! Ready to tackle your tasks?",
            fontSize = 22.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("📆 Upcoming deadlines", fontSize = 18.sp, color = Color.White)
        Text("⏳ Uncompleted tasks", fontSize = 18.sp, color = Color.White)
        Text("🎯 Weekly study goal", fontSize = 18.sp, color = Color.White)
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun PriorityMatrix(
    onNavigateToUrgent: () -> Unit,
    onNavigateToNotUrgent: () -> Unit,
    onNavigateToImportant: () -> Unit,
    onNavigateToStudyAndReview: () -> Unit,
    onNavigateToStudyAdvice: () -> Unit  // 新增
) {
    val priorityTasks = listOf(
        PriorityTask(
            title = "🔴 Urgent & Important",
            subtitle = "⚠ Due today/tomorrow",
            description = "Complete immediately",
            backgroundBrush = Brush.horizontalGradient(
                colors = listOf(Color(0xFFC62828), Color(0xFFE57373))
            ),
            onClick = onNavigateToUrgent
        ),
        PriorityTask(
            title = "🔵 Urgent but Not Important",
            subtitle = "Less critical tasks",
            description = "Tap to view non-urgent tasks",
            backgroundBrush = Brush.horizontalGradient(
                colors = listOf(Color(0xFF1565C0), Color(0xFF64B5F6))
            ),
            onClick = onNavigateToNotUrgent
        ),
        PriorityTask(
            title = "🟡 Important Not Urgent",
            subtitle = "📅 Long-term projects",
            description = "Plan ahead & schedule",
            backgroundBrush = Brush.horizontalGradient(
                colors = listOf(Color(0xFFFFA000), Color(0xFFFFD54F))
            ),
            onClick = onNavigateToImportant
        ),
        PriorityTask(
            title = "⚪ Study & Review",
            subtitle = "📚 Optional reading & practice",
            description = "",
            backgroundBrush = Brush.horizontalGradient(
                colors = listOf(Color(0xFF757575), Color(0xFFE0E0E0))
            ),
            onClick = onNavigateToStudyAndReview
        ),
        // 新增 Study Advice 卡片
        PriorityTask(
            title = "💡 Study Advice",
            subtitle = "",
            description = "",
            backgroundBrush = Brush.horizontalGradient(
                colors = listOf(Color(0xFF4CAF50), Color(0xFF81C784))
            ),
            onClick = onNavigateToStudyAdvice
        )
    )
    Column {
        priorityTasks.forEach { task ->
            StudyTaskCard(
                title = task.title,
                subtitle = task.subtitle,
                description = task.description,
                backgroundBrush = task.backgroundBrush,
                onClick = task.onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }
    }
}


@Composable
fun StudyTaskCard(
    title: String,
    subtitle: String,
    description: String,
    backgroundBrush: Brush,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(enabled = onClick != null) { onClick?.invoke() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .background(backgroundBrush, RoundedCornerShape(16.dp))
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 19.sp, color = Color.White)
                Spacer(modifier = Modifier.height(6.dp))
                Text(subtitle, fontSize = 17.sp, color = Color.White.copy(alpha = 0.9f))
                Spacer(modifier = Modifier.height(4.dp))
                Text(description, fontSize = 16.sp, color = Color.White.copy(alpha = 0.8f))
            }
        }
    }
}

// ------------------ Toggleable Task Card Components ------------------

@Composable
fun ToggleableTaskCardLarge(
    title: String,
    deadline: String,
    description: String,
    isCompleted: Boolean,
    onToggle: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onToggle() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = if (isCompleted) Color(0xFFB0BEC5) else Color.White)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = deadline, fontSize = 16.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = description, fontSize = 16.sp, color = Color.Black.copy(alpha = 0.8f))
            }
            IconButton(onClick = onDelete) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
            }
            if (isCompleted) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Completed", tint = Color.Green)
            }
        }
    }
}

@Composable
fun ToggleableTaskCardMedium(
    title: String,
    deadline: String,
    description: String,
    isCompleted: Boolean,
    onToggle: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onToggle() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = if (isCompleted) Color(0xFFB0BEC5) else Color.White)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = deadline, fontSize = 14.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = description, fontSize = 14.sp, color = Color.Black.copy(alpha = 0.8f))
            }
            IconButton(onClick = onDelete) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
            }
            if (isCompleted) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Completed", tint = Color.Green)
            }
        }
    }
}

// ------------------ Urgent & Important Screen ------------------

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UrgentAndImportantScreen(onBackToMain: () -> Unit, modifier: Modifier = Modifier) {
    val taskViewModel: TaskViewModel = viewModel()
    val allTasks by taskViewModel.taskList.collectAsState(initial = emptyList())
    // 根据任务标题前缀过滤出紧急且重要的任务
    val tasks = allTasks.filter { it.title.startsWith(CATEGORY_URGENT_IMPORTANT) }
    // 根据 deadline 排序（如果 deadline 不为空，则转换为 LocalDate，否则归为最大日期值，使其排在后面）
    val sortedTasks = tasks.sortedBy { task ->
        if(task.deadline.isNotBlank()) {
            try {
                java.time.LocalDate.parse(task.deadline)
            } catch (e: Exception) {
                java.time.LocalDate.MAX
            }
        } else java.time.LocalDate.MAX
    }

    // 三个输入字段的状态：标题、截止日期、描述
    var newTaskTitle by remember { mutableStateOf("") }
    var newTaskDeadline by remember { mutableStateOf("") }
    var newTaskDescription by remember { mutableStateOf("") }
    // 控制日期选择器显示与否
    var showDatePicker by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // 若需要显示日期选择器则调用 DatePickerDialog
    if (showDatePicker) {
        val calendar = java.util.Calendar.getInstance()
        android.app.DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                // 注意：month 从0开始计数，格式化输出为“yyyy-MM-dd”
                newTaskDeadline = "$year-${(month + 1).toString().padStart(2, '0')}-${dayOfMonth.toString().padStart(2, '0')}"
                showDatePicker = false
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    Scaffold(
        bottomBar = {
            Column(modifier = Modifier.padding(16.dp)) {
                // Title 输入框，标签文字设置为黑色且加粗
                OutlinedTextField(
                    value = newTaskTitle,
                    onValueChange = { newTaskTitle = it },
                    label = {
                        Text("Title", color = Color.Black, fontWeight = FontWeight.Bold)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = Color.Black),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        cursorColor = Color.Black
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Deadline 输入框，标签文字设置为黑色且加粗
                OutlinedTextField(
                    value = newTaskDeadline,
                    onValueChange = { },
                    label = {
                        Text("Deadline", color = Color.Black, fontWeight = FontWeight.Bold)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDatePicker = true },
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Select Date",
                            modifier = Modifier.clickable { showDatePicker = true }
                        )
                    },
                    textStyle = TextStyle(color = Color.Black),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        cursorColor = Color.Black
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Description 输入框，标签文字设置为黑色且加粗
                OutlinedTextField(
                    value = newTaskDescription,
                    onValueChange = { newTaskDescription = it },
                    label = {
                        Text("Description", color = Color.Black, fontWeight = FontWeight.Bold)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = Color.Black),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        cursorColor = Color.Black
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                // 添加任务按钮
                Button(
                    onClick = {
                        if (newTaskTitle.isNotBlank()) {
                            taskViewModel.addTask(
                                "$CATEGORY_URGENT_IMPORTANT $newTaskTitle",
                                newTaskDeadline,
                                newTaskDescription
                            )
                            // 重置输入框状态
                            newTaskTitle = ""
                            newTaskDeadline = ""
                            newTaskDescription = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Task")
                }
                Spacer(modifier = Modifier.height(8.dp))
                // 返回主界面按钮
                Button(
                    onClick = onBackToMain,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text("Back to Main", color = Color.Black, fontSize = 12.sp)
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFC62828), Color(0xFFE57373))
                    )
                )
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            item {
                Text(
                    "🔥 Urgent & Important Tasks",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            // 使用排序后的任务列表进行显示
            items(sortedTasks) { task ->
                ToggleableTaskCardLarge(
                    title = task.title.removePrefix("$CATEGORY_URGENT_IMPORTANT "),
                    deadline = task.deadline,
                    description = task.description,
                    isCompleted = task.isCompleted,
                    onToggle = { taskViewModel.toggleTask(task) },
                    onDelete = { taskViewModel.deleteTask(task) }
                )
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}


// ------------------ Urgent but Not Important Screen ------------------

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UrgentNotImportantScreen(onBackToMain: () -> Unit, modifier: Modifier = Modifier) {
    val taskViewModel: TaskViewModel = viewModel()
    val allTasks by taskViewModel.taskList.collectAsState(initial = emptyList())
    // 过滤标题以 CATEGORY_URGENT_NOT_IMPORTANT 开头的任务
    val tasks = allTasks.filter { it.title.startsWith(CATEGORY_URGENT_NOT_IMPORTANT) }
    // 根据 deadline 排序：若 deadline 为空，则认为其值为 LocalDate.MAX
    val sortedTasks = tasks.sortedBy { task ->
        if (task.deadline.isNotBlank()) {
            try {
                java.time.LocalDate.parse(task.deadline)
            } catch (e: Exception) {
                java.time.LocalDate.MAX
            }
        } else java.time.LocalDate.MAX
    }

    var newTaskTitle by remember { mutableStateOf("") }
    var newTaskDeadline by remember { mutableStateOf("") }
    var newTaskDescription by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (showDatePicker) {
        val calendar = java.util.Calendar.getInstance()
        android.app.DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                newTaskDeadline = "$year-${(month + 1).toString().padStart(2, '0')}-${dayOfMonth.toString().padStart(2, '0')}"
                showDatePicker = false
            },
            calendar.get(java.util.Calendar.YEAR),
            calendar.get(java.util.Calendar.MONTH),
            calendar.get(java.util.Calendar.DAY_OF_MONTH)
        ).show()
    }

    Scaffold(
        bottomBar = {
            Column(modifier = Modifier.padding(16.dp)) {
                // Title 输入框
                OutlinedTextField(
                    value = newTaskTitle,
                    onValueChange = { newTaskTitle = it },
                    label = {
                        Text("Title", color = Color.Black, fontWeight = FontWeight.Bold)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = Color.Black),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        cursorColor = Color.Black
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Deadline 输入框（只读，并添加点击事件打开日历选择器）
                OutlinedTextField(
                    value = newTaskDeadline,
                    onValueChange = { },
                    label = {
                        Text("Deadline", color = Color.Black, fontWeight = FontWeight.Bold)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDatePicker = true },
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Select Date",
                            modifier = Modifier.clickable { showDatePicker = true }
                        )
                    },
                    textStyle = TextStyle(color = Color.Black),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        cursorColor = Color.Black
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Description 输入框
                OutlinedTextField(
                    value = newTaskDescription,
                    onValueChange = { newTaskDescription = it },
                    label = {
                        Text("Description", color = Color.Black, fontWeight = FontWeight.Bold)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = Color.Black),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        cursorColor = Color.Black
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                // 添加任务按钮
                Button(
                    onClick = {
                        if (newTaskTitle.isNotBlank()) {
                            taskViewModel.addTask(
                                "$CATEGORY_URGENT_NOT_IMPORTANT $newTaskTitle",
                                newTaskDeadline,
                                newTaskDescription
                            )
                            newTaskTitle = ""
                            newTaskDeadline = ""
                            newTaskDescription = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Task")
                }
                Spacer(modifier = Modifier.height(8.dp))
                // 返回主界面按钮
                Button(
                    onClick = onBackToMain,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text("Back to Main", color = Color.Black, fontSize = 12.sp)
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF1565C0), Color(0xFF64B5F6))
                    )
                )
                .padding(paddingValues)
                .padding(horizontal = 12.dp, vertical = 16.dp)
        ) {
            item {
                Text(
                    "🔵 Urgent but Not Important Tasks",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            items(sortedTasks) { task ->
                ToggleableTaskCardMedium(
                    title = task.title.removePrefix("$CATEGORY_URGENT_NOT_IMPORTANT "),
                    deadline = task.deadline,
                    description = task.description,
                    isCompleted = task.isCompleted,
                    onToggle = { taskViewModel.toggleTask(task) },
                    onDelete = { taskViewModel.deleteTask(task) }
                )
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}



// ------------------ Important Not Urgent Screen ------------------

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ImportantNotUrgentScreen(onBackToMain: () -> Unit, modifier: Modifier = Modifier) {
    val taskViewModel: TaskViewModel = viewModel()
    val allTasks by taskViewModel.taskList.collectAsState(initial = emptyList())
    // 过滤标题以 CATEGORY_IMPORTANT_NOT_URGENT 开头的任务
    val tasks = allTasks.filter { it.title.startsWith(CATEGORY_IMPORTANT_NOT_URGENT) }
    // 根据 deadline 排序
    val sortedTasks = tasks.sortedBy { task ->
        if(task.deadline.isNotBlank()) {
            try {
                java.time.LocalDate.parse(task.deadline)
            } catch (e: Exception) {
                java.time.LocalDate.MAX
            }
        } else java.time.LocalDate.MAX
    }

    var newTaskTitle by remember { mutableStateOf("") }
    var newTaskDeadline by remember { mutableStateOf("") }
    var newTaskDescription by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (showDatePicker) {
        val calendar = java.util.Calendar.getInstance()
        android.app.DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                newTaskDeadline = "$year-${(month + 1).toString().padStart(2, '0')}-${dayOfMonth.toString().padStart(2, '0')}"
                showDatePicker = false
            },
            calendar.get(java.util.Calendar.YEAR),
            calendar.get(java.util.Calendar.MONTH),
            calendar.get(java.util.Calendar.DAY_OF_MONTH)
        ).show()
    }

    Scaffold(
        bottomBar = {
            Column(modifier = Modifier.padding(16.dp)) {
                // Title 输入框
                OutlinedTextField(
                    value = newTaskTitle,
                    onValueChange = { newTaskTitle = it },
                    label = {
                        Text("Title", color = Color.Black, fontWeight = FontWeight.Bold)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = Color.Black),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        cursorColor = Color.Black
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Deadline 输入框
                OutlinedTextField(
                    value = newTaskDeadline,
                    onValueChange = { },
                    label = {
                        Text("Deadline", color = Color.Black, fontWeight = FontWeight.Bold)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDatePicker = true },
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Select Date",
                            modifier = Modifier.clickable { showDatePicker = true }
                        )
                    },
                    textStyle = TextStyle(color = Color.Black),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        cursorColor = Color.Black
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Description 输入框
                OutlinedTextField(
                    value = newTaskDescription,
                    onValueChange = { newTaskDescription = it },
                    label = {
                        Text("Description", color = Color.Black, fontWeight = FontWeight.Bold)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = Color.Black),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        cursorColor = Color.Black
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                // 添加任务按钮
                Button(
                    onClick = {
                        if (newTaskTitle.isNotBlank()) {
                            taskViewModel.addTask(
                                "$CATEGORY_IMPORTANT_NOT_URGENT $newTaskTitle",
                                newTaskDeadline,
                                newTaskDescription
                            )
                            newTaskTitle = ""
                            newTaskDeadline = ""
                            newTaskDescription = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Task")
                }
                Spacer(modifier = Modifier.height(8.dp))
                // 返回主界面按钮
                Button(
                    onClick = onBackToMain,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text("Back to Main", color = Color.Black, fontSize = 12.sp)
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFFFA000), Color(0xFFFFD54F))
                    )
                )
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            item {
                Text(
                    "🟡 Important Not Urgent Tasks",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            items(sortedTasks) { task ->
                ToggleableTaskCardMedium(
                    title = task.title.removePrefix("$CATEGORY_IMPORTANT_NOT_URGENT "),
                    deadline = task.deadline,
                    description = task.description,
                    isCompleted = task.isCompleted,
                    onToggle = { taskViewModel.toggleTask(task) },
                    onDelete = { taskViewModel.deleteTask(task) }
                )
            }
        }
    }
}



// ------------------ Study & Review Page ------------------

@Composable
fun StudyAndReviewContent(onBack: () -> Unit) {
    // 使用 TaskViewModel（其中包含 Subject 相关的数据与方法）
    val taskViewModel: TaskViewModel = viewModel()
    // 获取科目及分数列表
    val subjects by taskViewModel.subjectList.collectAsState(initial = emptyList())
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF7986CB), Color(0xFF3F51B5))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp)
                .padding(bottom = 80.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Subject Review",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            // 使用已有的 ExamScoreBarChart 展示图表（保持不变）
            // 注意：如果图表数据需要和数据库同步，可自行调整代码
            ExamScoreBarChart(
                scores = subjects.associate { it.subject to it.score.toInt() }
            )
            Spacer(modifier = Modifier.height(16.dp))
            // 修改后 ScoreAnalysisSection 传入完整的科目列表以及添加与删除回调
            ScoreAnalysisSection(
                subjects = subjects,
                onDeleteSubject = { subjectEntity ->
                    taskViewModel.deleteSubject(subjectEntity)
                },
                onAddSubject = { subject, score ->
                    taskViewModel.addSubject(subject, score)
                }
            )
        }
        Button(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text("Back to Main", color = Color.Black, fontSize = 12.sp)
        }
    }
}


@Composable
fun ExamScoreBarChart(
    modifier: Modifier = Modifier,
    scores: Map<String, Int> = emptyMap()
) {
    Canvas(modifier = modifier
        .fillMaxWidth()
        .height(250.dp)
    ) {
        val leftMargin = 40.dp.toPx()
        val rightMargin = 16.dp.toPx()
        val topMargin = 16.dp.toPx()
        val bottomMargin = 60.dp.toPx()
        val chartWidth = size.width - leftMargin - rightMargin
        val chartHeight = size.height - topMargin - bottomMargin

        // 绘制 Y 轴及箭头
        drawLine(
            color = Color.Black,
            start = Offset(leftMargin, topMargin),
            end = Offset(leftMargin, size.height - bottomMargin),
            strokeWidth = 4f
        )
        val arrowSize = 8.dp.toPx()
        val yArrowPath = Path().apply {
            moveTo(leftMargin, topMargin)
            lineTo(leftMargin - arrowSize, topMargin + arrowSize)
            lineTo(leftMargin + arrowSize, topMargin + arrowSize)
            close()
        }
        drawPath(path = yArrowPath, color = Color.Black)

        // 绘制 X 轴及箭头
        drawLine(
            color = Color.Black,
            start = Offset(leftMargin, size.height - bottomMargin),
            end = Offset(size.width - rightMargin, size.height - bottomMargin),
            strokeWidth = 4f
        )
        val xArrowPath = Path().apply {
            moveTo(size.width - rightMargin, size.height - bottomMargin)
            lineTo(size.width - rightMargin - arrowSize, size.height - bottomMargin - arrowSize / 2)
            lineTo(size.width - rightMargin - arrowSize, size.height - bottomMargin + arrowSize / 2)
            close()
        }
        drawPath(path = xArrowPath, color = Color.Black)

        // 绘制柱状图（若数据库中没有数据，则不显示）
        val numberOfBars = scores.size
        if(numberOfBars > 0) {
            val spacing = 16.dp.toPx()
            val barWidth = (chartWidth - (numberOfBars + 1) * spacing) / numberOfBars
            val maxScore = scores.values.maxOrNull()?.toFloat() ?: 100f
            val entries = scores.entries.toList()
            entries.forEachIndexed { index, entry ->
                val x = leftMargin + spacing * (index + 1) + barWidth * index
                val barHeight = (entry.value / maxScore) * chartHeight
                val y = size.height - bottomMargin - barHeight

                drawRect(
                    color = Color.Cyan,
                    topLeft = Offset(x, y),
                    size = Size(barWidth, barHeight)
                )
                drawIntoCanvas { canvas ->
                    val paint = android.graphics.Paint().apply {
                        color = android.graphics.Color.BLACK
                        textSize = 40f
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                    canvas.nativeCanvas.drawText(
                        entry.key,
                        x + barWidth / 2,
                        size.height - bottomMargin / 2,
                        paint
                    )
                    canvas.nativeCanvas.drawText(
                        entry.value.toString(),
                        x + barWidth / 2,
                        y - 10f,
                        paint
                    )
                }
            }
        }

        // 绘制 Y 轴刻度标签
        drawIntoCanvas { canvas ->
            val tickPaint = android.graphics.Paint().apply {
                color = android.graphics.Color.BLACK
                textSize = 40f
                textAlign = android.graphics.Paint.Align.RIGHT
            }
            val maxScoreLabel = scores.values.maxOrNull()?.toInt() ?: 100
            canvas.nativeCanvas.drawText(
                maxScoreLabel.toString(),
                leftMargin - 8,
                topMargin + 40f,
                tickPaint
            )
            canvas.nativeCanvas.drawText(
                (maxScoreLabel / 2).toString(),
                leftMargin - 8,
                topMargin + chartHeight / 2 + 40f,
                tickPaint
            )
            canvas.nativeCanvas.drawText(
                "0",
                leftMargin - 8,
                size.height - bottomMargin,
                tickPaint
            )
        }
    }
}

@Composable
fun ScoreAnalysisSection(
    subjects: List<TaskEntity2>,
    onDeleteSubject: (TaskEntity2) -> Unit,
    onAddSubject: (String, Double) -> Unit
) {
    var newSubject by remember { mutableStateOf("") }
    var newScore by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Score Analysis",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        // 输入区域：输入科目与分数
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = newSubject,
                onValueChange = { newSubject = it },
                label = { Text("Subject") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = newScore,
                onValueChange = { newScore = it },
                label = { Text("Score") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    val scoreDouble = newScore.toDoubleOrNull()
                    if (newSubject.isNotBlank() && scoreDouble != null) {
                        onAddSubject(newSubject, scoreDouble)
                        newSubject = ""
                        newScore = ""
                    }
                }
            ) {
                Text("Add")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        // 列出所有科目的 ScoreAnalysisCard
        subjects.forEach { subjectEntity ->
            ScoreAnalysisCard(
                subject = subjectEntity.subject,
                score = subjectEntity.score.toInt(),
                onDelete = { onDeleteSubject(subjectEntity) }
            )
        }
    }
}


@Composable
fun ScoreAnalysisCard(
    subject: String,
    score: Int,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val feedback = when {
        score >= 90 -> "Excellent performance! You've mastered this subject. Keep up the fantastic work!"
        score in 80..89 -> "Good job overall! Your understanding is solid, but consider refining problem solving skills."
        else -> "Needs improvement. Review core concepts and practice more."
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        // 在同一行显示科目信息及删除按钮
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = subject,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Score: $score",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = feedback,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray
                )
            }
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Red
                )
            }
        }
    }
}

@Composable
fun StudyAdviceScreen(
    onBack: () -> Unit,
    viewModel: TaskViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    // 本地 state 用于保存网络加载后的高级功能数据
    var advancedFeatures by remember { mutableStateOf<List<AdvancedResponse>>(emptyList()) }

    // 页面启动时调用 loadAdvancedFeatures 加载数据
    LaunchedEffect(Unit) {
        viewModel.loadAdvancedFeatures { success ->
            if (success) {
                advancedFeatures = viewModel.advancedFeatures
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF90CAF9), // 浅蓝色
                        Color(0xFF64B5F6)  // 中蓝色
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(16.dp)
                .padding(bottom = 80.dp),  // 为底部按钮预留空间
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Study Advice",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            // 如果数据为空，则显示加载中
            if (advancedFeatures.isEmpty()) {
                Text(
                    text = "加载中...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    modifier = Modifier.padding(top = 16.dp)
                )
            } else {
                // 展示每个高级功能信息
                advancedFeatures.forEach { feature ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = feature.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = feature.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(4.dp))
//                        Text(
//                            text = if (feature.isEnabled) "启用" else "未启用",
//                            style = MaterialTheme.typography.labelSmall,
//                            color = Color.White
//                        )
                    }
                }
            }
            // 可根据需要增加其它占位内容，使页面可滚动
            Spacer(modifier = Modifier.height(600.dp))
        }
        Button(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text("Back To main", color = Color.Black)
        }
    }
}


// ------------------ Login Screen ------------------

@Composable
fun LoginScreen(
    onNavigateToSignUp: () -> Unit,
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF3F51B5), Color(0xFFE91E63))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Study & Time Manager",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.White),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Yellow,
                    cursorColor = Color.Yellow,
                    unfocusedBorderColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.White),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Yellow,
                    cursorColor = Color.Yellow,
                    unfocusedBorderColor = Color.White
                )
            )
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (username.isNotBlank() && password.isNotBlank()) {
                        onLoginSuccess()
                    } else {
                        errorMessage = "Please enter username and password"
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow)
            ) {
                Text("Login", color = Color.Black)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Don't have an account? Sign Up",
                color = Color.Cyan,
                modifier = Modifier.clickable { onNavigateToSignUp() }
            )
        }
    }
}

// ------------------ Sign Up Screen ------------------

@Composable
fun SignUpScreen(
    onBackToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF3F51B5), Color(0xFFE91E63))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Study & Time Manager",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.White),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Yellow,
                    cursorColor = Color.Yellow,
                    unfocusedBorderColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.White),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Yellow,
                    cursorColor = Color.Yellow,
                    unfocusedBorderColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.White),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Yellow,
                    cursorColor = Color.Yellow,
                    unfocusedBorderColor = Color.White
                )
            )
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (password == confirmPassword && username.isNotBlank() && password.isNotBlank()) {
                        onBackToLogin()
                    } else {
                        errorMessage = "Passwords do not match or fields are empty"
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow)
            ) {
                Text("Sign Up", color = Color.Black)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Back to Login",
                color = Color.Cyan,
                modifier = Modifier.clickable { onBackToLogin() }
            )
        }
    }
}
