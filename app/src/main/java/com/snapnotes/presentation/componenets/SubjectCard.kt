package com.snapnotes.presentation.componenets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.snapnotes.R

@Composable
fun SubjectCad(

    modifier: Modifier=Modifier,
    SubjectName:String,
    gradientColor:List<Color>,
    onClick:()->Unit

) {
    Box(modifier =modifier.size(150.dp).clickable { onClick() }.background(
        brush = Brush.verticalGradient(gradientColor, startY = 0.00f, endY = Float.POSITIVE_INFINITY, tileMode = TileMode.Clamp),
        shape = MaterialTheme.shapes.medium
    )) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.book),
                contentDescription =SubjectName,
                modifier = Modifier.size(80.dp).padding(bottom = 5.dp)
            )
            Text(text = SubjectName, style = MaterialTheme.typography.headlineSmall, color = Color.White)
        }
    }
}