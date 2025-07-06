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
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.convert
import platform.AVFoundation.AVAuthorizationStatusAuthorized
import platform.AVFoundation.AVAuthorizationStatusNotDetermined
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVCaptureDeviceInput
import platform.AVFoundation.AVCaptureDevicePositionBack
import platform.AVFoundation.AVCaptureSession
import platform.AVFoundation.AVCaptureSessionPresetPhoto
import platform.AVFoundation.AVCaptureStillImageOutput
import platform.AVFoundation.AVCaptureVideoPreviewLayer
import platform.AVFoundation.AVLayerVideoGravityResizeAspectFill
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.AVVideoCodecJPEG
import platform.AVFoundation.AVVideoCodecKey
import platform.AVFoundation.authorizationStatusForMediaType
import platform.AVFoundation.position
import platform.AVFoundation.requestAccessForMediaType
import platform.UIKit.UIView
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
    val device = AVCaptureDevice
        .devicesWithMediaType(AVMediaTypeVideo)
        .firstOrNull { (it as AVCaptureDevice).position == AVCaptureDevicePositionBack } as? AVCaptureDevice

    if (device == null) {
        println("⚠️ No back camera device found")
        return
    }

    val input = AVCaptureDeviceInput.deviceInputWithDevice(device, null) as? AVCaptureDeviceInput
    if (input == null) {
        println("⚠️ Could not create AVCaptureDeviceInput")
        return
    }

    val output = AVCaptureStillImageOutput()
    output.outputSettings = mapOf(AVVideoCodecKey to AVVideoCodecJPEG)

    val session = AVCaptureSession()

    session.sessionPreset = AVCaptureSessionPresetPhoto

    session.addInput(input)
    session.addOutput(output)

    val cameraPreviewLayer = remember { AVCaptureVideoPreviewLayer(session = session) }
    println("⚠ Ready to be running..")

    UIKitView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            val container = UIView()
            container.setNeedsLayout()
            container.layoutIfNeeded()
            cameraPreviewLayer.frame = container.bounds

            container.layer.addSublayer(cameraPreviewLayer)
            cameraPreviewLayer.videoGravity = AVLayerVideoGravityResizeAspectFill


            dispatch_async(dispatch_get_global_queue(QOS_CLASS_USER_INITIATED.toLong(), 0.convert())) {
                session.startRunning()
            }
            container
        })
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