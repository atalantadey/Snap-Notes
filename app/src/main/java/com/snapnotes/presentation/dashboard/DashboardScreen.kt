package com.snapnotes.presentation.dashboard

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.snapnotes.R
import com.snapnotes.domain.model.Subject
import com.snapnotes.domain.model.Task
import com.snapnotes.presentation.componenets.CountCard
import com.snapnotes.presentation.componenets.SubjectCad
import com.snapnotes.presentation.componenets.taskList

@Composable
fun DashboardScreen() {

    val subjects = listOf(
        Subject(name = "English", goalHours = 10f.toString(), colors = Subject.subjectCardColor[0]),
        Subject(name = "Maths", goalHours = 12f.toString(), colors = Subject.subjectCardColor[1]),
        Subject(name = "Physics", goalHours = 5f.toString(), colors = Subject.subjectCardColor[2]),
        Subject(
            name = "Chemistry",
            goalHours = 7f.toString(),
            colors = Subject.subjectCardColor[3]
        ),
        Subject(name = "Computer", goalHours = 2f.toString(), colors = Subject.subjectCardColor[4])
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
                    goalHours = "12"
                )
            }
            item {
                SubjectCardSection(
                    modifier = Modifier.fillMaxWidth(),
                    subjectList = subjects
                )
            }
            item {
                Button(
                    onClick = { /*TODO*/ },
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
                tasks = emptyList()
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
    emptyListText: String = "You Don't have any Subject. \n Click the + Button to add a new Subject"
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
            IconButton(onClick = { /*TODO*/ }) {
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
                    onClick = {})
            }
        }
    }
}