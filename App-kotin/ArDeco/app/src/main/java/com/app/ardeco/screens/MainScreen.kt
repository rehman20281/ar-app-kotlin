package com.app.ardeco.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.ardeco.components.Appbar


@Composable
fun MainScreen(navController: NavController) {
    Scaffold(
        topBar = { Appbar(2) },
        contentColor = Color(0xFFFFFFFF),
        containerColor = Color(0xFF2C254A),
        modifier = Modifier.background(color = Color(0xFF2C254A)),
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFF2C254A))
        ) {
            OutlinedButton(
                onClick = {
                    navController.navigate("listing/1")
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .height(100.dp)
            ) {
                Text(
                    text = "Appliances", fontSize = 25.sp
                )
            }

            OutlinedButton(
                onClick = {
                    navController.navigate("listing/2")
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .height(100.dp)
            ) {
                Text(
                    text = "Furniture", fontSize = 25.sp
                )
            }
        }
    }
}


