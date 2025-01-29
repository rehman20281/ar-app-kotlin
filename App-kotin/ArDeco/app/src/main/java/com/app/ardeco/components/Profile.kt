package com.app.ardeco.components

import android.content.Context
import android.view.MenuItem
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    val _name = sharedPreferences.getString("userName", "")
    val _userId = sharedPreferences.getInt("userId", 0).toString()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2D2542))
            .padding(16.dp),
    ) {
        item {
            // Profile Header
            ProfileHeader(
                name= _name.toString(),
                userId = _userId
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Menu Items
            MenuItem(icon = Icons.Default.Key, label = "Privacy Policy", onClick = {
                navController.navigate("privacy-policy")
            })
            MenuItem(icon = Icons.Default.Bookmark, label = "Saved Designs", onClick = {
                navController.navigate("saved-design")
            })
//            MenuItem(icon = Icons.Default.Notifications, label = "Notification")
            MenuItem(icon = Icons.Default.Settings, label = "Settings", onClick = {
                navController.navigate("setting")
            })

            MenuItem(icon = Icons.AutoMirrored.Filled.Help, label = "Help",
                onClick = {
                    navController.navigate("help")
                })
            MenuItem(
                icon = Icons.AutoMirrored.Filled.Logout,
                label = "Logout",
                onClick = {
                    // Perform logout
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("isLoggedIn", false)
                    editor.apply()

                    // Navigate to the login screen
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                })
        }
    }
}

@Composable
fun ProfileHeader(name:String, userId: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(bottom = 50.dp, top = 20.dp)
            .fillMaxWidth()
    ) {
        // Profile Icon
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Profile Icon",
            modifier = Modifier.size(140.dp),
            tint = Color(0xFFFFA8A8)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "$name",
            color = Color(0xFFFFA8A8),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "ID: ${userId}", color = Color(0xFFFFA8A8), fontSize = 14.sp
        )
    }
}

@Composable
fun MenuItem(icon: ImageVector, label: String, onClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 20.dp, horizontal = 20.dp)) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(24.dp),
            tint = Color(0xFFFFA8A8)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label, color = Color(0xFFFFA8A8), fontSize = 16.sp
        )
    }
}
