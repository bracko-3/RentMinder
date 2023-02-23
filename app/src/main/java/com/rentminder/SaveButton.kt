package com.rentminder

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp

@Composable
fun SaveRemindButton() {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    var count by remember { mutableStateOf(0) }

    OutlinedButton(
        onClick = {
            if(count > 0) {Toast.makeText(context, "Edit Saved!", Toast.LENGTH_SHORT).show()}
            else {Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()}
            count++
            focusManager.clearFocus()
                  },
        modifier = Modifier
            .offset(
                x = -10.dp
            ),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        shape = RoundedCornerShape(100),
        border = BorderStroke(0.dp, Color.Transparent)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (count > 0) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = "Saved Icon",
                    tint = Color.Green,
                    modifier = Modifier.size(50.dp)
                )
            }
            else {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Icon",
                    tint = Color.Black,
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    }
}
@ExperimentalMaterialApi
@Composable
@Preview
private fun SaveRemindButtonPreview() {
    SaveRemindButton()
}