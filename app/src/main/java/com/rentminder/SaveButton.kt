package com.rentminder

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun SaveRemindButton(enabled: Boolean) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val shape = RoundedCornerShape(size = 16.dp)
    var count by remember { mutableStateOf(0) }

    IconButton(
        onClick = {
            if (count > 0) {
                Toast.makeText(context, "Edit Saved!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()
            }
            count++
            focusManager.clearFocus()
        },
        enabled = enabled
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (count > 0) {
                Icon(
                    painterResource(id = R.drawable.outline_check_circle_outline_24),
                    contentDescription = "Saved Icon",
                    tint = Color.Green,
                    modifier = Modifier
                        .size(50.dp)
                )
            } else {
                Icon(
                    painterResource(id = R.drawable.outline_data_saver_on_24),
                    contentDescription = "Add Icon",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(50.dp)
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
@Preview
private fun SaveRemindButtonPreview() {
    val enabled = false;
    SaveRemindButton(enabled)
}