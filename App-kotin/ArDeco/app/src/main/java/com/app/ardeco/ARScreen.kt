package com.app.ardeco

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Matrix
import android.graphics.Rect
import android.graphics.YuvImage
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.media.Image
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Surface
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.filament.Engine
import com.google.ar.core.Anchor
import com.google.ar.core.Config
import com.google.ar.core.Frame
import com.google.ar.core.Plane
import com.google.ar.core.Pose
import com.google.ar.core.TrackingFailureReason
import io.github.sceneview.ar.ARScene
import io.github.sceneview.ar.arcore.createAnchorOrNull
import io.github.sceneview.ar.arcore.getUpdatedPlanes
import io.github.sceneview.ar.getDescription
import io.github.sceneview.ar.node.AnchorNode
import io.github.sceneview.ar.rememberARCameraNode
import io.github.sceneview.loaders.MaterialLoader
import io.github.sceneview.loaders.ModelLoader
import io.github.sceneview.node.CubeNode
import io.github.sceneview.node.ModelNode
import io.github.sceneview.rememberCollisionSystem
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberMaterialLoader
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNodes
import io.github.sceneview.rememberView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.reflect.InvocationTargetException

@Composable
fun ARScreen(modelUrl: String, id: String) {
    val context = LocalContext.current
    var downloadedFile by remember { mutableStateOf<File?>(null) }
    val coroutineScope = rememberCoroutineScope()

    val engine = rememberEngine()
    val view = rememberView(engine)
    var placedAnchors by remember { mutableStateOf(mutableListOf<Pose>()) }

    var planeRenderer by remember { mutableStateOf(true) }

    var trackingFailureReason by remember {
        mutableStateOf<TrackingFailureReason?>(null)
    }
    var frame by remember { mutableStateOf<Frame?>(null) }

    LaunchedEffect(modelUrl) {
        coroutineScope.launch {
            downloadedFile = downloadModel(context, modelUrl, "$id.glb")
        }
    }

    if (downloadedFile == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFF2C254A)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary, strokeWidth = 4.dp
                    )
                    Text("Loading model...", color = Color(0xFFFFFFFF), fontSize = 30.sp)
                }
            }
        }
    } else {
        // Wait until the file is downloaded before rendering AR
        Box(modifier = Modifier.fillMaxSize()) {
            val engine = rememberEngine()
            val modelLoader = rememberModelLoader(engine)
            val materialLoader = rememberMaterialLoader(engine)
            val cameraNode = rememberARCameraNode(engine)
            val childNodes = rememberNodes()
            val view = rememberView(engine)
            val collisionSystem = rememberCollisionSystem(view)

            ARScene(
                modifier = Modifier.fillMaxSize(),
                childNodes = childNodes,
                engine = engine,
                view = view,
                modelLoader = modelLoader,
                collisionSystem = collisionSystem,
                sessionConfiguration = { session, config ->
                    config.depthMode =
                        when (session.isDepthModeSupported(Config.DepthMode.AUTOMATIC)) {
                            true -> Config.DepthMode.AUTOMATIC
                            else -> Config.DepthMode.DISABLED
                        }
                    config.instantPlacementMode = Config.InstantPlacementMode.LOCAL_Y_UP
                    config.lightEstimationMode = Config.LightEstimationMode.ENVIRONMENTAL_HDR
                },
                cameraNode = cameraNode,
                planeRenderer = planeRenderer,
                onTrackingFailureChanged = {
                    trackingFailureReason = it
                },
                onSessionUpdated = { session, updatedFrame ->
                    frame = updatedFrame

                    if (childNodes.isEmpty()) {
                        updatedFrame.getUpdatedPlanes()
                            .firstOrNull { it.type == Plane.Type.HORIZONTAL_UPWARD_FACING }
                            ?.let { it.createAnchorOrNull(it.centerPose) }?.let { anchor ->
                                placedAnchors.add(anchor.pose)

                                childNodes += createAnchorNode(
                                    engine = engine,
                                    modelLoader = modelLoader,
                                    materialLoader = materialLoader,
                                    anchor = anchor,
                                    modelFile = downloadedFile!!
                                )
                            }
                    }
                },
            )

            Text(modifier = Modifier
                .systemBarsPadding()
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 16.dp, start = 32.dp, end = 32.dp),
                textAlign = TextAlign.Center,
                fontSize = 28.sp,
                color = Color.White,
                text = trackingFailureReason?.let {
                    it.getDescription(LocalContext.current)
                } ?: if (childNodes.isEmpty()) {
                    stringResource(R.string.point_your_phone_down)
                } else {
                    stringResource(R.string.tap_anywhere_to_add_model)
                })

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 20.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = CenterHorizontally
            ) {
                SaveImageButton(frame, modelId = id, placedAnchors = placedAnchors.toString())
            }
        }
    }
}

