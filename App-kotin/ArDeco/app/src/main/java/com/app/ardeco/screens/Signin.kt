package com.app.ardeco.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.ardeco.network.SignInViewModel


@ExperimentalMaterial3Api
@Composable
fun SigninScreen(navController: NavController,  viewModel: SignInViewModel = SignInViewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible1 by remember { mutableStateOf(false) }

    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val context = LocalContext.current


    Scaffold(
        contentColor = Color(0xFFFFFFFF),
        containerColor = Color(0xFF2C254A),
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            OutlinedTextField(value = email,
                onValueChange = {
                    email = it
                    emailError = !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
                },
                isError = emailError,
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
                )
            )

            if (emailError) {
                Box(modifier = Modifier.align(alignment = Alignment.Start)) {
                    Text(
                        text = "Enter a valid email address",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = password,
                onValueChange = {
                    password = it
                    passwordError = email.isEmpty()
                },
                isError = passwordError,
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = if (passwordVisible1) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisible1 = !passwordVisible1
                    }) { // Correct toggle logic
                        Icon(
                            imageVector = if (passwordVisible1) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (passwordVisible1) "Hide Password" else "Show Password"
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password, imeAction = ImeAction.Next
                )
            )
            if (passwordError) {
                Box(modifier = Modifier.align(alignment = Alignment.Start)) {
                    Text(
                        text = "Password must be at least 6 characters",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    emailError = !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
                    passwordError = password.length < 6
                    if (!emailError && !passwordError) {
                        isLoading = true
                        viewModel.signIn(
                            email=email,
                            password=password
                        ) { success, response, userId, userName ->
                            isLoading = false
                            if (success) {
                                val sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

                                val editor = sharedPreferences.edit()
                                editor.putBoolean("isLoggedIn", success)
                                editor.putInt("userId", userId)
                                editor.putString("userName", userName)

                                editor.apply()

                                Toast.makeText(context, response, Toast.LENGTH_LONG).show()
                                email = ""
                                password = ""

                                navController.navigate("dashboard")
                            } else {
                                Toast.makeText(context, "${response}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                },
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isLoading) {
                    Text(text = "Authenticating...", fontSize = 16.sp)
                } else {
                    Text(text = "Login", fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = {navController.navigate("signup") }) {
                Text("Create an account")
            }
        }
    }
}
