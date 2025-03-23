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
            .onAppear {
                Task {
                    await getMe()
                }
            }
            .tabItem {
                Label("tab.profile", systemImage: "person.crop.circle")
            }
        }
        .task {
            await getMe()
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
}

#Preview {
    MainView()
        .environment(\.managedObjectContext, PersistenceController.preview.container.viewContext)
        .environmentObject(Authentication())
}
