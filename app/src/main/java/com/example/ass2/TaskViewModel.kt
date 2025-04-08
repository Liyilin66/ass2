package com.example.ass2

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.ass2.Models.Task

class TaskViewModel : ViewModel() {
    private val _taskList1 = mutableStateListOf<Task>().apply {
        addAll(listOf(
            Task("Check Professor Emails", "Reply to pending questions", "Respond to emails regarding upcoming assignment clarifications and project deadlines.", isCompleted = false),
            Task("Organize Study Notes", "Sort last week's lecture notes", "Highlight key points and group materials by topic for efficient review.", isCompleted = false),
            Task("Update University Portal", "Confirm class schedules", "Check for updates on classroom changes and upcoming assessments.", isCompleted = false),
            Task("Review Presentation Slides", "Practice for seminar", "Prepare key talking points and refine visual content for upcoming seminar.", isCompleted = false)
        ))
    }
    private val _taskList2 = mutableStateListOf<Task>().apply {
        addAll(listOf(
            Task("Library Shift", "Work from 2 PM - 6 PM", "Assist students in finding books, manage check-ins, and maintain quiet study zones.", isCompleted = false),
            Task("Tutoring Session", "Help classmates with calculus", "Guide students through problem-solving techniques for upcoming test preparation.", isCompleted = false),
            Task("Dinner with Study Group", "Meet at 7 PM", "Discuss upcoming exam strategies and unwind with friends over dinner.", isCompleted = false),
            Task("Student Club Meeting", "Plan fundraising event", "Coordinate logistics for upcoming charity drive with fellow club members.", isCompleted = false)
        ))
    }
    private val _urgentImportantTasks = mutableStateListOf<Task>().apply {
        addAll(listOf(
            Task("Complete Assignment", "Due today at 11:59 PM", "Ensure all sections are well written and formatted.", isCompleted = false),
            Task("Prepare for Exam", "Review notes and practice questions", "Allocate at least 3 hours to revise core concepts.", isCompleted = false),
            Task("Project Deadline", "Submit group project by 5 PM", "Review final document and confirm all contributions.", isCompleted = false)
        ))
    }
    private val _workSocialTasks = mutableStateListOf<Task>().apply {
        addAll(listOf(
            Task("Part-time Job Shift", "Today from 4 PM - 8 PM", "Assist customers and manage store inventory.", isCompleted = false),
            Task("Dinner with Friends", "Tonight at 7:30 PM", "Catch up and enjoy quality time at the cafe.", isCompleted = false)
        ))
    }
    private val _importantNotUrgentTasks = mutableStateListOf<Task>().apply {
        addAll(listOf(
            Task("Complete Research Paper", "Due: Next Monday", "Focus on data analysis section.", isCompleted = false),
            Task("Prepare Group Presentation", "Team meeting at 3 PM", "Finalize slides and rehearse key points.", isCompleted = false),
            Task("Revise for Midterm", "Subjects: Math & Computer Science", "Cover core concepts and problem-solving techniques.", isCompleted = false)
        ))
    }
    private val _partTimeSocialTasks = mutableStateListOf<Task>().apply {
        addAll(listOf(
            Task("Library Assistant Shift", "Today 2 PM - 6 PM", "Assist students and organize book sections.", isCompleted = false),
            Task("Coding Club Meeting", "Tomorrow at 5 PM", "Discuss app development strategies.", isCompleted = false),
            Task("Volunteer Event", "Saturday at 10 AM", "Help organize a charity fundraiser.", isCompleted = false)
        ))
    }

    val taskList1: List<Task> = _taskList1
    val taskList2: List<Task> = _taskList2
    val urgentImportantTasks: List<Task> = _urgentImportantTasks
    val workSocialTasks: List<Task> = _workSocialTasks
    val importantNotUrgentTasks: List<Task> = _importantNotUrgentTasks
    val partTimeSocialTasks: List<Task> = _partTimeSocialTasks

    fun toggleTask(task: Task) {
        _taskList1.indexOfFirst { it.title == task.title }.let { idx ->
            if (idx != -1) {
                _taskList1[idx] = _taskList1[idx].copy(isCompleted = !_taskList1[idx].isCompleted)
                return
            }
        }
        _taskList2.indexOfFirst { it.title == task.title }.let { idx ->
            if (idx != -1) {
                _taskList2[idx] = _taskList2[idx].copy(isCompleted = !_taskList2[idx].isCompleted)
                return
            }
        }
        _urgentImportantTasks.indexOfFirst { it.title == task.title }.let { idx ->
            if (idx != -1) {
                _urgentImportantTasks[idx] = _urgentImportantTasks[idx].copy(isCompleted = !_urgentImportantTasks[idx].isCompleted)
                return
            }
        }
        _workSocialTasks.indexOfFirst { it.title == task.title }.let { idx ->
            if (idx != -1) {
                _workSocialTasks[idx] = _workSocialTasks[idx].copy(isCompleted = !_workSocialTasks[idx].isCompleted)
                return
            }
        }
        _importantNotUrgentTasks.indexOfFirst { it.title == task.title }.let { idx ->
            if (idx != -1) {
                _importantNotUrgentTasks[idx] = _importantNotUrgentTasks[idx].copy(isCompleted = !_importantNotUrgentTasks[idx].isCompleted)
                return
            }
        }
        _partTimeSocialTasks.indexOfFirst { it.title == task.title }.let { idx ->
            if (idx != -1) {
                _partTimeSocialTasks[idx] = _partTimeSocialTasks[idx].copy(isCompleted = !_partTimeSocialTasks[idx].isCompleted)
                return
            }
        }
    }
}
