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
                    BubbleView(
                        colors: [.blue, .teal],
                        alignment: .topLeading,
                        xOffsetRange: -60...(-20),
                        yOffsetRange: -80...(-50),
                        scaleRange: 0.8...1.2,
                        rotationRange: 0...180,
                        duration: 15
                    )
                    
                    BubbleView(
                        colors: [.orange, .yellow],
                        alignment: .bottomTrailing,
                        xOffsetRange: 20...60,
                        yOffsetRange: 50...80,
                        scaleRange: 0.7...1.1,
                        rotationRange: (-180)...0,
                        duration: 18
                    )
                    
                    BubbleView(
                        colors: [.purple, .pink],
                        alignment: .topTrailing,
                        xOffsetRange: -30...30,
                        yOffsetRange: -100...(-60),
                        scaleRange: 0.5...1.0,
                        rotationRange: 0...360,
                        duration: 20
                    )
                }
                .blur(radius: 10)
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


struct BubbleView: View {
    let colors: [Color]
    let alignment: Alignment
    let xOffsetRange: ClosedRange<CGFloat>
    let yOffsetRange: ClosedRange<CGFloat>
    let scaleRange: ClosedRange<CGFloat>
    let rotationRange: ClosedRange<Double>
    let duration: Double
    
    @State private var animate = false
    
    var body: some View {
        Circle()
            .fill(
                AngularGradient(
                    colors: colors,
                    center: .center,
                    startAngle: .degrees(animate ? rotationRange.lowerBound : rotationRange.upperBound),
                    endAngle: .degrees(animate ? rotationRange.upperBound : rotationRange.lowerBound)
                )
            )
            .frame(width: 140, height: 140)
            .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: alignment)
            .offset(
                x: animate ? xOffsetRange.upperBound : xOffsetRange.lowerBound,
                y: animate ? yOffsetRange.upperBound : yOffsetRange.lowerBound
            )
            .scaleEffect(animate ? scaleRange.upperBound : scaleRange.lowerBound)
            .opacity(animate ? 0.6 : 0.3)
            .animation(
                .easeInOut(duration: duration)
                .repeatForever(),
                value: animate
            )
            .onAppear { animate = true }
    }
}

#Preview {
    LoginView()
        .environmentObject(Authentication())
}
