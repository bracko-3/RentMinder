package com.rentminder

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.inputmethodservice.Keyboard.Row
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
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

    Column {
        TopNavBarColumn()
        CalendarNameColumn()
        RentBillColumn()
    }
}

/**
 * 1. Column (TopNav)
 * Creates navigation bar at the top of the app.
 */
@Composable
fun TopNavBarColumn() {
    Column {
        TopAppBar(
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
                        )
                )
                Text(
                    text = "RentMinder",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            actions = {
                IconButton(onClick = {}) {
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

/**
 * 2. Column (Calendar Display Name)
 * Displays current calendar in a month-only format
 */
@Composable
fun CalendarNameColumn()
{
    //Getting current month for Main Menu
    val calendar: Calendar = Calendar.getInstance()
    val monthDate = SimpleDateFormat("MMMM")
    val monthName: String = monthDate.format(calendar.time)

    Row(modifier = Modifier
        .padding(
            top = 5.dp,
            bottom = 10.dp
        )
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = monthName,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * 3. Column (All type of bills)
 * Bill input boxes, buttons, and text fields.
 *
 * 1. Row (Rent)
 * Properties:
 *
 * 2. Row (Electric)
 * Properties: 
 *
 * 3. Row (Water)
 * Properties:
 *
 * 4. Row (Wifi)
 * Properties:
 *
 * 5. Row (Miscellaneous)
 * Properties:
 *
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RentBillColumn() {
    var rentBill by remember { mutableStateOf("") }
    var electricBill by remember { mutableStateOf("") }
    var waterBill by remember { mutableStateOf("") }
    var wifiBill by remember { mutableStateOf("") }
    var otherBill by remember { mutableStateOf("") }
    var totalBill by remember { mutableStateOf("") }
    var dividedBill by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    //Column for all bill rows
    Column(modifier = Modifier
        .padding(
            horizontal = 15.dp
        )
    ) {
        //1. Row (Rent)
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 10.dp)) {
            setupRentTextProperties()
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
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

        //2. Row (Electric)
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 10.dp)) {
            setupElectricTextProperties()
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
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

        //3. Row (Water)
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 10.dp)) {
            setupWaterTextProperties()
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
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

        //4. Row (Wifi)
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 10.dp)) {
            setupWifiTextProperties()
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
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

        //5. Row (Miscellaneous)
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 10.dp)) {
            setupMiscellaneousTextProperties()
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
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

        //Line Dividing utility rows and totals
        Divider(color = Color.Black, thickness = Dp.Hairline)


    }
}
@Composable
fun SaveRemindButton() {
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
    SaveRemindButton()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RentMinderTheme {
        MainMenu()
    }
}