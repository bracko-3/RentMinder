package com.rentminder

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun SaveRemindButton(enabled: Boolean, count: MutableState<Int>, onInputChange: () -> Unit) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    IconButton(
        onClick = {
            if (enabled) {
                if (count.value > 0) {
                    Toast.makeText(context, "Edit Saved!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()
                }
                count.value++
                focusManager.clearFocus()
            } else {
                Toast.makeText(
                    context,
                    "Please enter a value before saving or editing",
                    Toast.LENGTH_SHORT
                ).show()
            }
        },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (count.value > 0 && enabled) {
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
    val enabled = false
    val count = remember { mutableStateOf(0) }
    SaveRemindButton(enabled, count) {

    }
}
