package com.snapnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ramcosta.composedestinations.DestinationsNavHost
import com.snapnotes.domain.model.Sessions
import com.snapnotes.domain.model.Subject
import com.snapnotes.domain.model.Task
import com.snapnotes.presentation.NavGraphs
import com.snapnotes.presentation.theme.SnapNotesTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SnapNotesTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}//Dummy Data
val subjects = listOf(
    Subject(
        name = "English",
        goalHours = 10f.toString(),
        colors = Subject.subjectCardColor[0],
        subjectId = 0
    ),
    Subject(
        name = "Maths",
        goalHours = 12f.toString(),
        colors = Subject.subjectCardColor[1],
        subjectId = 0
    ),
    Subject(
        name = "Physics",
        goalHours = 5f.toString(),
        colors = Subject.subjectCardColor[2],
        subjectId = 0
    ),
    Subject(
        name = "Chemistry",
        goalHours = 7f.toString(),
        colors = Subject.subjectCardColor[3],
        subjectId = 0
    ),
    Subject(
        name = "Computer",
        goalHours = 2f.toString(),
        colors = Subject.subjectCardColor[4],
        subjectId = 0
    )
)
val taskDisplay = listOf(
    Task(
        title = "Prepare Notes",
        description = "",
        dueDate = 0L,
        priority = 0,
        relatedToSubject = "",
        isComplete = false,
        taskId = 1,
        taskSubjectId = 0
    ),
    Task(
        title = "Study for Exams",
        description = "",
        dueDate = 0L,
        priority = 0,
        relatedToSubject = "",
        isComplete = true,
        taskId = 1,
        taskSubjectId = 0
    ),
    Task(
        title = "Leetcode",
        description = "",
        dueDate = 0L,
        priority = 2,
        relatedToSubject = "",
        isComplete = false,
        taskId = 1,
        taskSubjectId = 0
    ),
    Task(
        title = "Aptitude",
        description = "",
        dueDate = 0L,
        priority = 1,
        relatedToSubject = "",
        isComplete = false,
        taskId = 1,
        taskSubjectId = 0
    ),
)
val Sess = listOf(
    Sessions(
        relatedToSubject = "English",
        date = 0L,
        duration = 2,
        sessionSubjectId = 0,
        sessionId = 0
    ),
    Sessions(
        relatedToSubject = "Maths",
        date = 0L,
        duration = 1,
        sessionSubjectId = 0,
        sessionId = 0
    ),
    Sessions(
        relatedToSubject = "Physics",
        date = 0L,
        duration = 1,
        sessionSubjectId = 0,
        sessionId = 0
    ),
    Sessions(
        relatedToSubject = "Computer",
        date = 0L,
        duration = 2,
        sessionSubjectId = 0,
        sessionId = 0
    ),
)


