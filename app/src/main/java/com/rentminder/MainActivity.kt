package com.rentminder

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.inputmethodservice.Keyboard.Row
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
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.sharp.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
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
                fontSize = 28.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 10.dp),
                fontWeight = FontWeight.Bold
            )
            EditBillAmounts()
        }
    }
}

//Navigation bar at the top of app
@Composable
fun TopToolBar() {
    Column() {
        TopAppBar(
            modifier = Modifier.height(65.dp),
            title = {
                Icon(
                    painterResource(
                        id = R.drawable.outline_notifications_active_24
                    ),
                    contentDescription = "Notification Icon",
                    modifier = Modifier
                        .size(45.dp)
                        .padding(
                            end = 5.dp
                        ).height(30.dp)
                )
                Text(
                    text = "RentMinder",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            actions = {
                IconButton (onClick = {}){
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Navigation Bar",
                        modifier = Modifier
                            .size(35.dp),
                        tint = Color.Black
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

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier.padding(horizontal = 10.dp), verticalArrangement = Arrangement.Center) {
        //Rent bill text, text box, and button
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth(0.95f)) {
            RentIconText()
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()){
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
                    modifier = Modifier
                        .width(100.dp)
                        .height(55.dp),
                    textStyle = TextStyle.Default.copy(fontSize = 18.sp)
                )
                SaveRemindButton()
            }
        }
    }

    //Electric-Gas bill text, text box, and button
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth(0.9f)) {
        ElectricIconText()
        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()){
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
                modifier = Modifier
                    .width(100.dp)
                    .height(55.dp),
                textStyle = TextStyle.Default.copy(fontSize = 18.sp)
            )
            SaveRemindButton()
        }
    }

    //Water-Sewer bill text, text box, and button
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth(0.9f)) {
        WaterIconText()
        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()){
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
                modifier = Modifier
                    .width(100.dp)
                    .height(55.dp),
                textStyle = TextStyle.Default.copy(fontSize = 18.sp)
            )
            SaveRemindButton()
        }
    }

    //Wi-Fi bill text, text box, and button
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth(0.9f)) {
        WiFiIconText()
        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()){
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
                modifier = Modifier
                    .width(100.dp)
                    .height(55.dp),
                textStyle = TextStyle.Default.copy(fontSize = 18.sp)
            )
            SaveRemindButton()
        }
    }

    //Other bill text, text box, and button
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth(0.9f)) {
        OtherIconText()
        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()){
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
                modifier = Modifier
                    .width(100.dp)
                    .height(55.dp),
                textStyle = TextStyle.Default.copy(fontSize = 18.sp)
            )
            SaveRemindButton()
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