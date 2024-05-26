package com.snapnotes.presentation.subject

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.snapnotes.Sess
import com.snapnotes.domain.model.Subject
import com.snapnotes.presentation.componenets.AddSubjectDialog
import com.snapnotes.presentation.componenets.CountCard
import com.snapnotes.presentation.componenets.DeleteDialog
import com.snapnotes.presentation.componenets.StudySessionsList
import com.snapnotes.presentation.componenets.taskList
import com.snapnotes.presentation.destinations.TaskScreenRouteDestination
import com.snapnotes.presentation.task.TaskScreenNav
import com.snapnotes.taskDisplay

data class SubjectScreenNav(val subjectId:Int)
@Destination(navArgsDelegate = SubjectScreenNav::class)
@Composable
fun SubjectScreenRoute(navigator: DestinationsNavigator){

    val viewModel:SubjectViewModel= hiltViewModel()

    SubjectScreen(
        onBackButtonClick = { navigator.navigateUp() },
        onAddTaskButtonClick = {  val navArg=TaskScreenNav(TaskId=null,subjectId = null)
            navigator.navigate(TaskScreenRouteDestination(navArg)) },
        onTaskCardClick = { TaskId->
            val navArg= TaskScreenNav(TaskId=TaskId,subjectId = null)
            navigator.navigate(TaskScreenRouteDestination(navArg))
        })
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubjectScreen(
    onBackButtonClick: () -> Unit,
    onAddTaskButtonClick:()->Unit,
    onTaskCardClick:(Int?)->Unit
) {

    val listState = rememberLazyListState()
    val isFABExpanded by remember { derivedStateOf { listState.firstVisibleItemIndex==0 } }
    val scrollBehavior=TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    var isAddSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }
   // var isEditSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }
    var subjectName by remember { mutableStateOf("") }
    var goalHours by remember { mutableStateOf("") }
    var selectedCOlor by remember { mutableStateOf(Subject.subjectCardColor.random()) }
    var isDeleteSessionDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }

    AddSubjectDialog(
        isOpen = isAddSubjectDialogOpen,
        onDismissRequest = { isAddSubjectDialogOpen = false },
        onConfirmButton = { isAddSubjectDialogOpen = false },
        selectedColors = selectedCOlor,
        onColorChange = {selectedCOlor=it},
        onSubjectNameChange = {subjectName=it},
        onGoalHoursChange = {goalHours=it},
        goalHours = goalHours,
        subjectName = subjectName
    )
    DeleteDialog(
        isOpen = isDeleteSubjectDialogOpen,
        title = "Delete Subject ?",
        onDismissRequest = { isDeleteSubjectDialogOpen=false },
        onConfirmButton = { isAddSubjectDialogOpen=false },
        bodyText = "Are You Sure you want to Delete this Subject ?\nAll Related Tasks will be Deleted Permanently"
    )
    DeleteDialog(
        isOpen = isDeleteSessionDialogOpen,
        title = "Delete Session ?",
        onDismissRequest = { isDeleteSessionDialogOpen=false },
        onConfirmButton = { isDeleteSessionDialogOpen=false },
        bodyText = "Are You Sure?\nThis cannot be undone"
    )

    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
        SubjectScreenTopBar(
            title = "English",
            onBackButtonClick = onBackButtonClick,
            onDeleteBttonClick = { isDeleteSubjectDialogOpen=true }, onEditButtonClick = {isAddSubjectDialogOpen=true}, scrollBehavior = scrollBehavior)
    }, floatingActionButton = { ExtendedFloatingActionButton(onClick =  onAddTaskButtonClick , icon = { Icon(
        imageVector = Icons.Default.Add,
        contentDescription = "Add"
    )}, text = {Text(text = "Add Task")}, expanded = isFABExpanded) })// to use top App Bar and Floating Action Button
    { paddingValue->
        LazyColumn( state = listState,modifier = Modifier
            .fillMaxSize()
            .padding(paddingValue)) {

            item { SubjectCountCardsSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                studiedHours = "2",
                goalHours ="5",
                progress = 0.5F
            ) }
            taskList(
                sectionTitle = "UPCOMING TASKS",
                emptyListText = "You Don't Have any Upcoming Tasks.\n" + " Click on the + Button to add Tasks.",
                tasks = taskDisplay,//emptylist
                onCheckBoxClick = {},
                onTaskCardClick = onTaskCardClick
            )
            item { Spacer(modifier = Modifier.height(12.dp)) }
            taskList(
                sectionTitle = "COMPLETED TASKS",
                emptyListText = "You Don't Have any Upcoming Tasks.\n" + " Click on the CHeckbox on completion of  Tasks.",
                tasks = taskDisplay,//emptylist
                onCheckBoxClick = {},
                onTaskCardClick = onTaskCardClick
            )
            item { Spacer(modifier = Modifier.height(12.dp)) }
            StudySessionsList(
                sectionTitle = "RECENT STUDY SESSIONS",
                emptyListText = "You Don't Have any Study Sessions Yet.\n" + " Click on the  Button to Start a Study Session.",
                session = Sess,//emptyList()
                onDeleteIconClick = { isDeleteSessionDialogOpen=true }
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectScreenTopBar(
    title:String,
    onBackButtonClick:()->Unit,
    onDeleteBttonClick:()->Unit,
    onEditButtonClick:()->Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    LargeTopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(onClick = { onBackButtonClick() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Navigate Back"
                )
            }
        },
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.headlineMedium
            )
        },
        actions = {
            IconButton(onClick = { onDeleteBttonClick() }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Subject")
            }
            IconButton(onClick = { onEditButtonClick() }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Subject")
            }
        })
}
@Composable
private fun SubjectCountCardsSection(
    modifier: Modifier,
    studiedHours:String,
    goalHours:String,
    progress:Float
){
    val progressPercent =remember(progress){
        (progress*100).toInt().coerceIn(1,100)
    }
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CountCard(modifier = Modifier.weight(1f), headingText = "Goal Study Hours", count = goalHours)
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(modifier = Modifier.weight(1f), headingText = " Study Hours", count = studiedHours)
        Spacer(modifier = Modifier.width(10.dp))
        Box(modifier = Modifier.size(75.dp), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize(),
                progress = 1f,
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Round,
                color = MaterialTheme.colorScheme.surfaceVariant)
            CircularProgressIndicator(modifier = Modifier.fillMaxSize(),
                progress = progress,
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Round)
            Text(text = "$progressPercent %")
        }
    }
}