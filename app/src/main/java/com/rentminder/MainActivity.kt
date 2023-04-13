package com.rentminder

import android.app.NotificationChannel
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
import androidx.core.app.NotificationCompat
import com.rentminder.dto.Payment
import com.rentminder.dto.Bill
import com.rentminder.ui.theme.RentMinderTheme
import com.rentminder.ui.theme.SoftGreen
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.launch

//Getting current month for Main Menu
val cal: Calendar = Calendar.getInstance()
val monthDate = SimpleDateFormat("MMMM")
val monthName: String = monthDate.format(cal.time).replaceFirstChar { it.uppercase() }
private var selectedBill by mutableStateOf(Bill())

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val bills by viewModel.bills.observeAsState(initial = emptyList())
            RentMinderTheme {
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        TopToolBar(onNavigationIconClick = {
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        })
                    },
                    drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
                    drawerContent = {
                        DrawHeader()
                        DrawerBody(items = listOf(
                            MenuItem(
                                id = "members",
                                title = "Members",
                                contentDescription = "Members",
                                icon = Icons.Outlined.People
                            ),
                            MenuItem(
                                id = "pastBills",
                                title = "Past Bills",
                                contentDescription = "Past Bills",
                                icon = Icons.Outlined.Timelapse
                            ),
                            MenuItem(
                                id = "sysLogin",
                                title = "Login/Signup",
                                contentDescription = "System Login",
                                icon = Icons.Outlined.Login
                            )
                        ),
                            onItemClick = {
                                when(it.id) {
                                    "members" -> startActivity(Intent(this@MainActivity, MembersActivity::class.java))
                                    "pastBills" -> startActivity(Intent(this@MainActivity, PastBillsActivity::class.java))
                                }
                            })
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues = innerPadding)
                    ) {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colors.background
                        ) {
                            bills.forEach { bill ->
                                if (bill.month == monthName){
                                    selectedBill = bill
                                }
                            }
                            MainMenu()
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun MainMenu() {
        Column() {
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
    fun TopToolBar(
        onNavigationIconClick: () -> Unit
    ) {
        Column() {
            TopAppBar(title = {
                Text(
                    text = stringResource(R.string.appLabelTitle), fontSize = 25.sp, fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            },
                navigationIcon = {
                    IconButton(onClick = onNavigationIconClick) {
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
        var inRentBill by remember(selectedBill.month) { mutableStateOf(selectedBill.rentBill.toString()) }
        val rentBillEdited = remember { mutableStateOf(false) }
        var inElectricBill by remember(selectedBill.month) { mutableStateOf(selectedBill.energyBill.toString()) }
        val electricBillEdited = remember { mutableStateOf(false) }
        var inWaterBill by remember(selectedBill.month) { mutableStateOf(selectedBill.waterBill.toString()) }
        val waterBillEdited = remember { mutableStateOf(false) }
        var inWifiBill by remember(selectedBill.month) { mutableStateOf(selectedBill.wifiBill.toString()) }
        val wifiBillEdited = remember { mutableStateOf(false) }
        var inOtherBill by remember(selectedBill.month) { mutableStateOf(selectedBill.otherBill.toString()) }
        val otherBillEdited = remember { mutableStateOf(false) }
        var inTotalBill by remember(selectedBill.month) { mutableStateOf(selectedBill.total.toString()) }
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

            Column (horizontalAlignment = CenterHorizontally, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 13.dp)) {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = SoftGreen),
                    onClick = {
                        var message = ""
                        textFields.forEach {
                            if(it.value) {
                                inTotalBill = (inRentBill.toInt() + inElectricBill.toInt() + inWaterBill.toInt() + inWifiBill.toInt() + inOtherBill.toInt()).toString()
                                selectedBill.apply {
                                    month = monthName
                                    rentBill = inRentBill.toInt()
                                    energyBill = inElectricBill.toInt()
                                    waterBill = inWaterBill.toInt()
                                    wifiBill = inWifiBill.toInt()
                                    otherBill = inOtherBill.toInt()
                                    total = inTotalBill.toDouble()
                                }
                                viewModel.save(selectedBill)
                                message = "Saved!"

                                val notificationId = 1 // A unique ID for the notification
                                val channelId = "my_channel_id" // A unique ID for the notification channel
                                val notificationBuilder = NotificationCompat.Builder(context, channelId)
                                    .setSmallIcon(R.drawable.outline_notifications_active_24)
                                    .setContentTitle("RentMinder")
                                    .setContentText("Someone sent you a bill!")
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    val name = "RentMinder"
                                    val descriptionText = "Someone sent you a bill!"
                                    val importance = NotificationManager.IMPORTANCE_DEFAULT
                                    val channel = NotificationChannel(channelId, name, importance).apply {
                                        description = descriptionText
                                    }
                                    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                                    notificationManager.createNotificationChannel(channel)
                                }

                                val notificationManager = NotificationManagerCompat.from(context)
                                notificationManager.notify(notificationId, notificationBuilder.build())
                            }
                            else {
                                message = "Please make sure all boxes have a value."
                            }
                        }
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    })
                {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = stringResource(R.string.saveRemindButtonDescription),
                        Modifier.padding(2.dp)
                    )
                    Text(text = stringResource(R.string.saveRemindButton))
                }

                Row(modifier = Modifier.height(IntrinsicSize.Min).padding(top = 4.dp)) {
                    Row{
                        Button (
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                            elevation = null,
                            modifier = Modifier.padding(top = 8.dp),
                            onClick =
                            {
                                val intent = Intent(context, MembersActivity::class.java)
                                context.startActivity(intent)
                            }
                        ){
                            Icon(
                                imageVector = Icons.Outlined.People,
                                contentDescription = stringResource(R.string.membersButtonDescription),
                                Modifier.padding(2.dp)
                            )
                            Text(text = stringResource(R.string.membersButton))
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
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                            elevation = null,
                            modifier = Modifier.padding(top = 8.dp),
                            onClick =
                            {
                                val intent = Intent(context, PastBillsActivity::class.java)
                                context.startActivity(intent)
                            }
                        ){
                            Icon(
                                imageVector = Icons.Outlined.Timelapse,
                                contentDescription = stringResource(R.string.pastBillsButtonDescription),
                                Modifier.padding(2.dp)
                            )
                            Text(text = stringResource(R.string.pastBillsButton))
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