//
//  PersistenceControllerDataSeeder.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/20/25.
//

import Foundation
import CoreData

struct PersistenceControllerDataSeeder {
    static let shared = PersistenceControllerDataSeeder()
    
    func seed(class: NSManagedObject.Type, in context: NSManagedObjectContext) {
        switch `class` {
        case is Client.Type:
            seedClients(in: context)
        default:
            fatalError("Seeding not implemented for type: \(`class`)")
        }
    }
    
    private func seedClients(in context: NSManagedObjectContext) {
        let firstNames = ["Ginny", "Harry", "Hermione", "Luna", "Ron"]
        let lastNames = ["Granger", "Potter", "Lovegood", "Weasley"]
        
        for _ in 0..<10 {
            let chosenFirstName = firstNames.randomElement()!
            let chosenLastName = lastNames.randomElement()!
            
            let newItem = Client(context: context)
            newItem.id = UUID()
            newItem.firstName = chosenFirstName
            newItem.lastName = chosenLastName
            newItem.email = "\(chosenFirstName.lowercased()).\(chosenLastName.lowercased())@rafeisen.com"
            newItem.dateOfBirth = Date.random()
            newItem.gender = Gender.male
            newItem.address = "\(Int.random(in: 1...chosenFirstName.count)) Main St"
            newItem.has2FA = Bool.random()
            
        }
    }
    
}
