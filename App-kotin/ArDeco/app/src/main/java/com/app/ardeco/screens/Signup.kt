package com.app.ardeco.screens

import android.app.DatePickerDialog
import android.util.Patterns
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.ardeco.network.SignUpViewModel
import java.util.Calendar

@ExperimentalMaterial3Api
@Composable
fun SignUpScreen(navController: NavController, viewModel: SignUpViewModel = SignUpViewModel()) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var passwordVisible1 by remember { mutableStateOf(false) }
    var passwordVisible2 by remember { mutableStateOf(false) }

    var nameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }
    var mobileError by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            dateOfBirth = "$dayOfMonth-${month + 1}-$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Scaffold(contentColor = Color(0xFFFFFFFF), containerColor = Color(0xFF2C254A), topBar = {
        TopAppBar(
            colors = TopAppBarColors(
                containerColor = Color(0xFF2C254A),
                scrolledContainerColor = Color(0xFF2C254A),
                navigationIconContentColor = Color(0xFF2C254A),
                titleContentColor = Color(0xFFFFFFFF),
                actionIconContentColor = Color(0xFFFFFFFF)
            ),
            title = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text("Create an Account")
                }
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFFFFFFFF)
                    )
                }
            }
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .background(Color(0xFF2C254A))
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Name Input
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    nameError = name.isEmpty()
                },
                isError = nameError,
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
            if (nameError) {
                Box(modifier = Modifier.align(alignment = Alignment.Start)) {
                    Text(
                        text = "Enter your full name",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Email Input
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = !Patterns.EMAIL_ADDRESS.matcher(email).matches()
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
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Mobile Number Input
            OutlinedTextField(
                value = mobile,
                onValueChange = {
                    mobile = it
                    mobileError = mobile.length < 11 || !mobile.all { char -> char.isDigit() }
                },
                isError = mobileError,
                label = { Text("Mobile Number") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                )
            )
            if (mobileError) {
                Box(modifier = Modifier.align(alignment = Alignment.Start)) {
                    Text(
                        text = "Enter a valid 11-digit mobile number",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Password Input
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = password.length < 6
                },
                isError = passwordError,
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = if (passwordVisible1) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible1 = !passwordVisible1 }) {
                        Icon(
                            imageVector = if (passwordVisible1) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null
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

            // Confirm Password Input
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    confirmPasswordError = confirmPassword != password
                },
                isError = confirmPasswordError,
                label = { Text("Confirm Password") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = if (passwordVisible2) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible2 = !passwordVisible2 }) {
                        Icon(
                            imageVector = if (passwordVisible2) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
                )
            )
            if (confirmPasswordError) {
                Box(modifier = Modifier.align(alignment = Alignment.Start)) {
                    Text(
                        text = "Passwords do not match",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Date of Birth Picker
            OutlinedTextField(
                value = dateOfBirth,
                onValueChange = {},
                label = { Text("Date of Birth") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = false,
                trailingIcon = {
                    IconButton(onClick = { datePickerDialog.show() }) {
                        Icon(imageVector = Icons.Default.DateRange, contentDescription = "Pick Date")
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Sign-Up Button
            Button(
                onClick = {
                    nameError = name.isEmpty()
                    emailError = !Patterns.EMAIL_ADDRESS.matcher(email).matches()
                    mobileError = mobile.length < 11 || !mobile.all { char -> char.isDigit() }
                    passwordError = password.length < 6
                    confirmPasswordError = confirmPassword != password

                    if (!nameError && !emailError && !mobileError && !passwordError && !confirmPasswordError) {
                        isLoading = true
                        viewModel.signUp(
                            name = name,
                            email = email,
                            mobile = mobile,
                            password = password,
                            dateOfBirth = dateOfBirth
                        ) { success, response ->
                            isLoading = false
                            if (success) {
                                Toast.makeText(context, response, Toast.LENGTH_LONG).show()

                                name = ""
                                email = ""
                                mobile = ""
                                password = ""
                                confirmPassword = ""
                                dateOfBirth = ""
                            } else {
                                Toast.makeText(context, "Error: $response", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    Text(text = "Loading...", fontSize = 16.sp)
                } else {
                    Text(text = "Sign Up", fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = {
                navController.navigate("login")
            }) {
                Text("Already have an account? Log In")
            }

        }
    }
}
