package com.snapnotes.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.snapnotes.presentation.theme.gradient2
import com.snapnotes.presentation.theme.gradient3
import com.snapnotes.presentation.theme.gradient4
import com.snapnotes.presentation.theme.gradient5
import com.snapnotes.presentation.theme.gradientl

@Entity
data class Subject(
    val name:String,
    val goalHours:String,
    val colors:List<Color>,
    @PrimaryKey(autoGenerate = true)
    val subjectId:Int?=null
){
    companion object{
        val subjectCardColor = listOf(gradientl, gradient2, gradient3, gradient4, gradient5)
    }
}
