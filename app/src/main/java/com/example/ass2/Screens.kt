@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.ass2.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ass2.Models.Task
import com.example.ass2.Models.PriorityTask
import com.example.ass2.TaskViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

// ------------------ 主页面与子组件 ------------------

@Composable
fun StudyManagementScreen(
    onNavigateToUrgent: () -> Unit,
    onNavigateToNotUrgent: () -> Unit,
    onNavigateToImportant: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1A237E),
                        Color(0xFF3F51B5),
                        Color(0xFF7986CB)
                    )
                )
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                "📚 Lisa's Study & Time Manager",
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
                onNavigateToImportant = onNavigateToImportant
            )
        }
        item { Spacer(modifier = Modifier.height(14.dp)) }
        item { SectionTitle("⏳ Daily Schedule") }
        item {
            StudyTaskCard(
                title = "🟦 Classes",
                subtitle = "📖 Lecture sessions",
                description = "🕒 9:00 AM - 12:00 PM",
                backgroundBrush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFF1976D2), Color(0xFF64B5F6))
                )
            )
        }
        item {
            StudyTaskCard(
                title = "🟧 Work Shifts",
                subtitle = "💼 Part-time job",
                description = "🕒 2:00 PM - 5:00 PM",
                backgroundBrush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFFD84315), Color(0xFFFF7043))
                )
            )
        }
        item {
            StudyTaskCard(
                title = "🟪 Study Sessions",
                subtitle = "📚 Focused study time",
                description = "🕒 6:00 PM - 8:00 PM",
                backgroundBrush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFF8E24AA), Color(0xFFBA68C8))
                )
            )
        }
        // 此处可以继续添加主页面其他内容或导航入口
    }
}

