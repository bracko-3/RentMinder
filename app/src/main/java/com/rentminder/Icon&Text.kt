package com.rentminder

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.*

@Composable
fun RentIconText() {
    Row(verticalAlignment = Alignment.CenterVertically){
        Icon(
            imageVector = Icons.Outlined.Home,
            contentDescription = "Rent Icon",
            modifier = Modifier
                .padding(
                    end = 3.dp
                )
                .size(30.dp)
        )
        Text(
            text = "Rent",
            fontSize = (26.sp),
            fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ElectricIconText() {
    Row(verticalAlignment = Alignment.CenterVertically){
        Icon(
            imageVector = androidx.compose.material.material-symbols-outlined {
                font-variation-settings:
                'FILL' 0,
                'wght' 400,
                'GRAD' 0,
                'opsz' 48
            },
            contentDescription = "Electric Icon",
            modifier = Modifier
                .padding(
                    end = 3.dp
                )
                .size(30.dp)
        )
        Text(
            text = "Electric/Gas",
            fontSize = (26.sp),
            fontWeight = FontWeight.Bold)
    }
}