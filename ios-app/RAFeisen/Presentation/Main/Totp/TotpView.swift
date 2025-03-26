//
//  TotpView.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/21/25.
//

import SwiftUI
import CoreData
import SwiftOTP

struct TotpView: View {
    @EnvironmentObject private var authentication: Authentication
    let verifyService: VerifyService = VerifyAPIService()
    
    private var email: String? { authentication.cacheTokenHandler.getToken()?.email }
    
    private var codesRequest: FetchRequest<TotpCode>
    private var codes: FetchedResults<TotpCode> { codesRequest.wrappedValue }
    
    init() {
        codesRequest = FetchRequest(entity: TotpCode.entity(),
                                  sortDescriptors: [NSSortDescriptor(key: "name", ascending: true)],
                                  predicate: NSPredicate(value: false))
    }

    var body: some View {
        NavigationStack {
            Group {
                if codes.isEmpty {
                    ContentUnavailableView(
                        "No TOTP Codes",
                        systemImage: "key.fill",
                        description: Text("Add your first TOTP code using the + button")
                    )
                } else {
                    List {
                        ForEach(codes, id: \.self) { code in
                            TotpCodeCardView(code: code)
                                .swipeActions(edge: .trailing) {
                                    Button(role: .destructive) {
                                        deleteCode(code)
                                    } label: {
                                        Label("Delete", systemImage: "trash")
                                    }
                                }
                        }
                    }
                    .listStyle(.insetGrouped)
                }
            }
            .navigationTitle("Authenticator Codes")
            .toolbar {
                ToolbarItem(placement: .primaryAction) {
                    if codes.count == 0 {
                        Button {
                            Task {
                                let result = await verifyService.regenerateAuthenticator()
                                switch result {
                                case .success(let response):
                                    handleTOTPURL(URL(string:  response.url)!)
                                case .failure(let error):
                                    debugPrint(error)
                                }
                            }
                        } label: {
                            Image(systemName: "plus")
                        }
                    }
                }
            }
            .onAppear(perform: updatePredicate)
            .onChange(of: email, updatePredicate)
            .onOpenURL { url in
                handleTOTPURL(url)
            }
        }
    }
    
    private func updatePredicate() {
        let predicate: NSPredicate
        if let email = email {
            predicate = NSPredicate(format: "clientSecret.email == %@", email)
        } else {
            predicate = NSPredicate(value: false)
        }
        
        codesRequest.wrappedValue.nsPredicate = predicate
    }
    
    private func deleteCode(_ code: TotpCode) {
        let context = PersistenceController.shared.container.viewContext
        context.delete(code)
        
        do {
            try context.save()
        } catch {
            print("Error deleting code: \(error)")
            context.rollback()
        }
    }
    
    func handleTOTPURL(_ url: URL) {
        guard url.scheme == "otpauth", url.host == "totp" else { return }
        
        // Parse URL components
        let components = URLComponents(url: url, resolvingAgainstBaseURL: false)
        let queryItems = components?.queryItems ?? []
        
        let secret = queryItems.first(where: { $0.name == "secret" })?.value ?? ""
        let algorithm = queryItems.first(where: { $0.name == "algorithm" })?.value ?? "SHA1"
        let digits = Int16(queryItems.first(where: { $0.name == "digits" })?.value ?? "6") ?? 6
        let period = Int16(queryItems.first(where: { $0.name == "period" })?.value ?? "30") ?? 30
        
        let label = url.lastPathComponent
        
        // Get authenticated user email
        guard let email = authentication.cacheTokenHandler.getToken()?.email else {
            print("No authenticated user")
            return
        }
        
        // Get Core Data context
        let context = PersistenceController.shared.container.viewContext
        
        context.perform {
            // Fetch current user
            let fetchRequest: NSFetchRequest<Client> = Client.fetchRequest()
            fetchRequest.predicate = NSPredicate(format: "email == %@", email)
            fetchRequest.fetchLimit = 1
            
            do {
                guard let client = try context.fetch(fetchRequest).first else {
                    print("User not found in database")
                    return
                }
                // Add to client's TOTP secrets
                let existingCodes = client.totpSecret?.compactMap { $0 as? TotpCode }
                guard (existingCodes?.contains(where: { $0.secret == secret })) == false else {
                    print("TOTP secret already exists")
                    return
                }
                
                // Create new TOTP code
                let totpCode = TotpCode(context: context)
                totpCode.secret = secret
                totpCode.name = label
                totpCode.algorithm = algorithm
                totpCode.digits = digits
                totpCode.period = period
                
                client.addToTotpSecret(totpCode)
                
                // Save changes
                try context.save()
                print("Successfully saved TOTP code for \(email)")
                
            } catch {
                print("Error saving TOTP code: \(error)")
                context.rollback()
            }
        }
    }
    

}

#Preview {
    let context = PersistenceController.preview.container.viewContext
    let client = Client(context: context)
    client.email = "test@example.com"
    
    let code = TotpCode(context: context)
    code.name = "Example Service"
    code.secret = "SECRET123"
    code.algorithm = "SHA1"
    code.digits = 6
    code.period = 30
    client.addToTotpSecret(code)
    
    return TotpView()
        .environment(\.managedObjectContext, context)
        .environmentObject(Authentication())
}
