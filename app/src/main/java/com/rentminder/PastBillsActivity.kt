package com.rentminder

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Save
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentminder.dto.Bill
import com.rentminder.ui.theme.RentMinderTheme
import com.rentminder.ui.theme.SoftGreen
import org.koin.androidx.viewmodel.ext.android.viewModel

class PastBillsActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModel()
    private var selectedBill by mutableStateOf(Bill())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val bills by viewModel.bills.observeAsState(initial = emptyList())
            RentMinderTheme {
                Scaffold(topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "RentMinder",
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                startActivity(
                                    Intent(
                                        this@PastBillsActivity, MainActivity::class.java
                                    )
                                )
                            }) {
                                Icon(Icons.Filled.ArrowBack, "backIcon")
                            }
                        },
                    )
                }) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues = innerPadding)
                    ) {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colors.background
                        ) {
                            PaymentEditBillAmounts(bills, selectedBill)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun PaymentMenu(bills: List<Bill>) {
        var billText by remember { mutableStateOf("Select month") }
        var expanded by remember { mutableStateOf(false) } // state of the menu

        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Row(
                Modifier
                    .padding(24.dp)
                    .clickable { expanded = !expanded }
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically)
            {
                Text(text = billText, fontSize = 30.sp, modifier = Modifier.padding(end = 8.dp))
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    bills.forEach { bill ->
                        DropdownMenuItem(onClick = {
                            expanded = false
                            billText = bill.month
                            selectedBill = bill
                        }) {
                            Text(text = bill.month)
                        }
                    }
                }
            }
        }
    }

    //Bill input boxes, buttons, and text fields
    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun PaymentEditBillAmounts(bills: List<Bill> = ArrayList(), selectedBill: Bill = Bill()) {
        var inRentBill by remember(selectedBill.billId) { mutableStateOf(selectedBill.rentBill.toString()) }
        val rentBillEdited = remember { mutableStateOf(false) }
        var inElectricBill by remember(selectedBill.billId) { mutableStateOf(selectedBill.energyBill.toString()) }
        val electricBillEdited = remember { mutableStateOf(false) }
        var inWaterBill by remember(selectedBill.billId) { mutableStateOf(selectedBill.waterBill.toString()) }
        val waterBillEdited = remember { mutableStateOf(false) }
        var inWifiBill by remember(selectedBill.billId) { mutableStateOf(selectedBill.wifiBill.toString()) }
        val wifiBillEdited = remember { mutableStateOf(false) }
        var inOtherBill by remember(selectedBill.billId) { mutableStateOf(selectedBill.otherBill.toString()) }
        val otherBillEdited = remember { mutableStateOf(false) }
        var inTotalBill by remember(selectedBill.billId) { mutableStateOf(selectedBill.total.toString()) }
        var inDividedBill by remember(selectedBill.billId) { mutableStateOf("") }

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
            PaymentMenu(bills = bills)
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
                            .width(150.dp)
                            .height(55.dp),
                        textStyle = TextStyle.Default.copy(fontSize = 18.sp)
                    )
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
                            .width(150.dp)
                            .height(55.dp),
                        textStyle = TextStyle.Default.copy(fontSize = 18.sp)
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
                            .width(150.dp)
                            .height(55.dp),
                        textStyle = TextStyle.Default.copy(fontSize = 18.sp)
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
                            .width(150.dp)
                            .height(55.dp),
                        textStyle = TextStyle.Default.copy(fontSize = 18.sp)
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
                            .width(150.dp)
                            .height(55.dp),
                        textStyle = TextStyle.Default.copy(fontSize = 18.sp)
                    )
                }
            }

            //Line Dividing utility rows and totals
            Divider(color = Color.Black, thickness = Dp.Hairline)

            //Total Text
            Row() {
                if(inTotalBill == ""){
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

            //Save Button
            Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 13.dp)) {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = SoftGreen),
                    onClick = {
                        var message = ""
                        textFields.forEach {
                            if (it.value) {
                                inTotalBill =
                                    (inRentBill.toInt() + inElectricBill.toInt() + inWaterBill.toInt() + inWifiBill.toInt() + inOtherBill.toInt()).toString()
                                selectedBill.apply {
                                    month = selectedBill.month
                                    rentBill = inRentBill.toInt()
                                    energyBill = inElectricBill.toInt()
                                    waterBill = inWaterBill.toInt()
                                    wifiBill = inWifiBill.toInt()
                                    otherBill = inOtherBill.toInt()
                                    total = inTotalBill.toDouble()
                                }
                                viewModel.save(selectedBill)
                                message = "Saved successfully!"
                            } else {
                                message = "Please make sure all boxes have a value."
                            }
                        }
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    })
                {
                    Icon(
                        imageVector = Icons.Outlined.Save,
                        contentDescription = stringResource(R.string.saveRemindButtonDescription),
                        Modifier.padding(2.dp)
                    )
                    Text(text = "Save")
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun PaymentDefaultPreview() {
        RentMinderTheme {
            //PaymentMenu()
        }
    }
}