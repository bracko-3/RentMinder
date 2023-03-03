package com.rentminder

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

@Composable
fun setupRentTextProperties() {
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
fun setupElectricTextProperties() {
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
fun setupWaterTextProperties() {
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
fun setupWifiTextProperties() {
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
fun setupMiscellaneousTextProperties() {
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