package com.rentMinder

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import com.rentminder.R

@Composable
fun RentIconText() {
    Row(verticalAlignment = Alignment.CenterVertically){
        Icon(painterResource(
            id = R.drawable.outline_house_24
        ),
            contentDescription = "Rent Icon",
            modifier = Modifier
                .size(30.dp)
        )
        Text(
            text = "Rent",
            fontSize = (26.sp)
        )
    }
}

@Composable
fun ElectricIconText() {
    Row(verticalAlignment = Alignment.CenterVertically){
        Icon(painterResource(
            id = R.drawable.outline_lightbulb_24
        ),
            contentDescription = "Light Bulb Icon",
            modifier = Modifier
                .size(30.dp)
        )
        Text(
            text = "Electric/Gas",
            fontSize = (26.sp)
        )
    }
}

@Composable
fun WaterIconText() {
    Row(verticalAlignment = Alignment.CenterVertically){
        Icon(painterResource(
            id = R.drawable.outline_water_drop_24
        ),
            contentDescription = "Water Icon",
            modifier = Modifier
                .size(30.dp)
        )
        Text(
            text = "Water/Sewer",
            fontSize = (26.sp),
        )
    }
}

@Composable
fun WiFiIconText() {
    Row(verticalAlignment = Alignment.CenterVertically){
        Icon(painterResource(
            id = R.drawable.outline_wifi_24
        ),
            contentDescription = "WiFi Icon",
            modifier = Modifier
                .size(30.dp)
        )
        Text(
            text = "Wi-Fi",
            fontSize = (26.sp),
        )
    }
}

@Composable
fun OtherIconText() {
    Row(verticalAlignment = Alignment.CenterVertically){
        Icon(painterResource(
            id = R.drawable.baseline_add_shopping_cart_24
        ),
            contentDescription = "Other Icon",
            modifier = Modifier
                .size(30.dp)
        )
        Text(
            text = "Other",
            fontSize = (26.sp),
        )
    }
}