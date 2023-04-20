package com.rentminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.rentminder.dto.Bill
import com.rentminder.dto.Members
import com.rentminder.ui.theme.RentMinderTheme
import com.rentminder.ui.theme.SoftGreen
import org.koin.androidx.viewmodel.ext.android.viewModel

class MembersActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModel()
    private var selectedBill by mutableStateOf(Bill())
    private var selectedMember by mutableStateOf(Members())
    private var firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    var memberText by mutableStateOf("Select member")
    val cal: Calendar = Calendar.getInstance()
    val monthDate = SimpleDateFormat("MMMM")
    val monthName: String = monthDate.format(cal.time).replaceFirstChar { it.uppercase() }
    private var currentMonth: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentMonth = monthName

        setContent {
            firebaseUser?.let {
                val member = Members(it.uid, 1, it.displayName)
                viewModel.member = member
                viewModel.listenToAllBills()
                viewModel.listenToMembers()
            }
            val bills by viewModel.allBills.observeAsState(initial = emptyList())
            val members by viewModel.members.observeAsState(initial = emptyList())

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
                                        this@MembersActivity, MainActivity::class.java
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
                            bills.forEach { bill ->
                                if (bill.month == currentMonth){
                                    selectedBill = bill
                                }
                            }
                            membersPayments(members, bills)
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        currentMonth = monthName
    }

    @Composable
    fun MembersMenu(members: List<Members>, onMemberSelected: (Members) -> Unit) {
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
                Text(text = memberText, fontSize = 30.sp, modifier = Modifier.padding(end = 8.dp))
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    members.forEach { member ->
                        DropdownMenuItem(onClick = {
                            expanded = false
                            memberText = member.memberName.toString()
                            selectedMember = member
                            onMemberSelected(member) // invoke callback with selected member
                        }) {
                            member.memberName?.let { Text(text = it) }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun membersPayments(members: List<Members>, bills: List<Bill>) {
        var inPaidRent by remember { mutableStateOf(if (selectedBill.rentPaid) "Paid" else "Not Paid") }
        var inPaidElectric by remember { mutableStateOf(if (selectedBill.electricPaid) "Paid" else "Not Paid") }
        var inPaidWater by remember { mutableStateOf(if (selectedBill.waterPaid) "Paid" else "Not Paid") }
        var inPaidWifi by remember { mutableStateOf(if (selectedBill.wifiPaid) "Paid" else "Not Paid") }
        var inPaidOther by remember { mutableStateOf(if (selectedBill.otherPaid) "Paid" else "Not Paid") }
        val context = LocalContext.current

        selectedMember?.let { member ->
            val selectedBills =
                bills.filter { it.memberId == member.uid && it.month == currentMonth }
            val selectedBill = selectedBills.firstOrNull() ?: Bill()

            LaunchedEffect(selectedBills) {
                inPaidRent = if (selectedBill.rentPaid) "Paid" else "Not Paid"
                inPaidElectric = if (selectedBill.electricPaid) "Paid" else "Not Paid"
                inPaidWater = if (selectedBill.waterPaid) "Paid" else "Not Paid"
                inPaidWifi = if (selectedBill.wifiPaid) "Paid" else "Not Paid"
                inPaidOther = if (selectedBill.otherPaid) "Paid" else "Not Paid"
            }

            Column(
                modifier = Modifier.padding(
                    horizontal = 15.dp
                )
            ) {
                MembersMenu(members = members) { member ->
                    selectedMember = member
                }

                //Rent  text and button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Rent", fontSize = 28.sp, fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                selectedBill.rentPaid = !selectedBill.rentPaid
                                viewModel.member = selectedMember
                                viewModel.saveBill(selectedBill)
                                inPaidRent = if (selectedBill.rentPaid) "Paid" else "Not Paid"
                            },
                            shape = CutCornerShape(10),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = if (inPaidRent == "Paid") SoftGreen else Color.Red
                            )
                        ) {
                            Text(text = inPaidRent)
                        }
                    }
                }

                //Electric  text and button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "Electric", fontSize = 28.sp, fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                selectedBill.electricPaid = !selectedBill.electricPaid
                                viewModel.member = selectedMember
                                viewModel.saveBill(selectedBill)
                                inPaidElectric =
                                    if (selectedBill.electricPaid) "Paid" else "Not Paid"
                            },
                            shape = CutCornerShape(10),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = if (inPaidElectric == "Paid") SoftGreen else Color.Red
                            )
                        ) {
                            Text(text = inPaidElectric)
                        }
                    }
                }

                //Water  text and button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "Water", fontSize = 28.sp, fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                selectedBill.waterPaid = !selectedBill.waterPaid
                                viewModel.member = selectedMember
                                viewModel.saveBill(selectedBill)
                                inPaidWater = if (selectedBill.waterPaid) "Paid" else "Not Paid"
                            },
                            shape = CutCornerShape(10),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = if (inPaidWater == "Paid") SoftGreen else Color.Red
                            )
                        ) {
                            Text(text = inPaidWater)
                        }
                    }
                }

                //Wifi  text and button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "Wifi", fontSize = 28.sp, fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                selectedBill.wifiPaid = !selectedBill.wifiPaid
                                viewModel.member = selectedMember
                                viewModel.saveBill(selectedBill)
                                inPaidWifi = if (selectedBill.wifiPaid) "Paid" else "Not Paid"
                            },
                            shape = CutCornerShape(10),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = if (inPaidWifi == "Paid") SoftGreen else Color.Red
                            )
                        ) {
                            Text(text = inPaidWifi)
                        }
                    }
                }

                //Other  text and button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "Other", fontSize = 28.sp, fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                selectedBill.otherPaid = !selectedBill.otherPaid
                                viewModel.member = selectedMember
                                viewModel.saveBill(selectedBill)
                                inPaidOther = if (selectedBill.otherPaid) "Paid" else "Not Paid"
                            },
                            shape = CutCornerShape(10),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = if (inPaidOther == "Paid") SoftGreen else Color.Red
                            )
                        ) {
                            Text(text = inPaidOther)
                        }
                    }
                }
                Button(
                    onClick = {
                        val notificationId = 1 // A unique ID for the notification
                        val channelId = "my_channel_id" // A unique ID for the notification channel
                        val notificationBuilder = NotificationCompat.Builder(context, channelId)
                            .setSmallIcon(R.drawable.outline_notifications_active_24)
                            .setContentTitle("RentMinder")
                            .setContentText("You have pending bills!")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            val name = "RentMinder"
                            val descriptionText = "You have pending bills!"
                            val importance = NotificationManager.IMPORTANCE_DEFAULT
                            val channel = NotificationChannel(channelId, name, importance).apply {
                                description = descriptionText
                            }
                            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                            notificationManager.createNotificationChannel(channel)
                        }

                        val notificationManager = NotificationManagerCompat.from(context)
                        notificationManager.notify(notificationId, notificationBuilder.build())
                    },
                    shape = CutCornerShape(10),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = SoftGreen
                    )
                ) {
                    Text(text = "Remind")
                }
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview2() {
        RentMinderTheme {}
    }
}