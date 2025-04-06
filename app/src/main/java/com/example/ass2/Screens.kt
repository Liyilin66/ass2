package com.example.ass2.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
    modifier: Modifier = Modifier
) {
    Column(
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
        Text(
            "📚 Lisa's Study & Time Manager",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 40.8.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        HeaderSection()
        Spacer(modifier = Modifier.height(20.dp))
        SectionTitle("📌 Priority Matrix")
        PriorityMatrix(
            onNavigateToUrgent = onNavigateToUrgent,
            onNavigateToNotUrgent = onNavigateToNotUrgent
        )
        Spacer(modifier = Modifier.height(14.dp))
        SectionTitle("⏳ Daily Schedule")
        StudyTaskCard(
            title = "🟦 Classes",
            subtitle = "📖 Lecture sessions",
            description = "🕒 9:00 AM - 12:00 PM",
            backgroundBrush = Brush.horizontalGradient(
                colors = listOf(Color(0xFF1976D2), Color(0xFF64B5F6))
            )
        )
        StudyTaskCard(
            title = "🟧 Work Shifts",
            subtitle = "💼 Part-time job",
            description = "🕒 2:00 PM - 5:00 PM",
            backgroundBrush = Brush.horizontalGradient(
                colors = listOf(Color(0xFFD84315), Color(0xFFFF7043))
            )
        )
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
    onNavigateToNotUrgent: () -> Unit
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
            onClick = null
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
            items(
                items = listOf(
                    Task("Complete Assignment", "Due today at 11:59 PM", "Ensure all sections are well written and formatted."),
                    Task("Prepare for Exam", "Review notes and practice questions", "Allocate at least 3 hours to revise core concepts."),
                    Task("Project Deadline", "Submit group project by 5 PM", "Review final document and confirm all contributions.")
                )
            ) { task ->
                TaskCard(task.title, task.deadline, task.description)
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
            items(
                items = listOf(
                    Task("Part-time Job Shift", "Today from 4 PM - 8 PM", "Assist customers and manage store inventory."),
                    Task("Dinner with Friends", "Tonight at 7:30 PM", "Catch up and enjoy quality time at the cafe.")
                )
            ) { task ->
                TaskCard(task.title, task.deadline, task.description)
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
            items(
                items = taskList1.chunked(2)
            ) { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    row.forEach { task ->
                        TaskCard3(
                            task.title,
                            task.deadline,
                            task.description,
                            Modifier.weight(1f),
                            onClick = { /* 可添加点击事件 */ }
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
            items(
                items = taskList2.chunked(2)
            ) { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    row.forEach { task ->
                        TaskCard3(
                            task.title,
                            task.deadline,
                            task.description,
                            Modifier.weight(1f),
                            onClick = { /* 可添加点击事件 */ }
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
