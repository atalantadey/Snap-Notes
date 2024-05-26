package com.snapnotes.presentation.componenets

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDatePicker(
    state: DatePickerState,
    isOpen: Boolean,
    confirmButton: String = "OK",
    dismissButton: String = "Cancel",
    onDismissRequest: () -> Unit,
    onConfirmButtonClicked: () -> Unit
) {
    if(isOpen){
        DatePickerDialog(
            onDismissRequest = { onDismissRequest() },
            confirmButton = { TextButton(onClick = {onConfirmButtonClicked()}) {
                Text(text = confirmButton)
            } },
            dismissButton = {
                TextButton(onClick = { onDismissRequest() }) {
                    Text(text = dismissButton)
                }
            }, content = {
                DatePicker(state = state, dateValidator = {timestamp->
                    val selectedDate =Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate()
                    val currentDate =LocalDate.now(ZoneId.systemDefault())
                    selectedDate>=currentDate
                })
            })
    }
}