//
//  LoginViewModel.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/20/25.
//

import Foundation

class LoginViewModel: ObservableObject {
    @Published var credentials = LoginCredentials()
    @Published var error: Authentication.AuthenticationError?
    @Published var showProgressView = false
    @Published var storeCredentialsNext = false
    
    let authService: AuthService = AuthAPIService()
        
    var loginDisabled: Bool {
        credentials.email.isEmpty || credentials.password.isEmpty
    }
    
}
