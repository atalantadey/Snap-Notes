package com.snapnotes.presentation.componenets

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
@Composable
fun DeleteDialog(
    isOpen: Boolean,
    title: String ,
    onDismissRequest:() -> Unit,
    onConfirmButton: () -> Unit,
    bodyText : String
) {
    if (isOpen) {
        AlertDialog(
            onDismissRequest =  onDismissRequest ,
            title = { Text(text = title, color = Color.Red) },
            text = { Text(text = bodyText) },
            confirmButton = {
                TextButton(onClick =  onConfirmButton ) {
                    Text(text = "Delete", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = {onDismissRequest()}  ) { Text(text = "Cancel") }
            })
    }

}