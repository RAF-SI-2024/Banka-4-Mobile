//
//  LoginView.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/20/25.
//

import SwiftUI

struct LoginView: View {
    @EnvironmentObject private var authentication: Authentication
    @StateObject private var loginVM = LoginViewModel()
    
    @Environment(\.managedObjectContext) private var viewContext
    
    @FetchRequest(sortDescriptors: [
        NSSortDescriptor(keyPath: \Client.firstName, ascending: true),
        NSSortDescriptor(keyPath: \Client.lastName, ascending: true)
    ], animation: .default)
    var clients: FetchedResults<Client>
    
    var body: some View {
        VStack {
            /// Login View
            VStack(spacing: 13) {
                Text("RAFeisen")
                    .font(.title.bold())
                
                VStack(alignment: .leading, spacing: 8) {
                    Text("login.email.placeholder")
                        .font(.callout.bold())
                    TextField(hint: "example@domain.com", value: $loginVM.credentials.email)
                    
                    Text("login.password.placeholder")
                        .font(.callout.bold())
                    TextField(hint: "●●●●●●", value: $loginVM.credentials.password, isPassword: true)
                    
                    if loginVM.showProgressView {
                        ProgressView()
                    }

                    Button {
                        Task {
                            await login()
                        }
                    } label: {
                        Text("login")
                            .font(.title3)
                            .fontWeight(.semibold)
                            .foregroundStyle(.black)
                            .padding(.vertical, 12)
                            .frame(maxWidth: .infinity)
                            .background(.white)
                            .clipShape(.rect(cornerRadius: 8, style: .continuous))
                    }
                    .padding(.top, 30)

                }
                
                /// Other Login Options
//                if authentication.biometricType() != .none && authentication.hasToken() {
//                    Button {
//                        authentication.requestBiometricUnlock { (result:Result<LoginCredentials, Authentication.AuthenticationError>) in
//                            switch result {
//                            case .success(let credentials):
//                                loginVM.credentials = credentials
//                                loginVM.login { success in
//                                    authentication.updateValidation(success: success)
//                                }
//                            case .failure(let error):
//                                loginVM.error = error
//                            }
//                        }
//                    } label: {
//                        Image(systemName: authentication.biometricType() == .face ? "faceid" : "touchid")
//                            .resizable()
//                            .frame(width: 50, height: 50)
//                    }
//                    .padding(.top, 20)
//                }
            }
            .padding(.horizontal, 30)
            .padding(.top, 35)
            .padding(.bottom, 25)
            .background(
                .ultraThinMaterial,
                in: .rect(cornerRadius: 10, style: .continuous)
                
            )
            .clipShape(.rect(cornerRadius: 10, style: .continuous))
            
            /// Light white border
            .background {
                RoundedRectangle(cornerRadius: 10, style: .continuous)
                .stroke(.white.opacity(0.3), lineWidth: 1.5)
            }
            /// Adding Shadow
            .shadow(color: .black.opacity(0.2), radius: 10)
            .padding(.horizontal, 40)
            .background {
                ZStack {
                    Circle()
                        .fill(
                            .linearGradient(colors: [
                                .blue,
                                .teal
                            ], startPoint: .top, endPoint: .bottom)
                        )
                        .frame(width: 140, height: 140)
                        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .topLeading)
                        .offset(x: -25, y: -55)
                    
                    Circle()
                        .fill(
                            .linearGradient(colors: [
                                .yellow,
                                .orange
                            ], startPoint: .top, endPoint: .bottom)
                        )
                        .frame(width: 140, height: 140)
                        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .bottomTrailing)
                        .offset(x: 25, y: 55)

                }
            }
            
        }
        .frame(maxWidth: 390)
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
    
    // Custom Text Field
    @ViewBuilder
    func TextField(hint: String, value: Binding<String>, isPassword: Bool = false) -> some View {
        Group {
            if isPassword {
                SecureField(hint, text: value)
            } else {
                SwiftUI.TextField(hint, text: value)
            }
        }
        .padding(.vertical, 10)
        .padding(.horizontal, 15)
        .background(.secondary.opacity(0.12))
        .clipShape(.rect(cornerRadius: 8, style: .continuous))
        
    }
    
    func login() async {
        let email = loginVM.credentials.email
        let result = await loginVM.authService.clientLogin(params: ClientLoginRequestParams(email: email,
                                    password: loginVM.credentials.password))
        debugPrint(result)

        switch result {
        case .success(let response):
            authentication.cacheTokenHandler.save(token: TokenUser(email: email, token: response.accessToken))
            authentication.updateValidation(success: true)
        case .failure(let error): break
            
        }
    }
    
}

#Preview {
    LoginView()
        .environmentObject(Authentication())
}
