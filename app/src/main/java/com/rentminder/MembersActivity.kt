package com.rentminder

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            firebaseUser?.let {
                val member = Members(it.uid, 1, it.displayName)
                viewModel.member = member
                viewModel.listenToBills()
                viewModel.listenToMembers()
            }
            val bills by viewModel.bills.observeAsState(initial = emptyList())
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
                                if (bill.month == monthName){
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
        var selectedBill by remember(selectedMember) { mutableStateOf(Bill()) }
        var inPaidRent by remember { mutableStateOf(if (selectedBill.rentPaid) "Paid" else "Not Paid") }
        var inPaidElectric by remember { mutableStateOf(if (selectedBill.electricPaid) "Paid" else "Not Paid") }
        var inPaidWater by remember { mutableStateOf(if (selectedBill.waterPaid) "Paid" else "Not Paid") }
        var inPaidWifi by remember { mutableStateOf(if (selectedBill.wifiPaid) "Paid" else "Not Paid") }
        var inPaidOther by remember { mutableStateOf(if (selectedBill.otherPaid) "Paid" else "Not Paid") }


        val selectedMember = remember { mutableStateOf<Members?>(null) }

        Column(
            modifier = Modifier.padding(
                horizontal = 15.dp
            )
        ) {
            MembersMenu(members = members) { member ->
                selectedMember.value = member
                selectedBill = bills.find { bill -> bill.memberId == member.uid } ?: Bill()
            }

            LaunchedEffect(selectedBill.rentPaid) {
                inPaidRent = if (selectedBill.rentPaid) "Paid" else "Not Paid"
                inPaidElectric = if (selectedBill.electricPaid) "Paid" else "Not Paid"
                inPaidWater = if (selectedBill.waterPaid) "Paid" else "Not Paid"
                inPaidWifi = if (selectedBill.wifiPaid) "Paid" else "Not Paid"
                inPaidOther = if (selectedBill.otherPaid) "Paid" else "Not Paid"
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
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Button(
                        onClick = {
                            selectedBill.rentPaid = !selectedBill.rentPaid
                            viewModel.saveBill(selectedBill)
                            inPaidRent = if (selectedBill.rentPaid) "Paid" else "Not Paid"
                            },
                        shape = CutCornerShape(10),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if (inPaidRent == "Paid") SoftGreen else Color.Red
                        )){
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
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Button(
                        onClick = {
                            selectedBill.electricPaid = !selectedBill.electricPaid
                            viewModel.saveBill(selectedBill)
                            inPaidElectric = if (selectedBill.electricPaid) "Paid" else "Not Paid"
                            },
                        shape = CutCornerShape(10),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if (inPaidElectric == "Paid") SoftGreen else Color.Red
                        )){
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
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Button(
                        onClick = {
                            selectedBill.waterPaid = !selectedBill.waterPaid
                            viewModel.saveBill(selectedBill)
                            inPaidWater = if (selectedBill.waterPaid) "Paid" else "Not Paid"
                        },
                        shape = CutCornerShape(10),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if (inPaidWater == "Paid") SoftGreen else Color.Red
                        )){
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
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Button(
                        onClick = {
                            selectedBill.wifiPaid = !selectedBill.wifiPaid
                            viewModel.saveBill(selectedBill)
                            inPaidWifi = if (selectedBill.wifiPaid) "Paid" else "Not Paid"
                        },
                        shape = CutCornerShape(10),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if (inPaidWifi == "Paid") SoftGreen else Color.Red
                        )){
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
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Button(
                        onClick = {
                            selectedBill.otherPaid = !selectedBill.otherPaid
                            viewModel.saveBill(selectedBill)
                            inPaidOther = if (selectedBill.otherPaid) "Paid" else "Not Paid"
                        },
                        shape = CutCornerShape(10),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if (inPaidOther == "Paid") SoftGreen else Color.Red
                        )){
                        Text(text = inPaidOther)
                    }
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