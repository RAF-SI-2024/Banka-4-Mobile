//
//  RAFeisenApp.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/20/25.
//

import SwiftUI

@main
struct RAFeisenApp: App {
    @StateObject var authentication = Authentication()
    
    let persistenceController = PersistenceController.shared

    var body: some Scene {
        WindowGroup {
            if authentication.cacheTokenHandler.hasToken {
                MainView()
                    .environmentObject(authentication)
                    .environment(\.managedObjectContext, persistenceController.container.viewContext)
            } else {
                LoginView()
                    .environmentObject(authentication)
                    .environment(\.managedObjectContext, persistenceController.container.viewContext)
            }

        }
    }
}
