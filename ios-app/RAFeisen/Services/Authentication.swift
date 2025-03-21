//
//  Authentication.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/20/25.
//

import SwiftUI
import LocalAuthentication

class Authentication: ObservableObject {
    @Published var isValidated = false
    let cacheTokenHandler: TokenHandler

    init(isValidated: Bool = false) {
        self.isValidated = isValidated
        self.cacheTokenHandler = CacheMigrationTokenHandler()
    }
    
    enum BiometricType {
        case none
        case face
        case touch
    }
    
    enum AuthenticationError: Error, LocalizedError, Identifiable {
        case invalidCredentials
        case deniedAccess
        case noFaceIdEnrolled
        case noFingerprintEnrolled
        case biometrictError
        case credentialsNotSaved
        
        var id: String {
            self.localizedDescription
        }
        
        var errorDescription: String? {
            switch self {
            case .invalidCredentials:
                return String(localized: "Either your email or password are incorrect. Please try again.", comment: "Error: Authentication Service")
            case .deniedAccess:
                return String(localized: "You have denied access. Please go to the settings app and locate this application and turn Face ID on.", comment: "Error: Authentication Service")
            case .noFaceIdEnrolled:
                return String(localized: "You have not registered any Face Ids yet.", comment: "Error: Authentication Service")
            case .noFingerprintEnrolled:
                return String(localized: "You have not registered any fingerprints yet.", comment: "Error: Authentication Service")
            case .biometrictError:
                return String(localized: "Your face or fingerprint were not recognized.", comment: "Error: Authentication Service")
            case .credentialsNotSaved:
                return String(localized: "Your credentials have not been saved. Do you want to save them after the next successful login?", comment: "Error: Authentication Service")
            }
        }
    }
    
    func updateValidation(success: Bool) {
        withAnimation {
            isValidated = success
            if !isValidated {
                cacheTokenHandler.removeToken()
            }
        }
    }
    
    func biometricType() -> BiometricType {
        let authContext = LAContext()
        let _ = authContext.canEvaluatePolicy(.deviceOwnerAuthenticationWithBiometrics, error: nil)
        switch authContext.biometryType {
        case .none:
            return .none
        case .touchID:
            return .touch
        case .faceID:
            return .face
        case .opticID:
            return .none
        @unknown default:
            return .none
        }
    }
    
    func requestBiometricUnlock(completion: @escaping (Result<LoginCredentials, AuthenticationError>) -> Void) {
        let credentials: LoginCredentials? = LoginCredentials(email: "anything", password: "password")

        guard let credentials = credentials else {
            completion(.failure(.credentialsNotSaved))
            return
        }
        let context = LAContext()
        var error: NSError?
        let canEvaluate = context.canEvaluatePolicy(.deviceOwnerAuthenticationWithBiometrics, error: &error)
        if let error = error {
            switch error.code {
            case -6:
                completion(.failure(.deniedAccess))
            case -7:
                if context.biometryType == .faceID {
                    completion(.failure(.noFaceIdEnrolled))
                } else {
                    completion(.failure(.noFingerprintEnrolled))
                }
            default:
                completion(.failure(.biometrictError))
            }
            return
        }
        if canEvaluate {
            if context.biometryType != .none {
                context.evaluatePolicy(.deviceOwnerAuthenticationWithBiometrics, localizedReason: "Need to access credentials.") { success, error in
                    DispatchQueue.main.async {
                        if error != nil {
                            completion(.failure(.biometrictError))
                        } else {
                            completion(.success(credentials))
                        }
                    }
                }
            }
        }
    }
}
