package com.rentminder

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
fun IconText(
    iconResId: Int,
    contentDescription: String,
    text: String
){
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = contentDescription,
            modifier = Modifier.size(30.dp)
        )
        Text(
            text = text,
            fontSize = 26.sp
        )
    }
}

@Composable
fun RentIconText() {
    IconText(
        iconResId = R.drawable.outline_house_24,
        contentDescription = "Rent Icon",
        text = "Rent"
    )
}

@Composable
fun ElectricIconText() {
    IconText(
        iconResId = R.drawable.outline_lightbulb_24,
        contentDescription = "Light Bulb Icon",
        text = "Electric/Gas"
    )
}

@Composable
fun WaterIconText() {
    IconText(
        iconResId = R.drawable.outline_water_drop_24,
        contentDescription = "Water Icon",
        text = "Water/Sewer"
    )
}

@Composable
fun WiFiIconText() {
    IconText(
        iconResId = R.drawable.outline_wifi_24,
        contentDescription = "WiFi Icon",
        text = "Wi-Fi"
    )
}

@Composable
fun OtherIconText() {
    IconText(
        iconResId = R.drawable.baseline_add_shopping_cart_24,
        contentDescription = "Other Icon",
        text = "Other"
    )
}

@Composable
fun TotalText() {
    Text(
        text = "Total:",
        fontSize = (20.sp),
        modifier = Modifier
            .padding(
                top = 10.dp,
                start = 15.dp
            )
    )
}

@Composable
fun TotalPerPersonText() {
    Text(
        text = "Total Per Person:",
        fontSize = (20.sp),
        modifier = Modifier
            .padding(
                top = 5.dp,
                start = 15.dp
            )
    )
}