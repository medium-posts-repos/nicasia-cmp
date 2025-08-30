package junkeritechnepal.nicasiacmp.app.di.factory

import platform.UIKit.UIViewController

interface NativeViewFactory {
    fun provideCameraScreen(): UIViewController
}