//
//  MainView.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/20/25.
//

import SwiftUI
import CoreData

struct MainView: View {
    @EnvironmentObject private var authentication: Authentication
    let clientService: ClientService = ClientAPIService()
    
    @State var isLoading: Bool = false
    @State var client: Client?
    
    var body: some View {
        TabView {
            TotpView()
                .tabItem {
                    Label("tab.codes", systemImage: "qrcode")
                }
            
            ProfileView(client: $client, logoutAction: {
                authentication.updateValidation(success: false)
            })
            .tabItem {
                Label("tab.profile", systemImage: "person.crop.circle")
            }
        }
        .task {
            await getMe()
        }
        .onOpenURL { url in
            handleTOTPURL(url)
        }
    }
    
    func getMe() async {
        let result = await clientService.getMe()
        switch result {
        case .success(let client):
            self.client = client
        case .failure(let error):
            // TOOD: Do Erorr checking if unathorized
            print("Error: \(error)")
            authentication.updateValidation(success: false)
        }
    }
    
    // MARK: - TOTP Handling
    func handleTOTPURL(_ url: URL) {
        guard url.scheme == "otpauth", url.host == "totp" else { return }
        
        // Parse URL components
        let components = URLComponents(url: url, resolvingAgainstBaseURL: false)
        let queryItems = components?.queryItems ?? []
        
        // Extract parameters
        let secret = queryItems.first(where: { $0.name == "secret" })?.value ?? ""
        let issuer = queryItems.first(where: { $0.name == "issuer" })?.value ?? ""
        let algorithm = queryItems.first(where: { $0.name == "algorithm" })?.value ?? "SHA1"
        let digits = Int16(queryItems.first(where: { $0.name == "digits" })?.value ?? "6") ?? 6
        let period = Int16(queryItems.first(where: { $0.name == "period" })?.value ?? "30") ?? 30
        
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
                totpCode.name = issuer
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
    MainView()
        .environment(\.managedObjectContext, PersistenceController.preview.container.viewContext)
        .environmentObject(Authentication())
}
