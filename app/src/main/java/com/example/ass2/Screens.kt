package com.example.ass2.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
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
import com.example.ass2.Models.taskList1
import com.example.ass2.Models.taskList2

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

// ------------------ 新增：可切换任务卡组件 ------------------

@Composable
fun ToggleableTaskCard(
    title: String,
    deadline: String,
    description: String,
    modifier: Modifier = Modifier
) {
    var isCompleted by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { isCompleted = !isCompleted },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = if (isCompleted) Color(0xFFB0BEC5) else Color.White)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = deadline, fontSize = 12.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = description, fontSize = 12.sp, color = Color.Black.copy(alpha = 0.8f))
            }
            if (isCompleted) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Completed",
                    tint = Color.Green
                )
            }
        }
    }
}

// ------------------ 新增：大字号可切换任务卡组件（用于 Urgent & Important 页面） ------------------

@Composable
fun ToggleableTaskCardLarge(
    title: String,
    deadline: String,
    description: String,
    modifier: Modifier = Modifier
) {
    var isCompleted by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { isCompleted = !isCompleted },
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
            if (isCompleted) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Completed",
                    tint = Color.Green
                )
            }
        }
    }
}

// ------------------ 新增：大字号任务卡组件（用于 Urgent & Important 页面 Work & Social Commitments 部分） ------------------
@Composable
fun TaskCardLarge(
    title: String,
    deadline: String,
    description: String
) {
    // 使用大字号可切换组件确保任务可点击
    ToggleableTaskCardLarge(title, deadline, description)
}

// ------------------ 新增：中字号可切换任务卡组件（用于 Urgent Not Important 和 Important Not Urgent 页面） ------------------
@Composable
fun ToggleableTaskCardMedium(
    title: String,
    deadline: String,
    description: String,
    modifier: Modifier = Modifier
) {
    var isCompleted by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { isCompleted = !isCompleted },
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
            if (isCompleted) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Completed",
                    tint = Color.Green
                )
            }
        }
    }
}

// ------------------ Urgent & Important 页面 ------------------

@Composable
fun UrgentAndImportantScreen(onBackToMain: () -> Unit, modifier: Modifier = Modifier) {
    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
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
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            // 使用大字号可切换任务卡组件展示紧急且重要任务
            items(
                items = listOf(
                    Task("Complete Assignment", "Due today at 11:59 PM", "Ensure all sections are well written and formatted."),
                    Task("Prepare for Exam", "Review notes and practice questions", "Allocate at least 3 hours to revise core concepts."),
                    Task("Project Deadline", "Submit group project by 5 PM", "Review final document and confirm all contributions.")
                )
            ) { task ->
                ToggleableTaskCardLarge(task.title, task.deadline, task.description)
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
            // 使用大字号可切换任务卡组件展示工作与社交任务，确保可点击
            items(
                items = listOf(
                    Task("Part-time Job Shift", "Today from 4 PM - 8 PM", "Assist customers and manage store inventory."),
                    Task("Dinner with Friends", "Tonight at 7:30 PM", "Catch up and enjoy quality time at the cafe.")
                )
            ) { task ->
                ToggleableTaskCardLarge(task.title, task.deadline, task.description)
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun TaskCard(title: String, deadline: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = deadline, fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = description, fontSize = 12.sp, color = Color.Black.copy(alpha = 0.8f))
        }
    }
}

// ------------------ Urgent but Not Important 页面 ------------------

@Composable
fun UrgentNotImportantScreen(onBackToMain: () -> Unit, modifier: Modifier = Modifier) {
    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
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
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            // 使用中字号可切换任务卡组件展示 urgent but not important 任务
            items(
                items = taskList1.chunked(2)
            ) { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    row.forEach { task ->
                        ToggleableTaskCardMedium(
                            title = task.title,
                            deadline = task.deadline,
                            description = task.description,
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
            // 同样使用中字号可切换任务卡组件展示第二组任务
            items(
                items = taskList2.chunked(2)
            ) { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    row.forEach { task ->
                        ToggleableTaskCardMedium(
                            title = task.title,
                            deadline = task.deadline,
                            description = task.description,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
            }
        }
    }
}

@Composable
fun TaskCard3(
    title: String,
    deadline: String,
    description: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = modifier
            .padding(vertical = 4.dp)
            .clickable(enabled = onClick != null) { onClick?.invoke() },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(3.dp))
            Text(deadline, fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(3.dp))
            Text(description, fontSize = 12.sp, color = Color.Black.copy(alpha = 0.8f))
        }
    }
}

// ------------------ Important Not Urgent 页面 ------------------

@Composable
fun ImportantNotUrgentScreen(onBackToMain: () -> Unit, modifier: Modifier = Modifier) {
    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
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
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            // 这里使用中字号可切换任务卡组件展示 Important Not Urgent 任务
            items(
                items = listOf(
                    Task("Complete Research Paper", "Due: Next Monday", "Focus on data analysis section."),
                    Task("Prepare Group Presentation", "Team meeting at 3 PM", "Finalize slides and rehearse key points."),
                    Task("Revise for Midterm", "Subjects: Math & Computer Science", "Cover core concepts and problem-solving techniques.")
                )
            ) { task ->
                ToggleableTaskCardMedium(task.title, task.deadline, task.description)
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
            // 同样使用中字号可切换任务卡组件展示第二组任务
            items(
                items = listOf(
                    Task("Library Assistant Shift", "Today 2 PM - 6 PM", "Assist students and organize book sections."),
                    Task("Coding Club Meeting", "Tomorrow at 5 PM", "Discuss app development strategies."),
                    Task("Volunteer Event", "Saturday at 10 AM", "Help organize a charity fundraiser.")
                )
            ) { task ->
                ToggleableTaskCardMedium(task.title, task.deadline, task.description)
            }
        }
    }
}
