package com.rentminder

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentminder.ui.theme.RentMinderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RentMinderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainMenu()
                }
            }
        }
    }
}

@Composable
fun MainMenu() {
    Column() {
        TopToolBar()
        //Second column to center the body of the page
        Column(modifier = Modifier.padding(horizontal = 12.dp).padding(vertical = 15.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = stringResource(id = R.string.current_month),
                fontSize = 24.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Bold
            )
            EditBillAmounts()
        }
    }
}

@Composable
fun TopToolBar() {
    Column() {
        TopAppBar(
            title = {
                Text(text = "Rentminder")
            }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditBillAmounts() {
    var rentBill by remember { mutableStateOf("") }
    var electricBill by remember { mutableStateOf("") }
    var waterBill by remember { mutableStateOf("") }
    var wifiBill by remember { mutableStateOf("") }
    var otherBill by remember { mutableStateOf("") }
    var totalBill by remember { mutableStateOf("") }
    var dividedBill by remember { mutableStateOf("") }
    val context = LocalContext.current

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.padding(horizontal = 31.dp, vertical = 5.dp),
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {

        //Rent bill text and input box
        Text(text = "Rent Bill:", fontSize = (18.sp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = rentBill,
                onValueChange = { rentBill = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                ),
                modifier = Modifier.width(180.dp),
                textStyle = TextStyle.Default.copy(fontSize = 18.sp)
            )
            Spacer(modifier = Modifier.width(40.dp))
            Button(
                onClick = {
                    Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.width(88.dp)
            ) {
                Text(text = "Save & Remind")
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
        Text(text = "Water/Sewer Bill", fontSize = (18.sp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = waterBill,
                onValueChange = { waterBill = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                ),
                modifier = Modifier.width(180.dp),
                textStyle = TextStyle.Default.copy(fontSize = 18.sp)
            )
            Spacer(modifier = Modifier.width(40.dp))
            Button(
                onClick = {
                    Toast.makeText(context, "Water Bill Saved!", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.width(88.dp)
            ) {
                Text(text = "Save & Remind")
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

            Text(text = "WiFi Bill", fontSize = (18.sp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextField(
                    value = wifiBill,
                    onValueChange = { wifiBill = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        }
                    ),
                    modifier = Modifier.width(180.dp),
                    textStyle = TextStyle.Default.copy(fontSize = 18.sp)
                )
                Spacer(modifier = Modifier.width(40.dp))
                Button(
                    onClick = {
                        Toast.makeText(context, "WiFi Bill Saved!", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.width(88.dp)
                ) {
                    Text(text = "Save & Remind")
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Other Bills:", fontSize = (18.sp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RentMinderTheme {
        MainMenu()
    }
}