fun createAnchorNode(
    engine: Engine,
    modelLoader: ModelLoader,
    materialLoader: MaterialLoader,
    anchor: Anchor,
    modelFile: File,
): AnchorNode {
    val anchorNode = AnchorNode(engine = engine, anchor = anchor)

    try {
        if (!modelFile.exists()) {
            return anchorNode
        }

        val modelInstance = modelLoader.createModelInstance(modelFile)
        // Load the model instance
        val modelNode = ModelNode(
            modelInstance = modelInstance, scaleToUnits = 0.5f
        ).apply {
            isEditable = true
            editableScaleRange = 0.2f..0.75f
        }

        // Create a bounding box for the model (for visualization)
        val boundingBoxNode = CubeNode(
            engine,
            size = modelNode.extents,
            center = modelNode.center,
            materialInstance = materialLoader.createColorInstance(Color.White.copy(alpha = 0.5f))
        ).apply {
            isVisible = false // Hide the bounding box until it's needed
        }

        // Add bounding box node to the model node and the model node to the anchor node
        modelNode.addChildNode(boundingBoxNode)
        anchorNode.addChildNode(modelNode)

        // Set up visibility for the bounding box when editing
        listOf(modelNode, anchorNode).forEach {
            it.onEditingChanged = { editingTransforms ->
                boundingBoxNode.isVisible = editingTransforms.isNotEmpty()
            }
        }

    } catch (e: InvocationTargetException) {
        // Handle the InvocationTargetException
        Log.e("ARScreen", "Invocation target exception: ${e.message}", e)
        e.cause?.let { Log.e("ARScreen", "Cause: ${it.message}", it) }
    } catch (e: Exception) {
        // Catch general exceptions and log the error
        Log.e("ARScreen", "General exception: ${e.message}", e)
    }

    return anchorNode
}