@Composable
fun HeaderSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Good morning, Lisa! Ready to tackle Wednesday?",
            fontSize = 22.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("📆 Upcoming deadlines: 3", fontSize = 18.sp, color = Color.White)
        Text("⏳ Uncompleted tasks: 5", fontSize = 18.sp, color = Color.White)
        Text("🎯 Weekly study goal: 12/20 hrs", fontSize = 18.sp, color = Color.White)
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
    onNavigateToImportant: () -> Unit
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
            onClick = null
        )
    )
    Column {
        priorityTasks.chunked(2).forEach { rowTasks ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowTasks.forEach { task ->
                    StudyTaskCard(
                        title = task.title,
                        subtitle = task.subtitle,
                        description = task.description,
                        backgroundBrush = task.backgroundBrush,
                        onClick = task.onClick,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
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

// ------------------ 修改后的任务卡组件 ------------------

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

// ------------------ Urgent & Important 页面 ------------------
// 样式与 Urgent but Not Important 和 Important Not Urgent 页面一致：不使用 TopAppBar，而在内容顶部显示标题

@Composable
fun UrgentAndImportantScreen(onBackToMain: () -> Unit, modifier: Modifier = Modifier) {
    val taskViewModel: TaskViewModel = viewModel()
    var newTaskTitle by remember { mutableStateOf("") }
    Scaffold(
        bottomBar = {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = newTaskTitle,
                    onValueChange = { newTaskTitle = it },
                    label = { Text("New Task") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        if (newTaskTitle.isNotBlank()) {
                            taskViewModel.addUrgentImportantTask(Task(newTaskTitle, "", "", false))
                            newTaskTitle = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Task")
                }
                Spacer(modifier = Modifier.height(8.dp))
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
            items(taskViewModel.urgentImportantTasks) { task ->
                ToggleableTaskCardLarge(
                    title = task.title,
                    deadline = task.deadline,
                    description = task.description,
                    isCompleted = task.isCompleted,
                    onToggle = { taskViewModel.toggleTask(task) },
                    onDelete = { taskViewModel.deleteTask(task) }
                )
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "💼 Work & Social Commitments",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(taskViewModel.workSocialTasks) { task ->
                ToggleableTaskCardLarge(
                    title = task.title,
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

// ------------------ Urgent but Not Important 页面 ------------------
// 移除 TopAppBar，直接在内容顶部显示标题，并同时显示 taskList1 与 taskList2 两部分

@Composable
fun UrgentNotImportantScreen(onBackToMain: () -> Unit, modifier: Modifier = Modifier) {
    val taskViewModel: TaskViewModel = viewModel()
    var newTaskTitle by remember { mutableStateOf("") }
    Scaffold(
        bottomBar = {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = newTaskTitle,
                    onValueChange = { newTaskTitle = it },
                    label = { Text("New Task") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        if (newTaskTitle.isNotBlank()) {
                            taskViewModel.addUrgentNotImportantTask(Task(newTaskTitle, "", "", false))
                            newTaskTitle = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Task")
                }
                Spacer(modifier = Modifier.height(8.dp))
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
            // 第一部分：taskList1
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
            items(taskViewModel.taskList1.chunked(2)) { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    row.forEach { task ->
                        ToggleableTaskCardMedium(
                            title = task.title,
                            deadline = task.deadline,
                            description = task.description,
                            isCompleted = task.isCompleted,
                            onToggle = { taskViewModel.toggleTask(task) },
                            onDelete = { taskViewModel.deleteTask(task) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
            }
            // 第二部分：taskList2 显示 Part-Time Job & Social Commitments
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "💼 Part-Time Job & Social Commitments",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            items(taskViewModel.taskList2.chunked(2)) { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    row.forEach { task ->
                        ToggleableTaskCardMedium(
                            title = task.title,
                            deadline = task.deadline,
                            description = task.description,
                            isCompleted = task.isCompleted,
                            onToggle = { taskViewModel.toggleTask(task) },
                            onDelete = { taskViewModel.deleteTask(task) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
            }
        }
    }
}

// ------------------ Important Not Urgent 页面 ------------------
// 移除 TopAppBar，直接在内容顶部显示标题

@Composable
fun ImportantNotUrgentScreen(onBackToMain: () -> Unit, modifier: Modifier = Modifier) {
    val taskViewModel: TaskViewModel = viewModel()
    var newTaskTitle by remember { mutableStateOf("") }
    Scaffold(
        bottomBar = {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = newTaskTitle,
                    onValueChange = { newTaskTitle = it },
                    label = { Text("New Task") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        if (newTaskTitle.isNotBlank()) {
                            taskViewModel.addImportantNotUrgentTask(Task(newTaskTitle, "", "", false))
                            newTaskTitle = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Task")
                }
                Spacer(modifier = Modifier.height(8.dp))
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
            items(taskViewModel.importantNotUrgentTasks) { task ->
                ToggleableTaskCardMedium(
                    title = task.title,
                    deadline = task.deadline,
                    description = task.description,
                    isCompleted = task.isCompleted,
                    onToggle = { taskViewModel.toggleTask(task) },
                    onDelete = { taskViewModel.deleteTask(task) }
                )
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "💼 Part-Time Job & Social Activities",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            items(taskViewModel.partTimeSocialTasks) { task ->
                ToggleableTaskCardMedium(
                    title = task.title,
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

// ------------------ 新设计：Study & Review 页面（内嵌到主页面或独立使用均可） ------------------

@Composable
fun StudyAndReviewContent() {
    Text(
        text = "⚪ Study & Review",
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        fontSize = 28.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Topics", "Flashcards")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = selectedTab == index,
                    onClick = { selectedTab = index }
                )
            }
        }
        when (selectedTab) {
            0 -> StudyTopicsTab()
            1 -> FlashcardsTab()
        }
    }
}

@Composable
fun StudyTopicsTab() {
    var searchQuery by remember { mutableStateOf("") }
    val reviewTopics = listOf("Mathematics", "Computer Science", "History", "Physics", "Chemistry")
    val filteredTopics = if (searchQuery.isEmpty()) reviewTopics
    else reviewTopics.filter { it.contains(searchQuery, ignoreCase = true) }
    Column(modifier = Modifier.padding(top = 16.dp)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search Topics") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyColumn {
            items(filteredTopics) { topic ->
                ReviewTopicCard(topic = topic, onClick = { /* 处理点击事件 */ })
            }
        }
    }
}

@Composable
fun ReviewTopicCard(topic: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Text(
            text = topic,
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun FlashcardsTab() {
    val flashcards = listOf(
        "What is the capital of France?" to "Paris",
        "What is 2+2?" to "4",
        "What is the powerhouse of the cell?" to "Mitochondria"
    )
    Column(modifier = Modifier.padding(top = 16.dp)) {
        LazyColumn {
            items(flashcards) { card ->
                Flashcard(question = card.first, answer = card.second, onClick = { /* 处理点击事件 */ })
            }
        }
    }
}

@Composable
fun Flashcard(question: String, answer: String, onClick: () -> Unit) {
    var showAnswer by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            if (showAnswer) {
                Text(text = "Answer:", fontWeight = FontWeight.Bold, color = Color.Blue)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = answer)
            } else {
                Text(text = "Question:", fontWeight = FontWeight.Bold, color = Color.Blue)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = question)
            }
        }
    }
}
