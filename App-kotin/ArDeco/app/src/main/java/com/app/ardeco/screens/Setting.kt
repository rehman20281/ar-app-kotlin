package com.app.ardeco.screens

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.app.ardeco.network.SignUpViewModel
import com.app.ardeco.utils.ModelViewModel
import com.app.ardeco.utils.UserModel
import com.app.ardeco.utils.UserViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Setting(navController: NavController) {
    val viewModel: UserViewModel = viewModel()
    val user by viewModel.modelData.collectAsState()

    val context = LocalContext.current

    val sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    val id = sharedPreferences.getInt("userId", 0).toString()

    LaunchedEffect(Unit) {
        viewModel.getUserData("https://tgali.com/ardeco/ardeco/api/v1/user/$id")
    }

    // State variables for user fields
    var name by remember { mutableStateOf("") }
    var photo by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var mobileError by remember { mutableStateOf(false) }

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

    // Update fields when user data changes
    LaunchedEffect(user) {
        user?.let {
            name = it.name ?: ""
            email = it.email ?: ""
            mobile = it.mobile ?: ""
            dateOfBirth = it.dob ?: ""
            photo = it.photo ?: ""
        }
    }

    Scaffold(
        contentColor = Color(0xFF2C254A),
        containerColor = Color(0xFF2C254A),
        topBar = {
        TopAppBar(
            colors = TopAppBarColors(
                containerColor = Color(0xFF2C254A),
                scrolledContainerColor = Color(0xFF2C254A),
                navigationIconContentColor = Color(0xFFFFFFFF),
                titleContentColor = Color(0xFFFFFFFF),
                actionIconContentColor = Color(0xFFFFFFFF),
            ),
            title = { Text("Setting") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFFFFFFFF)
                    )
                }
            },
            actions = {
                var loading by remember { mutableStateOf(false) }
                IconButton(
                    onClick = {
                        nameError = name.isEmpty()
                        emailError = !Patterns.EMAIL_ADDRESS.matcher(email).matches()
                        mobileError = mobile.length < 11 || !mobile.all { char -> char.isDigit() }

                        if (!nameError && !emailError && !mobileError) {
                            loading = true // Show loading indicator

                            val updatedUser = UserModel(
                                id = id.toInt(),
                                name = name,
                                email = email,
                                mobile = mobile,
                                dob = dateOfBirth,
                                photo = photo
                            )

                            viewModel.updateUser(
                                "https://tgali.com/ardeco/ardeco/api/v1/user/update/${id}",
                                updatedUser,
                                onSuccess = {
                                    loading = false // Hide loading indicator
                                    // Update UI state
                                    name = updatedUser.name
                                    email = updatedUser.email
                                    mobile = updatedUser.mobile
                                    dateOfBirth = updatedUser.dob
                                    photo = updatedUser.photo

                                    Toast.makeText(
                                        context,
                                        "User updated successfully!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                onError = { error ->
                                    loading = false // Hide loading indicator
                                    Toast.makeText(
                                        context,
                                        "Failed to update user: ${error.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                        }
                    },
                    enabled = !loading // Disable button when loading
                ) {
                    if (loading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Save,
                            contentDescription = "Save",
                            tint = Color.White
                        )
                    }
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
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
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
                Text(
                    text = "Enter your full name",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {},
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                isError = emailError,
                singleLine = true,
                enabled = false
            )

            Spacer(modifier = Modifier.height(8.dp))

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
                Text(
                    text = "Enter a valid 11-digit mobile number",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = dateOfBirth,
                onValueChange = {},
                label = { Text("Date of Birth") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { datePickerDialog.show() }) {
                        Icon(imageVector = Icons.Default.DateRange, contentDescription = "Pick Date")
                    }
                }
            )
        }
    }
}
