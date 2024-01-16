package com.example.mmm_mobile

import android.annotation.SuppressLint
import android.content.Context
import androidx.camera.core.Camera
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import android.content.ContentValues.TAG
import android.widget.Toast
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.camera.core.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.openapitools.client.apis.ProductApi
import org.openapitools.client.infrastructure.ClientException
import java.util.concurrent.Executors

@ExperimentalGetImage
class BarcodeCamera(private val navController: NavController, private val context: Context) {
    private var camera: Camera? = null
    private var isScanning = false
    private var lastBarcode = ""

    @Composable
    fun CameraPreview(
        onBarcodeScanned: (Barcode?) -> Unit
    ) {
        // Will bind the camera to the lifecycle
        val lifecycleOwner = LocalLifecycleOwner.current

        val imageCapture = remember {
            ImageCapture
                .Builder()
                .build()
        }
        AndroidView(
            factory = { context ->
                PreviewView(context).apply {
                    layoutParams =
                        LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    scaleType = PreviewView.ScaleType.FILL_START

                    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
                    cameraProviderFuture.addListener({
                        startCamera(
                            context = context,
                            previewView = this,
                            imageCapture = imageCapture,
                            lifecycleOwner = lifecycleOwner,
                            onBarcodeScanned = onBarcodeScanned
                        )
                    }, ContextCompat.getMainExecutor(context))
                }
            }
        )
    }


    private fun startCamera(
        context: Context,
        previewView: PreviewView,
        lifecycleOwner: LifecycleOwner,
        imageCapture: ImageCapture,
        onBarcodeScanned: (Barcode?) -> Unit
    ) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

            val executor = Executors.newSingleThreadExecutor()

            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            // Types of barcodes
            val options = BarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                    Barcode.FORMAT_EAN_8,
                    Barcode.FORMAT_EAN_13,
                    Barcode.FORMAT_CODE_128
                )
                .enableAllPotentialBarcodes()
                .build()

            val scanner = BarcodeScanning.getClient(options)

            // Analyze the image
            imageAnalysis.setAnalyzer(executor) { imageProxy ->
                // Will be implemented
                processImageProxy(
                    barcodeScanner = scanner,
                    imageProxy = imageProxy,
                    onSuccess = onBarcodeScanned
                )
            }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                camera = cameraProvider.bindToLifecycle(
                    lifecycleOwner, cameraSelector, preview, imageCapture, imageAnalysis
                )

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(context))
    }


    @kotlin.OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("RestrictedApi")
    @OptIn(ExperimentalGetImage::class)
    private fun processImageProxy(
        barcodeScanner: BarcodeScanner,
        imageProxy: ImageProxy,
        onSuccess: (Barcode?) -> Unit
    ) {
        imageProxy.image?.let { image ->
            val inputImage =
                InputImage.fromMediaImage(
                    image,
                    imageProxy.imageInfo.rotationDegrees
                )

            if (!isScanning) {
                isScanning = true

                barcodeScanner.process(inputImage)
                    .addOnSuccessListener { barcodeList ->
                        val barcode = barcodeList.getOrNull(0)
                        onSuccess(barcode)

                        barcode?.let {
                            GlobalScope.launch {
                                val productApi = ProductApi()
                                it.rawValue?.let { barcode ->
                                    try {
                                        if(lastBarcode != barcode) {
                                            lastBarcode = barcode
                                            val id = productApi.getProductByBarcode(barcode).id
                                            withContext(Dispatchers.Main) {
                                                id?.let {
                                                    navController.navigate("Product/$id")
                                                }
                                            }
                                        }
                                    } catch (e: Exception) {
                                        Log.e(TAG, e.message.orEmpty())
                                        withContext(Dispatchers.Main) {
                                            if (e is ClientException && e.statusCode == 404) {
                                                Toast.makeText(context, "Product not found", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    .addOnFailureListener {
                        // This failure will happen if the barcode scanning model
                        // fails to download from Google Play Services
                        Log.e(TAG, it.message.orEmpty())
                    }.addOnCompleteListener {
                        // When the image is from CameraX analysis use case, must
                        // call image.close() on received images when finished
                        // using them. Otherwise, new images may not be received
                        // or the camera may stall.
                        imageProxy.image?.close()
                        imageProxy.close()
                        isScanning = false
                    }
            }
        }
    }
}