package com.app.ardeco.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.app.ardeco.components.Card
import com.app.ardeco.utils.ModelViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListingScreen(navController: NavController, page: String?) {
    val viewModel: ModelViewModel = viewModel()

    // Collect modelData from the ViewModel
    val modelData by viewModel.modelData.collectAsState(initial = emptyList())

    val isLoading by viewModel.isLoading.collectAsState(initial = true)

    // Fetch data for the specific page key
    LaunchedEffect(page) {
        if (page != null) {
            viewModel.fetchModelData("https://tgali.com/ardeco/ardeco/api/v1/models/${page}", page)
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
                title = {
                    Text(
                        text = if (page == "1") "Appliances" else "Furniture",
                        style = MaterialTheme.typography.titleLarge, color = Color.White)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription="")
                    }
                },
                actions = {
//                    IconButton(onClick = { }) {
//                        Icon(imageVector = Icons.Filled.ShoppingCart, contentDescription="")
//                    }
                }
            )
        },
        content = { paddingValues ->
            when {
                isLoading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(paddingValues),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                modelData.isEmpty() -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Data not found",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(paddingValues)
                        )
                    }
                }

                else -> {
                    // Show the grid of items
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(paddingValues),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(modelData.size) { index ->
                            val modelItem = modelData[index]
                            Card(
                                navController = navController,
                                id = modelItem.id,
                                title = modelItem.title,
                                thumbUrl = modelItem.thumb,
                                file = modelItem.file,
                                createdAt = modelItem.created_at
                            )
                        }
                    }
                }
            }
        },
    )
}