suspend fun downloadModel(context: Context, url: String, fileName: String): File? {
    val client = OkHttpClient()
    val request = Request.Builder().url(url).build()

    return withContext(Dispatchers.IO) {
        try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val file = File(context.filesDir, fileName)
                FileOutputStream(file).use { output ->
                    response.body?.byteStream()?.copyTo(output)
                }
                // Log the downloaded file path for debugging
                Log.d("ARScreen", "Model downloaded to: ${file.absolutePath}")
                file
            } else {
                Log.d("ARScreen", "Model not downloaded to")
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}


fun uploadImage(
    modelId: String,
    file: File,
    placedAnchors: String,
    onUploadStart: () -> Unit,
    onUploadFinish: (Boolean) -> Unit,
    context: Context,
) {
    val client = OkHttpClient()
    val requestBody =
        MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("userId", "1")
            .addFormDataPart("modelId", modelId).addFormDataPart("design", placedAnchors)
            .addFormDataPart(
                "file", file.name, file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            ).build()

    val request = Request.Builder().url("https://tgali.com/ardeco/ardeco/api/v1/upload/design")
        .post(requestBody).build()

    onUploadStart()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("Upload", "Failed to upload image: ${e.message}")
            // Use Looper to run Toast on the main thread
            Looper.getMainLooper()?.let {
                Handler(it).post {
                    onUploadFinish(false)
                    Toast.makeText(context, "Failed to upload image.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        override fun onResponse(call: Call, response: Response) {
            Looper.getMainLooper()?.let {
                Handler(it).post {
                    if (response.isSuccessful) {
                        Log.d("Upload", "Image uploaded successfully")
                        onUploadFinish(true)
                        Toast.makeText(context, "Image uploaded successfully.", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Log.e("Upload", "Failed to upload image: ${response.message}")
                        onUploadFinish(false)
                        Toast.makeText(context, "Failed to upload image.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    })
}

@Composable
fun SaveImageButton1(frame: Frame?, modelId: String, placedAnchors: String) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var placedAnchors by remember { mutableStateOf(mutableListOf<Pose>()) }

    Button(
        onClick = {
            frame?.let { currentFrame ->
                var image: Image? = null
                try {
                    image = currentFrame.acquireCameraImage()

                    if (image != null) {
                        // Convert the camera image to Bitmap and save it to a file
                        val width = image.width
                        val height = image.height
                        val yBuffer = image.planes[0].buffer // Y plane
                        val uBuffer = image.planes[1].buffer // U plane
                        val vBuffer = image.planes[2].buffer // V plane

                        val ySize = yBuffer.remaining()
                        val uSize = uBuffer.remaining()
                        val vSize = vBuffer.remaining()

                        val nv21 = ByteArray(ySize + uSize + vSize)
                        yBuffer.get(nv21, 0, ySize)
                        vBuffer.get(nv21, ySize, vSize)
                        uBuffer.get(nv21, ySize + vSize, uSize)

                        val yuvImage = YuvImage(nv21, ImageFormat.NV21, width, height, null)
                        val out = ByteArrayOutputStream()
                        yuvImage.compressToJpeg(Rect(0, 0, width, height), 100, out)
                        val imageBytes = out.toByteArray()
                        var bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

                        // Get the camera sensor orientation
                        val cameraManager =
                            context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
                        val cameraId = cameraManager.cameraIdList[0] // Assuming the first camera
                        val characteristics = cameraManager.getCameraCharacteristics(cameraId)
                        val sensorOrientation =
                            characteristics[CameraCharacteristics.SENSOR_ORIENTATION] ?: 0

                        // Get device rotation and adjust for the sensor orientation
                        val rotationDegrees =
                            when ((context as Activity).windowManager.defaultDisplay.rotation) {
                                Surface.ROTATION_0 -> 0
                                Surface.ROTATION_90 -> 90
                                Surface.ROTATION_180 -> 180
                                Surface.ROTATION_270 -> 270
                                else -> 0
                            }
                        val adjustedDegrees = (sensorOrientation - rotationDegrees + 360) % 360

                        // Rotate the bitmap if needed
                        if (adjustedDegrees != 0) {
                            val matrix = Matrix().apply {
                                postRotate(adjustedDegrees.toFloat())
                            }
                            bitmap = Bitmap.createBitmap(
                                bitmap,
                                0,
                                0,
                                bitmap.width,
                                bitmap.height,
                                matrix,
                                true
                            )
                        }

                        // Save the Bitmap to a file
                        val file = File(
                            context.externalCacheDir,
                            "AR_Capture_${System.currentTimeMillis()}.jpg"
                        )
                        FileOutputStream(file).use { output ->
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output)
                        }

                        // Upload the file using a coroutine
                        coroutineScope.launch {
                            uploadImage(modelId = modelId,
                                file = file,
                                placedAnchors = placedAnchors.toString(),
                                context = context,
                                onUploadStart = { isLoading = true },
                                onUploadFinish = { success ->
                                    isLoading = false
                                    if (!success) {
                                        Log.e("SaveImageButton", "Image upload failed")
                                    }
                                })
                        }
                    } else {
                        Toast.makeText(context, "Failed to capture image.", Toast.LENGTH_SHORT)
                            .show()
                    }
                } catch (e: Exception) {
                    Log.e("SaveImageButton", "Error: ${e.message}", e)
                } finally {
                    image?.close()
                }
            } ?: Toast.makeText(context, "No AR frame available.", Toast.LENGTH_SHORT).show()
        }, modifier = Modifier.padding(16.dp), enabled = !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(4.dp))
        } else {
            Text("Capture & Save")
        }
    }
}

@Composable
fun SaveImageButton(frame: Frame?, modelId: String, placedAnchors: String) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Button(
        onClick = {
            frame?.let { currentFrame ->
                var image: Image? = null
                try {
                    image = currentFrame.acquireCameraImage()

                    if (image != null) {
                        // Convert the camera image to Bitmap and save it to a file
                        val width = image.width
                        val height = image.height
                        val yBuffer = image.planes[0].buffer // Y plane
                        val uBuffer = image.planes[1].buffer // U plane
                        val vBuffer = image.planes[2].buffer // V plane

                        val ySize = yBuffer.remaining()
                        val uSize = uBuffer.remaining()
                        val vSize = vBuffer.remaining()

                        val nv21 = ByteArray(ySize + uSize + vSize)
                        yBuffer.get(nv21, 0, ySize)
                        vBuffer.get(nv21, ySize, vSize)
                        uBuffer.get(nv21, ySize + vSize, uSize)

                        val yuvImage = YuvImage(nv21, ImageFormat.NV21, width, height, null)
                        val out = ByteArrayOutputStream()
                        yuvImage.compressToJpeg(Rect(0, 0, width, height), 100, out)
                        val imageBytes = out.toByteArray()
                        var bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

                        // Get the camera sensor orientation
                        val cameraManager =
                            context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
                        val cameraId = cameraManager.cameraIdList[0] // Assuming the first camera
                        val characteristics = cameraManager.getCameraCharacteristics(cameraId)
                        val sensorOrientation =
                            characteristics[CameraCharacteristics.SENSOR_ORIENTATION] ?: 0

                        // Get device rotation and adjust for the sensor orientation
                        val rotationDegrees =
                            when ((context as Activity).windowManager.defaultDisplay.rotation) {
                                Surface.ROTATION_0 -> 0
                                Surface.ROTATION_90 -> 90
                                Surface.ROTATION_180 -> 180
                                Surface.ROTATION_270 -> 270
                                else -> 0
                            }
                        val adjustedDegrees = (sensorOrientation - rotationDegrees + 360) % 360

                        // Rotate the bitmap if needed
                        if (adjustedDegrees != 0) {
                            val matrix = Matrix().apply {
                                postRotate(adjustedDegrees.toFloat())
                            }
                            bitmap = Bitmap.createBitmap(
                                bitmap,
                                0,
                                0,
                                bitmap.width,
                                bitmap.height,
                                matrix,
                                true
                            )
                        }

                        // Save the Bitmap to a file
                        val file = File(
                            context.externalCacheDir,
                            "AR_Capture_${System.currentTimeMillis()}.jpg"
                        )
                        FileOutputStream(file).use { output ->
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output)
                        }

                        // Upload the file using a coroutine
                        coroutineScope.launch {
                            uploadImage(modelId = modelId,
                                file = file,
                                placedAnchors = placedAnchors,
                                context = context,
                                onUploadStart = { isLoading = true },
                                onUploadFinish = { success ->
                                    isLoading = false
                                    if (!success) {
                                        Log.e("SaveImageButton", "Image upload failed")
                                    }
                                })
                        }
                    } else {
                        Toast.makeText(context, "Failed to capture image.", Toast.LENGTH_SHORT)
                            .show()
                    }
                } catch (e: Exception) {
                    Log.e("SaveImageButton", "Error: ${e.message}", e)
                } finally {
                    image?.close()
                }
            } ?: Toast.makeText(context, "No AR frame available.", Toast.LENGTH_SHORT).show()
        }, modifier = Modifier.padding(16.dp), enabled = !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(4.dp))
        } else {
            Text("Capture & Save")
        }
    }
}

