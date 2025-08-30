//
//  CameraScreen.swift
//  iosApp
//
//  Created by swornim-shah on 30/08/2025.
//

import SwiftUI
import AVFoundation
import ComposeApp

public struct CameraScreenView: View {
    @State private var isCameraAuthorized: Bool = false
    @State private var showPermissionDeniedAlert: Bool = false

    public var body: some View {
        VStack {
            if isCameraAuthorized {
                CameraView()
                    .frame(width: .infinity, height: .infinity)
            } else {
                PermissionPlaceholder()
            }
        }
        .ignoresSafeArea()
        .onAppear(perform: checkPermission)
        .alert(isPresented: $showPermissionDeniedAlert) {
            Alert(
                title: Text("Camera Permission Denied"),
                message: Text("Please enable camera access in Settings to use the camera."),
                primaryButton: .default(Text("Open Settings"), action: openSettings),
                secondaryButton: .cancel()
            )
        }
    }

    private func checkPermission() {
        switch AVCaptureDevice.authorizationStatus(for: .video) {
        case .authorized:
            isCameraAuthorized = true
        case .notDetermined:
            isCameraAuthorized = false
        case .denied, .restricted:
            isCameraAuthorized = false
            showPermissionDeniedAlert = false
        @unknown default:
            isCameraAuthorized = false
        }
    }

    private func requestPermission() {
        AVCaptureDevice.requestAccess(for: .video) { granted in
            DispatchQueue.main.async {
                if granted {
                    isCameraAuthorized = true
                } else {
                    isCameraAuthorized = false
                    showPermissionDeniedAlert = true
                }
            }
        }
    }

    private func openSettings() {
        guard let url = URL(string: UIApplication.openSettingsURLString) else { return }
        if UIApplication.shared.canOpenURL(url) {
            UIApplication.shared.open(url)
        }
    }
}

// MARK: - Permission Placeholder
public struct PermissionPlaceholder: View {
    public var body: some View {
        VStack(spacing: 12) {
            Image(systemName: "camera.fill")
                .font(.system(size: 48))
                .padding()
                .background(Color.gray.opacity(0.15))
                .clipShape(Circle())

            Text("Camera is not available")
                .font(.headline)

            Text("Grant camera permission to show the live preview.")
                .font(.subheadline)
                .multilineTextAlignment(.center)
                .foregroundColor(.secondary)
                .padding(.horizontal)
        }
        .padding()
        .frame(maxWidth: .infinity)
        .background(Color(UIColor.secondarySystemBackground))
        .cornerRadius(12)
    }
}

// MARK: - CameraView using UIViewControllerRepresentable
public struct CameraView: UIViewControllerRepresentable {
    public func makeUIViewController(context: Context) -> CameraViewController {
        let vc = CameraViewController()
        return vc
    }

    public func updateUIViewController(_ uiViewController: CameraViewController, context: Context) {
        // no-op for now
    }
}

// MARK: - CameraViewController (AVCapture preview)
public class CameraViewController: UIViewController {
    private let session = AVCaptureSession()
    private var previewLayer: AVCaptureVideoPreviewLayer?
    private let sessionQueue = DispatchQueue(label: "camera.session.queue")

    public override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = .black
        configureSession()
    }

    public override func viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()
        previewLayer?.frame = view.bounds
    }

    public override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        sessionQueue.async { [weak self] in
            self?.session.startRunning()
        }
    }

    public override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        sessionQueue.async { [weak self] in
            self?.session.stopRunning()
        }
    }

    private func configureSession() {
        session.beginConfiguration()
        session.sessionPreset = .high

        // Input
        guard let device = AVCaptureDevice.default(.builtInWideAngleCamera, for: .video, position: .back) else {
            session.commitConfiguration()
            return
        }

        do {
            let input = try AVCaptureDeviceInput(device: device)
            if session.canAddInput(input) {
                session.addInput(input)
            }
        } catch {
            print("Camera input error:\(error)")
            session.commitConfiguration()
            return
        }

        // Output (not used now but ready for extension)

        session.commitConfiguration()

        // Preview
        let preview = AVCaptureVideoPreviewLayer(session: session)
        preview.videoGravity = .resizeAspectFill
        preview.connection?.videoOrientation = .portrait
        DispatchQueue.main.async {
            self.previewLayer = preview
            preview.frame = self.view.bounds
            self.view.layer.insertSublayer(preview, at: 0)
        }
    }
}
