package com.rentminder

import android.util.Log
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
fun SaveRemindButton(enabled: Boolean, inputEdited: MutableState<Boolean>) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    var saved by remember { mutableStateOf(false) }

    IconButton(
        onClick = {
            try{
                if (enabled) {
                    if (saved) {
                        Toast.makeText(context, R.string.edit_saved_message, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, R.string.saved_message, Toast.LENGTH_SHORT).show()
                    }
                    saved = true
                    inputEdited.value = false
                    focusManager.clearFocus()
                } else {
                    Toast.makeText(
                        context,
                        R.string.enter_value_message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch(e: Exception){
                Log.e("BtnClickEvent", "Error while handling click event", e)
            }

        },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (saved && enabled && !inputEdited.value) {
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
    val inputEdited = remember { mutableStateOf(false) }
    SaveRemindButton(enabled, inputEdited)
}