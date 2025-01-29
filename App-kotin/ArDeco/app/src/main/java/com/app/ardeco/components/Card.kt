package com.app.ardeco.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun Card(navController: NavController, id:Int, title: String, thumbUrl: String, file:String, createdAt: String) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF362D59))
            .clickable {
                val encodedFile = URLEncoder.encode(file, "UTF-8")

                navController.navigate("decoration/${encodedFile}/${id}")
            }
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Thumbnail image
            AsyncImage(
                model = thumbUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)) // Rounded corners for the image
                    .background(Color(0xFF2C254A))
            )

            // Title
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 8.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@Composable
fun Card1(navController: NavController, id: Int, modelImage: String, file: String, createdAt: String) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF362D59))
            .clickable {
                val encodedUrl = URLEncoder.encode(modelImage, StandardCharsets.UTF_8.toString())
                navController.navigate("imageViewer/$encodedUrl")
            }
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Thumbnail image
            AsyncImage(
                model = modelImage,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF2C254A))
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageViewer(navController: NavController, imageUrl: String?) {
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
                        text = "Catalog Furniture",
                        style = MaterialTheme.typography.titleLarge, color = Color.White)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription="")
                    }
                },
            )
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                if (imageUrl.isNullOrEmpty()) {
                    Text(
                        text = "Image URL not provided",
                        modifier = Modifier.fillMaxSize(),
                        color = Color.Red,
                        textAlign = TextAlign.Center
                    )
                } else {
                    val painter = rememberImagePainter(
                        data = imageUrl,
                        builder = {
                            crossfade(true)
                            placeholder(android.R.drawable.ic_menu_report_image)
                            error(android.R.drawable.stat_notify_error)
                        }
                    )

                    Image(
                        painter = painter,
                        contentDescription = "Image from URL",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    )
}
