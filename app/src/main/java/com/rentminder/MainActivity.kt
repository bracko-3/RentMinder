package com.rentminder

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.sharp.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentminder.ui.theme.RentMinderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RentMinderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
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
        Row(
            modifier = Modifier
                .padding(
                    top = 5.dp, bottom = 10.dp
                )
                .fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = monthName, fontSize = 28.sp, fontWeight = FontWeight.Bold
            )
        }
        EditBillAmounts()
    }
}

//Navigation bar at the top of app
@Composable
fun TopToolBar() {
    Column {
        TopAppBar(title = {
            Icon(
                painterResource(
                    id = R.drawable.outline_notifications_active_24
                ),
                contentDescription = "Notification Icon",
                modifier = Modifier
                    .size(45.dp)
                    .padding(
                        end = 5.dp
                    )
            )
            Text(
                text = "RentMinder", fontSize = 25.sp, fontWeight = FontWeight.Bold
            )
        }, actions = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Navigation Bar",
                    modifier = Modifier.size(35.dp),
                    tint = Color.Black
                )
            }
        })
    }
}

//Bill input boxes, buttons, and text fields
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditBillAmounts() {
    var rentBill by remember { mutableStateOf("") }
    val rentBillEdited = remember { mutableStateOf(false) }
    var electricBill by remember { mutableStateOf("") }
    val electricBillEdited = remember { mutableStateOf(false) }
    var waterBill by remember { mutableStateOf("") }
    val waterBillEdited = remember { mutableStateOf(false) }
    var wifiBill by remember { mutableStateOf("") }
    val wifiBillEdited = remember { mutableStateOf(false) }
    var otherBill by remember { mutableStateOf("") }
    val otherBillEdited = remember { mutableStateOf(false) }
    var totalBill by remember { mutableStateOf("") }
    val totalBillEdited = remember { mutableStateOf(false) }
    var totalperpersonBill by remember { mutableStateOf("") }
    val totalperpersonBillEdited = remember { mutableStateOf(false) }


    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    //Column for all bill rows
    Column(
        modifier = Modifier.padding(
            horizontal = 15.dp
        )
    ) {

        //Rent bill text, text box, and button
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 10.dp)
        ) {
            RentIconText()
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                OutlinedTextField(
                    value = rentBill,
                    onValueChange = { newRentBill ->
                        rentBill = newRentBill
                        rentBillEdited.value = true
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }),
                    modifier = Modifier
                        .width(100.dp)
                        .height(55.dp),
                    textStyle = TextStyle.Default.copy(fontSize = 18.sp)
                )
                SaveRemindButton(enabled = rentBill.isNotEmpty(), inputEdited = rentBillEdited)
            }
        }

        //Electric-Gas bill text, text box, and button
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 10.dp)
        ) {
            ElectricIconText()
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                OutlinedTextField(
                    value = electricBill,
                    onValueChange = { newElectricBill ->
                        electricBill = newElectricBill
                        electricBillEdited.value = true
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }),
                    modifier = Modifier
                        .width(100.dp)
                        .height(55.dp),
                    textStyle = TextStyle.Default.copy(fontSize = 18.sp)
                )
                SaveRemindButton(
                    enabled = electricBill.isNotEmpty(), inputEdited = electricBillEdited
                )
            }
        }

        //Water-Sewer bill text, text box, and button
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 10.dp)
        ) {
            WaterIconText()
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                OutlinedTextField(
                    value = waterBill,
                    onValueChange = { newWaterBill ->
                        waterBill = newWaterBill
                        waterBillEdited.value = true
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }),
                    modifier = Modifier
                        .width(100.dp)
                        .height(55.dp),
                    textStyle = TextStyle.Default.copy(fontSize = 18.sp)
                )
                SaveRemindButton(
                    enabled = waterBill.isNotEmpty(), inputEdited = waterBillEdited
                )
            }
        }

        //Wi-Fi bill text, text box, and button
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 10.dp)
        ) {
            WiFiIconText()
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                OutlinedTextField(
                    value = wifiBill,
                    onValueChange = { newWifiBill ->
                        wifiBill = newWifiBill
                        wifiBillEdited.value = true
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }),
                    modifier = Modifier
                        .width(100.dp)
                        .height(55.dp),
                    textStyle = TextStyle.Default.copy(fontSize = 18.sp)
                )
                SaveRemindButton(
                    enabled = wifiBill.isNotEmpty(), inputEdited = wifiBillEdited
                )
            }
        }

        //Other bill text, text box, and button
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 10.dp)
        ) {
            OtherIconText()
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                OutlinedTextField(
                    value = otherBill,
                    onValueChange = { newOtherBill ->
                        otherBill = newOtherBill
                        otherBillEdited.value = true
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }),
                    modifier = Modifier
                        .width(100.dp)
                        .height(55.dp),
                    textStyle = TextStyle.Default.copy(fontSize = 18.sp)
                )
                SaveRemindButton(
                    enabled = otherBill.isNotEmpty(), inputEdited = otherBillEdited
                )
            }
        }

        //Line Dividing utility rows and totals
        Divider(color = Color.Black, thickness = Dp.Hairline)

        //Total feature
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 10.dp)
        ) {
            TotalText()
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {

                OutlinedTextField(
                    value = totalBill,
                    onValueChange = { newTotalBill ->
                        totalBill = newTotalBill
                        totalBillEdited.value = true
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }),
                    modifier = Modifier
                        .width(100.dp)
                        .height(55.dp),
                    textStyle = TextStyle.Default.copy(fontSize = 18.sp)
                )
                SaveRemindButton(
                    enabled = totalBill.isNotEmpty(), inputEdited = totalBillEdited
                )
            }

        }

        //Total Per Person Feature
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 10.dp)
        ) {
            TotalPerPersonText()
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {

                OutlinedTextField(
                    value = totalperpersonBill,
                    onValueChange = { newtotalperpersonBill ->
                        totalperpersonBill = newtotalperpersonBill
                        totalBillEdited.value = true
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }),
                    modifier = Modifier
                        .width(100.dp)
                        .height(55.dp),
                    textStyle = TextStyle.Default.copy(fontSize = 18.sp)
                )
                SaveRemindButton(
                    enabled = totalperpersonBill.isNotEmpty(), inputEdited = totalperpersonBillEdited
                )

            }
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