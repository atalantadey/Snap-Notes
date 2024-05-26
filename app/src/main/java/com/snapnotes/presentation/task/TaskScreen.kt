package com.snapnotes.presentation.task

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.snapnotes.Util.Priority
import com.snapnotes.Util.changeMillistoDateString
import com.snapnotes.presentation.componenets.DeleteDialog
import com.snapnotes.presentation.componenets.SubjectListBotSheet
import com.snapnotes.presentation.componenets.TaskDatePicker
import com.snapnotes.presentation.componenets.taskCheckBox
import com.snapnotes.presentation.theme.Green
import com.snapnotes.subjects
import kotlinx.coroutines.launch
import java.time.Instant

data class TaskScreenNav(val TaskId:Int?, val subjectId:Int?)
@Destination(navArgsDelegate = TaskScreenNav::class)
@Composable
fun TaskScreenRoute(navigator: DestinationsNavigator){

    val viewModel:TaskViewModell= hiltViewModel()

    TaskScreen(onBackButtonClick = {navigator.navigateUp()})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskScreen(
    onBackButtonClick: () -> Unit,

) {
    var isDatePickerOpen by rememberSaveable { mutableStateOf(false) }
    var datePickerState =
        rememberDatePickerState(initialSelectedDateMillis = Instant.now().toEpochMilli())
    var isDeleteDialogOpen by rememberSaveable { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var sheetState = rememberModalBottomSheetState()
    var isBottomSheetOpen by remember { mutableStateOf(false) }
    val scope= rememberCoroutineScope()

    var taskTitleError by rememberSaveable {
        mutableStateOf<String?>(null)
    }
    taskTitleError = when {
        title.isBlank() -> "Please Enter a Task Title"
        title.length < 4 -> "Task Title Too Short"
        title.length > 30 -> "Task Title Too Long"
        else -> null
    }

    DeleteDialog(
        isOpen = isDeleteDialogOpen,
        title = "Delete Task?",
        onDismissRequest = { isDeleteDialogOpen = false },
        onConfirmButton = { isDeleteDialogOpen = true },
        bodyText = "Are you sure, you want to Delete this Task?"
    )

    TaskDatePicker(
        state = datePickerState,
        isOpen = isDatePickerOpen,
        onDismissRequest = { isDatePickerOpen = false },
        onConfirmButtonClicked = { isDatePickerOpen = true })
    SubjectListBotSheet(
        sheetState = sheetState,
        isOpen = isBottomSheetOpen,
        subjects = subjects,
        onSubjectClicked = {
            scope.launch { sheetState.hide() }.invokeOnCompletion { if(!sheetState.isVisible) isBottomSheetOpen=false }
        },
        onDismissRequest = { isBottomSheetOpen = false })

    Scaffold(topBar = {
        TaskTopBar(
            isTaskExist = true,
            isComplete = false,
            checkBoxBorderColours = Green,
            onBackButtonClick =  onBackButtonClick ,
            onDeleteButtonClick = { isDeleteDialogOpen = true },
            onCheckBoxClick = {})
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 12.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(
                        text = "Task Title"
                    )
                },
                singleLine = true,
                isError = taskTitleError != null && title.isNotBlank(),
                supportingText = {
                    Text(
                        text = taskTitleError.orEmpty()
                    )
                })
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Task Description") })
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Due Date", style = MaterialTheme.typography.bodySmall)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = datePickerState.selectedDateMillis.changeMillistoDateString(),
                    style = MaterialTheme.typography.bodyLarge
                )
                IconButton(onClick = { isDatePickerOpen = true }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select Due Date"
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Priority", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Priority.entries.forEach { priority: Priority ->
                    priorityButton(
                        modifier = Modifier.weight(1f),
                        label = priority.title,
                        backgroundColor = priority.color,
                        borderColor = if (priority == Priority.MEDIUM) {
                            Color.White
                        } else Color.Transparent,
                        labelColor = if (priority == Priority.MEDIUM) {
                            Color.White
                        } else Color.White.copy(0.7f), onClick = {}
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Related To Subject", style = MaterialTheme.typography.bodySmall)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "English", style = MaterialTheme.typography.bodyLarge)
                IconButton(onClick = { isBottomSheetOpen=true }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Select Subject"
                    )
                }
            }
            Button(
                onClick = { /*TODO*/ }, enabled = taskTitleError == null, modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 40.dp)
            ) {
                Text(text = "Save")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskTopBar(
    isTaskExist: Boolean,
    isComplete: Boolean,
    checkBoxBorderColours: Color,
    onBackButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    onCheckBoxClick: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = "Tasks", style = MaterialTheme.typography.headlineMedium) },
        navigationIcon = {
            IconButton(onClick = { onBackButtonClick() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Navigate Back")
            }
        },
        actions = {
            if (isTaskExist) {
                taskCheckBox(
                    isComplete = isComplete,
                    borderColor = checkBoxBorderColours,
                    onCheckBoxClick = onCheckBoxClick
                )
                IconButton(onClick = { onDeleteButtonClick() }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Task")
                }
            }
        })
}

@Composable
fun priorityButton(
    modifier: Modifier = Modifier,
    label: String,
    backgroundColor: Color,
    borderColor: Color,
    labelColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(5.dp)
            .border(1.dp, borderColor, RoundedCornerShape(5.dp))
            .padding(5.dp), contentAlignment = Alignment.Center
    ) {
        Text(text = label, color = labelColor)
    }
}