
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
            items(
                items = listOf(
                    Task("Complete Research Paper", "Due: Next Monday", "Focus on data analysis section."),
                    Task("Prepare Group Presentation", "Team meeting at 3 PM", "Finalize slides and rehearse key points."),
                    Task("Revise for Midterm", "Subjects: Math & Computer Science", "Cover core concepts and problem-solving techniques.")
                )
            ) { task ->
                TaskCard(task.title, task.deadline, task.description)
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
            items(
                items = listOf(
                    Task("Library Assistant Shift", "Today 2 PM - 6 PM", "Assist students and organize book sections."),
                    Task("Coding Club Meeting", "Tomorrow at 5 PM", "Discuss app development strategies."),
                    Task("Volunteer Event", "Saturday at 10 AM", "Help organize a charity fundraiser.")
                )
            ) { task ->
                TaskCard(task.title, task.deadline, task.description)
            }
        }
    }
}
