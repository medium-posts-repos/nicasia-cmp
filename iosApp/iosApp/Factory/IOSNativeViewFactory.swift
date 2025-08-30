//
//  IOSNativeViewFactory.swift
//  iosApp
//
//  Created by swornim-shah on 30/08/2025.
//

import Foundation
import ComposeApp
import UIKit
import SwiftUI

public final class IOSNativeViewFactory: NativeViewFactory {
    public static let shared = IOSNativeViewFactory()
    
    public func provideCameraScreen() -> UIViewController {
        return UIHostingController(rootView: CameraScreenView())
    }
}
