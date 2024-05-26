package com.snapnotes.presentation.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.snapnotes.R
import com.snapnotes.Sess
import com.snapnotes.domain.model.Sessions
import com.snapnotes.domain.model.Subject
import com.snapnotes.domain.model.Task
import com.snapnotes.presentation.componenets.AddSubjectDialog
import com.snapnotes.presentation.componenets.CountCard
import com.snapnotes.presentation.componenets.DeleteDialog
import com.snapnotes.presentation.componenets.StudySessionsList
import com.snapnotes.presentation.componenets.SubjectCad
import com.snapnotes.presentation.componenets.taskList
import com.snapnotes.presentation.destinations.SessionScreenRouteDestination
import com.snapnotes.presentation.destinations.SubjectScreenRouteDestination
import com.snapnotes.presentation.destinations.TaskScreenRouteDestination
import com.snapnotes.presentation.subject.SubjectScreenNav
import com.snapnotes.presentation.task.TaskScreenNav
import com.snapnotes.subjects
import com.snapnotes.taskDisplay

@Destination(start = true)
@Composable
fun DashboardScreenRoute(
    navigator:DestinationsNavigator)
{

    val viewModel:DashboardViewModel= hiltViewModel()

    DashboardScreen(
        onSubjectCardClick = { subjectId->
            subjectId?.let { val navArg=SubjectScreenNav(subjectId=subjectId)
            navigator.navigate(SubjectScreenRouteDestination(navArg))}
        },
        onSessionCardClick = {
            navigator.navigate(SessionScreenRouteDestination())
        },
        onTaskCardClick = {TaskId->
                val navArg=TaskScreenNav(TaskId=TaskId,subjectId = null)
                navigator.navigate(TaskScreenRouteDestination(navArg))
            }
    )
}

@Composable
private fun DashboardScreen(
    onSubjectCardClick: (Int?) -> Unit,
    onSessionCardClick: () -> Unit,
    onTaskCardClick: (Int?) -> Unit,

    ) {
    var isAddSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }
    var subjectName by remember { mutableStateOf("") }
    var goalHours by remember { mutableStateOf("") }
    var selectedCOlor by remember { mutableStateOf(Subject.subjectCardColor.random()) }
    var isDeleteSessionDialogOpen by rememberSaveable { mutableStateOf(false) }

    AddSubjectDialog(
        isOpen = isAddSubjectDialogOpen,
        onDismissRequest = { isAddSubjectDialogOpen = false },
        onConfirmButton = { isAddSubjectDialogOpen = false },
        selectedColors = selectedCOlor,
        onColorChange = { selectedCOlor = it },
        onSubjectNameChange = { subjectName = it },
        onGoalHoursChange = { goalHours = it },
        goalHours = goalHours,
        subjectName = subjectName
    )
    DeleteDialog(
        isOpen = isDeleteSessionDialogOpen,
        title = "Delete Session ?",
        onDismissRequest = { isDeleteSessionDialogOpen = false },
        onConfirmButton = { isDeleteSessionDialogOpen = false },
        bodyText = "Are You Sure?\nThis cannot be undone"
    )

    Scaffold(topBar = { DashboardScreenTopBar() }) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                CountCardSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    subjectCount = 5,
                    studiedHours = "4",
                    goalHours = "11"
                )
            }
            item {
                SubjectCardSection(
                    modifier = Modifier.fillMaxWidth(),
                    subjectList = subjects,
                    onAddIconClicked = { isAddSubjectDialogOpen = true },
                    onSubjectCardCl = onSubjectCardClick
                )
            }
            item {
                Button(
                    onClick = onSessionCardClick ,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 56.dp, vertical = 28.dp)
                ) {
                    Text(text = "Start Study Session")
                }
            }//button
            taskList(
                sectionTitle = "UPCOMING TASKS",
                emptyListText = "You Don't Have any Upcoming Tasks.\n" + " Click on the + Button to add Tasks.",
                tasks = taskDisplay,//emptylist
                onCheckBoxClick = {},
                onTaskCardClick =  onTaskCardClick
            )
            item { Spacer(modifier = Modifier.height(12.dp)) }
            StudySessionsList(
                sectionTitle = "RECENT STUDY SESSIONS",
                emptyListText = "You Don't Have any Study Sessions Yet.\n" + " Click on the  Button to Start a Study Session.",
                session = Sess,//emptyList()
                onDeleteIconClick = { isDeleteSessionDialogOpen = true }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardScreenTopBar() {
    CenterAlignedTopAppBar(title = {
        Text(
            text = "SNAP NOTES",
            style = MaterialTheme.typography.headlineMedium
        )
    })
}

@Composable
fun CountCardSection(
    modifier: Modifier, subjectCount: Int, studiedHours: String, goalHours: String
) {
    Row(modifier = modifier) {
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Subject Count",
            count = "$subjectCount"
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Studied Hours",
            count = studiedHours
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(modifier = Modifier.weight(1f), headingText = "Study Goal", count = goalHours)
    }

}

@Composable
private fun SubjectCardSection(
    modifier: Modifier,
    subjectList: List<Subject>,
    emptyListText: String = "You Don't have any Subject. \n Click the + Button to add a new Subject",
    onAddIconClicked: () -> Unit,
    onSubjectCardCl: (Int?) -> Unit,
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "SUBJECTS",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 12.dp)
            )
            IconButton(onClick = { onAddIconClicked() }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Subject")
            }

        }
        if (subjectList.isEmpty()) {
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(R.drawable.book), contentDescription = emptyListText
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = emptyListText,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.Center

            )
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp)
        ) {
            items(subjectList) { subjects ->
                SubjectCad(
                    SubjectName = subjects.name,
                    gradientColor = subjects.colors,
                    onClick = { onSubjectCardCl(subjects.subjectId) })
            }
        }
    }
}