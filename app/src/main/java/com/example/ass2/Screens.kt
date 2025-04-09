@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.ass2.Screens

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ass2.Models.Task
import com.example.ass2.Models.PriorityTask
import com.example.ass2.TaskViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.unit.toSize

// ------------------ Main Screen and Sub-components ------------------

@Composable
fun StudyManagementScreen(
    onNavigateToUrgent: () -> Unit,
    onNavigateToNotUrgent: () -> Unit,
    onNavigateToImportant: () -> Unit,
    onNavigateToStudyAndReview: () -> Unit,
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
                onNavigateToImportant = onNavigateToImportant,
                onNavigateToStudyAndReview = onNavigateToStudyAndReview
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
    onNavigateToImportant: () -> Unit,
    onNavigateToStudyAndReview: () -> Unit
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

// ------------------ Urgent but Not Important Screen ------------------

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

// ------------------ Important Not Urgent Screen ------------------

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

// ------------------ Study & Review Page (Changed to "各科考试成绩分析") ------------------

@Composable
fun StudyAndReviewContent(onBack: () -> Unit, taskViewModel: TaskViewModel = viewModel()) {
    var currentFeature by remember { mutableStateOf("SUBJECT_REVIEW") }
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
                .padding(bottom = 80.dp)  // 增加额外的底部内边距
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { currentFeature = "SUBJECT_REVIEW" },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (currentFeature == "SUBJECT_REVIEW") Color.White else Color.Transparent
                    )
                ) {
                    Text("Subject Review", color = if (currentFeature == "SUBJECT_REVIEW") Color.Black else Color.White)
                }
                Button(
                    onClick = { currentFeature = "QUESTION_CARD" },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (currentFeature == "QUESTION_CARD") Color.White else Color.Transparent
                    )
                ) {
                    Text("Question Card", color = if (currentFeature == "QUESTION_CARD") Color.Black else Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (currentFeature) {
                "SUBJECT_REVIEW" -> {
                    Column {
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
                        // 柱状图部分
                        ExamScoreBarChart()
                        Spacer(modifier = Modifier.height(16.dp))
                        // 成绩分析部分
                        ScoreAnalysisSection()
                    }
                }
                "QUESTION_CARD" -> {
                    Column {
                        Text(
                            "Question Card",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "No question content available yet. Please add later.",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
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
fun ScoreAnalysisCard(
    subject: String,
    score: Int,
    modifier: Modifier = Modifier
) {
    // 根据分数生成评语
    val feedback = when {
        score >= 90 -> "Excellent performance! You've mastered this subject. Keep up the fantastic work!"
        score in 80..89 -> "Good job overall! Your understanding is solid, but consider focusing on refining problem solving skills."
        else -> "Needs improvement. Your fundamentals could be stronger; review core concepts and practice more."
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        shape = RoundedCornerShape(8.dp),
        // 设置背景色为白色
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
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
    }
}



@Composable
fun ScoreAnalysisSection(
    scores: Map<String, Int> = mapOf(
        "Math" to 78,
        "English" to 85,
        "Physics" to 90,
        "Chemistry" to 88,
        "Biology" to 82
    )
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Score Analysis",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        scores.forEach { (subject, score) ->
            ScoreAnalysisCard(subject = subject, score = score)
        }
    }
}


// ------------------ ExamScoreBarChart Component ------------------

@Composable
fun ExamScoreBarChart(
    modifier: Modifier = Modifier,
    scores: Map<String, Int> = mapOf(
        "Math" to 78,
        "English" to 85,
        "Physics" to 90,
        "Chemistry" to 88,
        "Biology" to 82
    )
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

        // Draw Y axis with arrowhead
        drawLine(
            color = Color.Black,
            start = Offset(leftMargin, topMargin),
            end = Offset(leftMargin, size.height - bottomMargin),
            strokeWidth = 4f
        )
        val arrowSize = 8.dp.toPx()
        val yArrowPath = androidx.compose.ui.graphics.Path().apply {
            moveTo(leftMargin, topMargin)
            lineTo(leftMargin - arrowSize, topMargin + arrowSize)
            lineTo(leftMargin + arrowSize, topMargin + arrowSize)
            close()
        }
        drawPath(path = yArrowPath, color = Color.Black)

        // Draw X axis with arrowhead
        drawLine(
            color = Color.Black,
            start = Offset(leftMargin, size.height - bottomMargin),
            end = Offset(size.width - rightMargin, size.height - bottomMargin),
            strokeWidth = 4f
        )
        val xArrowPath = androidx.compose.ui.graphics.Path().apply {
            moveTo(size.width - rightMargin, size.height - bottomMargin)
            lineTo(size.width - rightMargin - arrowSize, size.height - bottomMargin - arrowSize / 2)
            lineTo(size.width - rightMargin - arrowSize, size.height - bottomMargin + arrowSize / 2)
            close()
        }
        drawPath(path = xArrowPath, color = Color.Black)

        // Draw bars for each subject
        val numberOfBars = scores.size
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

        // Draw Y-axis tick labels
        drawIntoCanvas { canvas ->
            val tickPaint = android.graphics.Paint().apply {
                color = android.graphics.Color.BLACK
                textSize = 40f
                textAlign = android.graphics.Paint.Align.RIGHT
            }
            canvas.nativeCanvas.drawText(
                maxScore.toInt().toString(),
                leftMargin - 8,
                topMargin + 40f,
                tickPaint
            )
            canvas.nativeCanvas.drawText(
                (maxScore / 2).toInt().toString(),
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

