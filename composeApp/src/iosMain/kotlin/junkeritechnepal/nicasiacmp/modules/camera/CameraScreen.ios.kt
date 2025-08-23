package junkeritechnepal.nicasiacmp.modules.camera

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.convert
import platform.AVFoundation.AVAuthorizationStatusAuthorized
import platform.AVFoundation.AVAuthorizationStatusNotDetermined
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVCaptureDeviceInput
import platform.AVFoundation.AVCaptureDevicePositionBack
import platform.AVFoundation.AVCaptureSession
import platform.AVFoundation.AVCaptureSessionPresetPhoto
import platform.AVFoundation.AVCaptureVideoPreviewLayer
import platform.AVFoundation.AVLayerVideoGravityResizeAspectFill
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.authorizationStatusForMediaType
import platform.AVFoundation.position
import platform.AVFoundation.requestAccessForMediaType
import platform.CoreGraphics.CGRect
import platform.QuartzCore.CATransaction
import platform.QuartzCore.kCATransactionDisableActions
import platform.UIKit.UIView
import platform.UIKit.UIViewContentMode
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_global_queue
import platform.darwin.dispatch_get_main_queue
import platform.posix.QOS_CLASS_USER_INITIATED

@Composable
actual fun CameraScreen() {
    var showCamera by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        requestCameraPermission { granted ->
            showCamera = granted
        }
    }

    if (showCamera) {
        CameraScreenView()
    } else {
        Text("Camera permission issues")
    }
}

@OptIn(ExperimentalForeignApi::class)
@Composable
fun CameraScreenView() {
    // 1. Define and remember the camera session and preview layer
    val cameraSession = remember { AVCaptureSession() }
    val cameraPreviewLayer = remember { AVCaptureVideoPreviewLayer(session = cameraSession) }

    // Use LaunchedEffect to manage the lifecycle of the camera session
    LaunchedEffect(Unit) {
        // Find the back-facing camera device. Using a more robust method is a good idea.
        val device = AVCaptureDevice
            .devicesWithMediaType(AVMediaTypeVideo)
            .firstOrNull { (it as AVCaptureDevice).position == AVCaptureDevicePositionBack } as? AVCaptureDevice

        if (device == null) {
            println("⚠️ No back camera device found")
            return@LaunchedEffect
        }

        // Create the device input.
        val input = AVCaptureDeviceInput.deviceInputWithDevice(device, null)

        if (input == null) {
            println("⚠️ Could not create AVCaptureDeviceInput")
            return@LaunchedEffect
        }

        // Configure the session on a background thread
        dispatch_async(dispatch_get_global_queue(QOS_CLASS_USER_INITIATED.toLong(), 0.convert())) {
            cameraSession.beginConfiguration()

            // Check if input can be added and add it
            if (cameraSession.canAddInput(input)) {
                cameraSession.addInput(input)
                println("✅ Added AVCaptureDeviceInput to session")
            } else {
                println("⚠️ Cannot add AVCaptureDeviceInput to session")
                cameraSession.commitConfiguration()
                return@dispatch_async
            }

            // Set session preset
            cameraSession.sessionPreset = AVCaptureSessionPresetPhoto

            // The `AVCaptureStillImageOutput` is deprecated. Use `AVCapturePhotoOutput` instead for a complete solution.
            // For now, we'll focus on just getting the preview working.

            cameraSession.commitConfiguration()

            // Start the session
            cameraSession.startRunning()
            println("✅ Camera session started")
        }
    }

    // 2. Use UIKitView to embed the native view
    UIKitView(
        modifier = Modifier.fillMaxSize(),
        background = Color.Transparent,
        factory = {
            val container = UIView()
            container.contentMode = UIViewContentMode.UIViewContentModeScaleAspectFit
            container.layer.addSublayer(cameraPreviewLayer)
            cameraPreviewLayer.videoGravity = AVLayerVideoGravityResizeAspectFill
            container
        },
        onResize = { container: UIView, rect: CValue<CGRect> ->
            // Use CATransaction to prevent animations when resizing
            CATransaction.begin()
            CATransaction.setValue(true, kCATransactionDisableActions)
            container.layer.setFrame(rect)
            cameraPreviewLayer.setFrame(rect)
            CATransaction.commit()
        },
        onRelease = {
            // Clean up resources when the composable is removed from the composition
            dispatch_async(dispatch_get_global_queue(QOS_CLASS_USER_INITIATED.toLong(), 0.convert())) {
                cameraSession.stopRunning()
                println("✅ Camera session stopped")
            }
        }
    )
}

fun requestCameraPermission(onResult: (granted: Boolean) -> Unit) {
    val status = AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo)

    when (status) {
        AVAuthorizationStatusAuthorized -> onResult(true)
        AVAuthorizationStatusNotDetermined -> {
            AVCaptureDevice.requestAccessForMediaType(AVMediaTypeVideo) { granted ->
                dispatch_async(dispatch_get_main_queue()) {
                    onResult(granted)
                }
            }
        }
        else -> onResult(false)
    }
}