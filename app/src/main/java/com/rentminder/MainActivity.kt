package com.rentminder

import android.annotation.SuppressLint
import android.app.NotificationChannel
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
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
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
import androidx.core.app.NotificationCompat
import com.rentminder.dto.Bill
import com.rentminder.ui.theme.RentMinderTheme
import com.rentminder.ui.theme.SoftGreen
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.compose.ui.text.style.TextAlign
import androidx.core.app.NotificationManagerCompat
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import com.rentminder.dto.Members

//Getting current month for Main Menu
val cal: Calendar = Calendar.getInstance()
val monthDate = SimpleDateFormat("MMMM")
val monthName: String = monthDate.format(cal.time).replaceFirstChar { it.uppercase() }
private var selectedBill by mutableStateOf(Bill())
private var isLoggedIn by mutableStateOf(false)

class MainActivity : ComponentActivity() {
    private var firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser

    private val viewModel: MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("my_prefs", MODE_PRIVATE)
        val uid = prefs.getString("uid", null)
        val displayName = prefs.getString("displayName", null)

        if (uid != null && displayName != null) {
            firebaseUser = FirebaseAuth.getInstance().currentUser
            val member = Members(uid, 1, displayName)
            viewModel.member = member
            isLoggedIn = firebaseUser != null
            viewModel.listenToBills()
            viewModel.listenToMembers()
        }

        setContent {
            val bills by viewModel.bills.observeAsState(initial = emptyList())
            val members by viewModel.members.observeAsState(initial = emptyList())
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
                            if (isLoggedIn) {
                                MenuItem(
                                    id = "sysLogout",
                                    title = "Logout",
                                    contentDescription = "Logout",
                                    icon = Icons.Outlined.Logout
                                )
                            } else {
                                MenuItem(
                                    id = "sysLogin",
                                    title = "Login/Sign-Up",
                                    contentDescription = "System Login",
                                    icon = Icons.Outlined.Login
                                )
                            }
                        ),
                            onItemClick = {
                                when(it.id) {
                                    "members" -> startActivity(Intent(this@MainActivity, MembersActivity::class.java))
                                    "pastBills" -> startActivity(Intent(this@MainActivity, PastBillsActivity::class.java))
                                    "sysLogin" -> {
                                        signIn()
                                    }
                                    "sysLogout" -> {
                                        FirebaseAuth.getInstance().signOut()
                                        isLoggedIn = false
                                    }
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
                            MainMenu(members)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun MainMenu(members: List<Members>) {
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
            EditBillAmounts(members)
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
    @SuppressLint("SuspiciousIndentation")
    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun EditBillAmounts(members: List<Members>) {
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
        var inDividedBill by remember(selectedBill.month) { mutableStateOf(selectedBill.totalPerson.toString()) }
        val isFormFilled = remember { mutableStateOf(false) }
        isFormFilled.value = inRentBill.isNotEmpty() && inElectricBill.isNotEmpty() && inWaterBill.isNotEmpty() && inWifiBill.isNotEmpty() && inOtherBill.isNotEmpty()

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
                if (inDividedBill == "") {
                    TotalPerPersonText(0.0)
                }
                else {
                    TotalPerPersonText(inDividedBill.toDouble())
                }
            }

            Column (horizontalAlignment = CenterHorizontally, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 13.dp)) {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = SoftGreen),
                    onClick = {
                        var message = ""

                        if (isFormFilled.value) {
                            inTotalBill = (inRentBill.toInt() + inElectricBill.toInt() + inWaterBill.toInt() + inWifiBill.toInt() + inOtherBill.toInt()).toString()
                            inDividedBill = (inTotalBill.toDouble()/members.size).toString()
                            selectedBill.apply {
                                month = monthName
                                memberId = firebaseUser?.let {
                                    it.uid
                                } ?: ""
                                rentBill = inRentBill.toInt()
                                energyBill = inElectricBill.toInt()
                                waterBill = inWaterBill.toInt()
                                wifiBill = inWifiBill.toInt()
                                otherBill = inOtherBill.toInt()
                                total = inTotalBill.toDouble()
                                totalPerson = inDividedBill.toDouble()
                            }
                            viewModel.saveBill(selectedBill)
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
            //MainMenu()
        }
    }

    private fun signIn() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setIsSmartLockEnabled(false)
            .setAvailableProviders(providers)
            .build()

        signInLauncher.launch(signInIntent)
    }

    private val signInLauncher = registerForActivityResult (
            FirebaseAuthUIActivityResultContract()
            ) {
                res -> this.signInResult(res)
            }

    private fun signInResult(result: FirebaseAuthUIAuthenticationResult){
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            firebaseUser = FirebaseAuth.getInstance().currentUser
            firebaseUser?.let{
                val member = Members(it.uid, 1, it.displayName)
                viewModel.member = member
                viewModel.saveMember()
                viewModel.listenToBills()
                isLoggedIn = true

                // Store user credentials in SharedPreferences
                val prefs = getSharedPreferences("my_prefs", MODE_PRIVATE)
                with(prefs.edit()) {
                    putString("uid", it.uid)
                    putString("displayName", it.displayName)
                    apply()
                }
            }
        }
        else {
            Log.e("MainActivity.kt", "Error logging in " + response?.error?.errorCode)
        }
    }
}