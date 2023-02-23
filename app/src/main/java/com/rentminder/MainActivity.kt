package com.rentminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentminder.ui.theme.RentMinderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RentMinderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainMenu()
                }
            }
        }
    }
}

@Composable
fun MainMenu() {
    Column() {
        TopToolBar()
        //Second column to center the body of the page
        Column(modifier = Modifier.padding(horizontal = 20.dp).padding(vertical = 15.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = stringResource(id = R.string.current_month),
                fontSize = 24.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun TopToolBar() {
    Column() {
        TopAppBar(
            title = {
                Text(text = "Rentminder")
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RentMinderTheme {
        MainMenu()
    }
}