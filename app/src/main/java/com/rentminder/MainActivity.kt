package com.rentminder

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentminder.ui.theme.LavenderBlue
import com.rentminder.ui.theme.RentMinderTheme
import com.rentminder.ui.theme.SoftGreen

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
    //Getting current month for Main Menu
    val cal: Calendar = Calendar.getInstance()
    val monthDate = SimpleDateFormat("MMMM")
    val monthName: String = monthDate.format(cal.time)

    Column() {
        TopToolBar()
        //Second column to center the body of the page
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {
            Text(
                text = monthName,
                fontSize = 25.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Bold
            )
            EditBillAmounts()
        }
    }
}

//Navigation bar at the top of app
@Composable
fun TopToolBar() {
    val contextForToast = LocalContext.current.applicationContext

    Column() {
        TopAppBar(
            modifier = Modifier.height(70.dp),
            backgroundColor = LavenderBlue,
            title = {
                Text(text = "RentMinder", fontSize = 25.sp)
            },
            actions = {
                IconButton(
                    onClick = {
                        Toast.makeText(
                            contextForToast,
                            "Navigation Icon Click",
                            Toast.LENGTH_SHORT
                        ).show()
                    }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Menu Icon"
                    )
                }
            }
        )
    }
}

//Bill input boxes, buttons, and text fields
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
        modifier = Modifier.padding(horizontal = 31.dp, vertical = 0.dp),
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        //Rent bill text, text box, and button
        Text(text = "Rent Bill:", fontSize = (18.sp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = rentBill,
                onValueChange = { newRentBill -> rentBill = newRentBill },
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
            SaveRemindButton()
        }

        //Electric-Gas bill text, text box, and button
        Text(text = "Electric/Gas Bill:", fontSize = (18.sp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = electricBill,
                onValueChange = { newElectricBill -> electricBill = newElectricBill },
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
            SaveRemindButton()
        }

        //Water-Sewer bill text, text box, and button
        Text(text = "Water/Sewer Bill:", fontSize = (18.sp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = waterBill,
                onValueChange = { newWaterBill -> waterBill = newWaterBill },
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
            SaveRemindButton()
        }

        //Wi-Fi bill text, text box, and button
        Text(text = "WiFi Bill:", fontSize = (18.sp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = wifiBill,
                onValueChange = { newWifiBill -> wifiBill = newWifiBill },
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
            SaveRemindButton()
        }

        //Other bill text, text box, and button
        Text(text = "Other Bill(s):", fontSize = (18.sp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = otherBill,
                onValueChange = { newOtherBill -> otherBill = newOtherBill },
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
            SaveRemindButton()
        }
    }
    Column(horizontalAlignment = CenterHorizontally) {
        Button(
            onClick = {Toast.makeText(context, "Saved!", Toast.LENGTH_LONG).show()},
            modifier = Modifier.align(CenterHorizontally).padding(top = 5.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = SoftGreen)
        ){Text(text = "Save & Remind")}
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RentMinderTheme {
        MainMenu()
    }
}