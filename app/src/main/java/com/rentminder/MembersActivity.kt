package com.rentminder

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentminder.ui.theme.RentMinderTheme

class MembersActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RentMinderTheme {
                Scaffold(topBar = {
                    TopAppBar(
                        title = {
                            Text(text = "Top App Bar")
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                startActivity(
                                    Intent(
                                        this@MembersActivity,
                                        MainActivity::class.java
                                    )
                                )
                            }) {
                                Icon(Icons.Filled.ArrowBack, "backIcon")
                            }
                        },
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = Color.White,
                        elevation = 10.dp
                    )
                }) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues = innerPadding)
                    ) {
                        Text(
                            text = "Content of the page", fontSize = 30.sp, color = Color.White
                        )

                    }
                }
            }
        }
    }
}

@Composable
fun MembersMenu() {

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    RentMinderTheme {}
}