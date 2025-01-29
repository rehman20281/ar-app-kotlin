package com.app.ardeco.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.ardeco.R

@Composable
fun Appbar(number: Int) {

    when(number) {
        1 ->     Box(
            modifier = Modifier
                .background(color = Color(0xFF2C254A))
                .padding(vertical = 10.dp, horizontal = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    stringResource(R.string.app_title),
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(horizontal = 20.dp),
                    fontSize = 20.sp,
                )
//            MinimalDropdownMenu()
            }
        }
        2 -> Box(
            modifier = Modifier
                .background(Color(0xFF2C254A)) // Background color similar to the image
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Greeting and Search Icon
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Hi, Welcome Back",
                            fontSize = 20.sp,
                            color = Color(0xFFFFD6E4), // Light pink
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Decor your space with Augmented Reality",
                            fontSize = 14.sp,
                            color = Color(0xFFD3CDE8) // Subtle light purple
                        )
                    }
                }
            }
            
        }
    }

}
