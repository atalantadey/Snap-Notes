package com.snapnotes.presentation.session

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.snapnotes.Sess
import com.snapnotes.presentation.componenets.DeleteDialog
import com.snapnotes.presentation.componenets.StudySessionsList
import com.snapnotes.presentation.componenets.SubjectListBotSheet
import com.snapnotes.subjects
import kotlinx.coroutines.launch

@Destination
@Composable
fun SessionScreenRoute(navigator: DestinationsNavigator){
    SessionScreen(onBackClick = {navigator.navigateUp()})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SessionScreen(
    onBackClick: () -> Unit
) {

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var isBottomSheetOpen by remember { mutableStateOf(false) }
    var isDeleteDialogOpen by rememberSaveable { mutableStateOf(false) }

    SubjectListBotSheet(
        sheetState = sheetState,
        isOpen = isBottomSheetOpen,
        subjects = subjects,
        onSubjectClicked = {
            scope.launch { sheetState.hide() }.invokeOnCompletion { if(!sheetState.isVisible) isBottomSheetOpen=false }
        },
        onDismissRequest = { isBottomSheetOpen = false })


    DeleteDialog(
        isOpen = isDeleteDialogOpen,
        title = "Delete Task?",
        onDismissRequest = { isDeleteDialogOpen = false },
        onConfirmButton = { isDeleteDialogOpen = true },
        bodyText = "Are you sure, you want to Delete this Task?"
    )


    Scaffold(topBar = {
        SessionTopbar(onBackClick = onBackClick)
    }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                Timer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                )
            }
            item {
                RelatedToSubject(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 12.dp
                        ),
                relatedToSubject = "English", selectSubjectClick = {isBottomSheetOpen=true}
                )
            }
            item {
                Buttons(
                    startBtn = { },
                    cancelBtn = { },
                    FinishBtn = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                )
            }
            StudySessionsList(
                sectionTitle = "RECENT STUDY HISTORY",
                emptyListText = "You Don't Have any Study Sessions Yet.\n" + " Click on the  Button to Start a Study Session.",
                session = Sess,//emptyList()
                onDeleteIconClick = { isDeleteDialogOpen=true }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionTopbar(
    onBackClick: () -> Unit
) {
    TopAppBar(title = {
        Text(
            text = "Study Session",
            style = MaterialTheme.typography.headlineSmall
        )
    }, navigationIcon = {
        IconButton(onClick = { onBackClick() }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Navigate Back")
        }
    })

}

@Composable
fun Timer(
    modifier: Modifier
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .size(250.dp)
                .border(
                    5.dp,
                    MaterialTheme.colorScheme.surfaceVariant,
                    CircleShape
                )
        )
        Text(text = "00:05:32", style = MaterialTheme.typography.titleLarge.copy(fontSize = 45.sp))
    }

}

@Composable
fun RelatedToSubject(
    modifier: Modifier,
    relatedToSubject: String,
    selectSubjectClick: () -> Unit
) {
    Column(modifier = modifier) {
        Text(text = "Related To Subject", style = MaterialTheme.typography.bodySmall)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = relatedToSubject, style = MaterialTheme.typography.bodyLarge)
            IconButton(onClick = { selectSubjectClick() }) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Select Subject"
                )
            }
        }
    }
}

@Composable
fun Buttons(
    modifier: Modifier,
    startBtn: () -> Unit,
    cancelBtn: () -> Unit,
    FinishBtn: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(onClick = { cancelBtn() }) {
            Text(text = "Cancel", modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp))
        }
        Button(onClick = { startBtn() }) {
            Text(text = "Start", modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp))
        }
        Button(onClick = { FinishBtn() }) {
            Text(text = "Finish", modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp))
        }
    }
}