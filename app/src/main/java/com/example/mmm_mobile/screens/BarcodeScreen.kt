package com.example.mmm_mobile.screens

import android.Manifest
import androidx.camera.core.ExperimentalGetImage
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.mmm_mobile.BarcodeCamera
import com.example.mmm_mobile.ui.theme.MmmmobileTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@androidx.annotation.OptIn(ExperimentalGetImage::class)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun BarcodeScreen(navController: NavController, barcode : String? = null) {
    val cameraPermission = rememberPermissionState(
        Manifest.permission.CAMERA
    )
    LaunchedEffect(key1 = true) {
        if (!cameraPermission.status.isGranted) {
            cameraPermission.launchPermissionRequest()
        }
    }

    val camera = remember {
        BarcodeCamera(navController)
    }

    var lastScannedBarcode by remember {
        mutableStateOf<String?>(null)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Box {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(2f),
                contentAlignment = Alignment.BottomCenter
            ) {
                AnimatedVisibility(
                    visible = lastScannedBarcode != null,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    Box(
                        modifier = Modifier.padding(
                            horizontal = 16.dp,
                            vertical = 20.dp
                        )
                    ) {
                        Text(text = lastScannedBarcode.toString())
                    }

                }
            }
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .drawWithContent {
                        val canvasWidth = size.width
                        val canvasHeight = size.height
                        val width = canvasWidth * .9f
                        val height = width * 3 / 4f

                        drawContent()

                        drawRect(Color(0x99000000))

                        // Draws the rectangle in the middle
                        drawRoundRect(
                            topLeft = Offset(
                                (canvasWidth - width) / 2,
                                canvasHeight * .3f
                            ),
                            size = Size(width, height),
                            color = Color.Transparent,
                            cornerRadius = CornerRadius(24.dp.toPx()),
                            blendMode = BlendMode.SrcIn
                        )

                        // Draws the rectangle outline
                        drawRoundRect(
                            topLeft = Offset(
                                (canvasWidth - width) / 2,
                                canvasHeight * .3f
                            ),
                            color = Color.White,
                            size = Size(width, height),
                            cornerRadius = CornerRadius(24.dp.toPx()),
                            style = Stroke(
                                width = 2.dp.toPx()
                            ),
                            blendMode = BlendMode.Src
                        )
                    }
            ) {
                if (cameraPermission.status.isGranted) {
                    camera.CameraPreview(
                        onBarcodeScanned = { barcode ->
                            barcode?.displayValue?.let {
                                lastScannedBarcode = it
                            }
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun Greeting234(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview343() {
    MmmmobileTheme {
        Greeting234("Android!@#")
    }
}
