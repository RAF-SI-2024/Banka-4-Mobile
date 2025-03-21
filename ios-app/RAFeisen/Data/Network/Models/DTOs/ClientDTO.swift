//
//  ClientDTO.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/21/25.
//

import Foundation
import CoreData

struct ClientDTO: Decodable {
    let id: UUID
    let firstName: String
    let lastName: String
    let dateOfBirth: Date
    let gender: GenderDTO
    let email: String
    let phone: String
    let address: String
    let has2FA: Bool
}

// TODO: Move this to a separate mapper function
// MARK: Mapper to Client
extension ClientDTO {
    func toClient(in context: NSManagedObjectContext) -> Client {
        // Check for existing client first
        let client: Client = fetchExistingClient(in: context) ?? Client(context: context)
        
        // Map all properties
        client.id = id
        client.genderValue = gender.toGender().rawValue
        client.firstName = firstName
        client.lastName = lastName
        client.dateOfBirth = dateOfBirth
        client.email = email
        client.phone = phone
        client.address = address
        client.has2FA = has2FA
                
        return client
    }
    
    private func fetchExistingClient(in context: NSManagedObjectContext) -> Client? {
        let fetchRequest: NSFetchRequest<Client> = Client.fetchRequest()
        fetchRequest.predicate = NSPredicate(format: "id == %@", id as CVarArg)
        fetchRequest.fetchLimit = 1
        
        do {
            return try context.fetch(fetchRequest).first
        } catch {
            print("Error fetching existing client: \(error)")
            return nil
        }
    }
}
