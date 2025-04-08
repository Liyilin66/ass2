package com.example.ass2.Models

import androidx.compose.ui.graphics.Brush

data class Task(
    val title: String,
    val deadline: String,
    val description: String,
    var isCompleted: Boolean = false
)

data class PriorityTask(
    val title: String,
    val subtitle: String,
    val description: String,
    val backgroundBrush: Brush,
    val onClick: (() -> Unit)?
)

val taskList1 = listOf(
    Task("Check Professor Emails", "Reply to pending questions", "Respond to emails regarding upcoming assignment clarifications and project deadlines."),
    Task("Organize Study Notes", "Sort last week's lecture notes", "Highlight key points and group materials by topic for efficient review."),
    Task("Update University Portal", "Confirm class schedules", "Check for updates on classroom changes and upcoming assessments."),
    Task("Review Presentation Slides", "Practice for seminar", "Prepare key talking points and refine visual content for upcoming seminar.")
)

val taskList2 = listOf(
    Task("Library Shift", "Work from 2 PM - 6 PM", "Assist students in finding books, manage check-ins, and maintain quiet study zones."),
    Task("Tutoring Session", "Help classmates with calculus", "Guide students through problem-solving techniques for upcoming test preparation."),
    Task("Dinner with Study Group", "Meet at 7 PM", "Discuss upcoming exam strategies and unwind with friends over dinner."),
    Task("Student Club Meeting", "Plan fundraising event", "Coordinate logistics for upcoming charity drive with fellow club members.")
)
