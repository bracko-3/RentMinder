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
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.sharp.Home
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
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
import com.rentminder.dto.Payment
import com.rentminder.dto.Bill
import com.rentminder.ui.theme.RentMinderTheme
import com.rentminder.ui.theme.SoftGreen
import org.koin.androidx.viewmodel.ext.android.viewModel

//Getting current month for Main Menu
val cal: Calendar = Calendar.getInstance()
val monthDate = SimpleDateFormat("MMMM")
val monthName: String = monthDate.format(cal.time)

private var selectedPayment: Bill? = null


class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModel()
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

    @Composable
    fun MainMenu() {
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
        var inRentBill by remember { mutableStateOf("") }
        val rentBillEdited = remember { mutableStateOf(false) }
        var inElectricBill by remember { mutableStateOf("") }
        val electricBillEdited = remember { mutableStateOf(false) }
        var inWaterBill by remember { mutableStateOf("") }
        val waterBillEdited = remember { mutableStateOf(false) }
        var inWifiBill by remember { mutableStateOf("") }
        val wifiBillEdited = remember { mutableStateOf(false) }
        var inOtherBill by remember { mutableStateOf("") }
        val otherBillEdited = remember { mutableStateOf(false) }
        var inTotalBill by remember { mutableStateOf("") }
        var inDividedBill by remember { mutableStateOf("") }
        val textFields: List<MutableState<Boolean>> = listOf(rentBillEdited, electricBillEdited, waterBillEdited, wifiBillEdited, otherBillEdited)
        val context = LocalContext.current
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
                        value = inRentBill,
                        onValueChange = { newRentBill ->
                            inRentBill = newRentBill
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
                    SaveRemindButton(enabled = inRentBill.isNotEmpty(), inputEdited = rentBillEdited)
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
                        value = inElectricBill,
                        onValueChange = { newElectricBill ->
                            inElectricBill = newElectricBill
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
                        enabled = inElectricBill.isNotEmpty(), inputEdited = electricBillEdited
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
                        value = inWaterBill,
                        onValueChange = { newWaterBill ->
                            inWaterBill = newWaterBill
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
                        enabled = inWaterBill.isNotEmpty(), inputEdited = waterBillEdited
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
                        value = inWifiBill,
                        onValueChange = { newWifiBill ->
                            inWifiBill = newWifiBill
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
                        enabled = inWifiBill.isNotEmpty(), inputEdited = wifiBillEdited
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
                        value = inOtherBill,
                        onValueChange = { newOtherBill ->
                            inOtherBill = newOtherBill
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
                        enabled = inOtherBill.isNotEmpty(), inputEdited = otherBillEdited
                    )
                }
            }
            //Line Dividing utility rows and totals
            Divider(color = Color.Black, thickness = Dp.Hairline)

            //Total feature
            Row() {
                if(inTotalBill.equals("")){
                    TotalText(0.0)
                }
                else{
                    TotalText(inTotalBill.toDouble())
                }
            }

            //Total Per Person Feature
            Row() {
                TotalPerPersonText()
            }

            Column (horizontalAlignment = CenterHorizontally, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 13.dp)) {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = SoftGreen),
                    onClick = {
                        textFields.forEach {
                            if(it.value) {
                                inTotalBill = (inRentBill.toInt() + inElectricBill.toInt() + inWaterBill.toInt() + inWifiBill.toInt() + inOtherBill.toInt()).toString()
                                viewModel.selectedPayment.apply {
                                    month = monthName
                                    paymentId = selectedPayment?.let {
                                        bill ->
                                            bill.paymentId
                                        } ?: -1
                                    rentBill = inRentBill.toInt()
                                    energyBill = inElectricBill.toInt()
                                    waterBill = inWaterBill.toInt()
                                    wifiBill = inWifiBill.toInt()
                                    otherBill = inOtherBill.toInt()
                                    total = inTotalBill.toDouble()
                                }
                                viewModel.save()
                                Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()
                            }
                            else {
                                Toast.makeText(
                                    context,
                                    "Please make sure all boxes have a value.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
                {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Save & Remind Button",
                        Modifier.padding(2.dp)
                    )
                    Text(text = "Save & Remind")
                }

                Row(modifier = Modifier.height(IntrinsicSize.Min).padding(top = 4.dp)) {
                    Row{
                        Button (
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                            elevation = null,
                            modifier = Modifier.padding(top = 8.dp),
                            onClick = { /*TODO*/ }
                        ){
                            Icon(
                                imageVector = Icons.Outlined.People,
                                contentDescription = "Members Button",
                                Modifier.padding(2.dp)
                            )
                            Text(text = "Members")
                        }
                    }
                    Divider(
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.5.dp)
                            .padding(top = 10.dp)
                    )
                    Row{
                        Button (
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                            elevation = null,
                            modifier = Modifier.padding(top = 8.dp),
                            onClick = { /*TODO*/ }
                        ){
                            Icon(
                                imageVector = Icons.Outlined.Timelapse,
                                contentDescription = "Past Bills Button",
                                Modifier.padding(2.dp)
                            )
                            Text(text = "Past Bills")
                        }
                    }
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
}