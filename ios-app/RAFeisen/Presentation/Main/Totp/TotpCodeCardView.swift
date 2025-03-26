//
//  TotpCodeCardView.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/20/25.
//

import SwiftUI
import SwiftOTP

struct TotpCodeCardView: View {
    @ObservedObject var code: TotpCode
    @State private var currentCode: String = ""
    @State private var timeRemaining: Double = 30
    @State private var totpInstance: TOTP?
    let verifyService: VerifyService = VerifyAPIService()
    let clientService: ClientService = ClientAPIService()

    let timer = Timer.publish(every: 1, on: .main, in: .common).autoconnect()

    var body: some View {
        HStack(spacing: 16) {
            Image(systemName: "key.fill")
                .font(.title2)
                .foregroundColor(.blue)
                .frame(width: 40)
            
            VStack(alignment: .leading, spacing: 12) {
                Text(code.name ?? "Unknown Service")
                    .font(.subheadline)
                
                Text(currentCode)
                    .font(.system(.title2, design: .monospaced))
                    .contentTransition(.numericText())
                
                ProgressView(value: timeRemaining, total: Double(code.period))
                    .tint(timeRemaining < 10 ? .red : .blue)
                    .animation(.easeInOut, value: timeRemaining)
            }
            
            Spacer()
            
            VStack(alignment: .trailing) {
                Text("\(Int(timeRemaining))s")
                    .monospacedDigit()
                    .foregroundColor(timeRemaining < 10 ? .red : .secondary)
                    .font(.headline)
            }
        }
        .padding(.vertical, 8)
        .onAppear(perform: initializeTOTP)
        .onReceive(timer) { _ in
            updateCodeAndTimer()
        }
    }
    
    private func initializeTOTP() {
        guard let secretString = code.secret,
              let secretData = base32DecodeToData(secretString) else {
            currentCode = "Invalid secret"
            return
        }
        
        let digits = Int(code.digits)
        let period = Int(code.period)
        
        guard (6...8).contains(digits) else {
            currentCode = "Invalid digits"
            return
        }
        
        totpInstance = TOTP(
            secret: secretData,
            digits: digits,
            timeInterval: period,
            algorithm: .sha1
        )
        
        updateCodeAndTimer()
        
        Task {
            await verifyService.verifyNewAuthenticatorCode(code: currentCode)
            await clientService.getMe()
        }

    }
    
    private func updateCodeAndTimer() {
        guard let totp = totpInstance else { return }
        
        let currentDate = Date()
        let currentTime = Int(currentDate.timeIntervalSince1970)
        
        // Generate new code
        if let code = totp.generate(secondsPast1970: currentTime) {
            currentCode = code
        }
        
        // Calculate remaining time
        if code.period != 0 {
            let remainingTime = Int(code.period) - (currentTime % Int(code.period))
            timeRemaining = Double(remainingTime)
        }
    }
}

