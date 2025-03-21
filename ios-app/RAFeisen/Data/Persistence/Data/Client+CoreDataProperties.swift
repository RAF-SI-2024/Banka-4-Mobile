//
//  Client+CoreDataProperties.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/21/25.
//
//

import Foundation
import CoreData


extension Client {

    @nonobjc public class func fetchRequest() -> NSFetchRequest<Client> {
        return NSFetchRequest<Client>(entityName: "Client")
    }

    @NSManaged public var address: String?
    @NSManaged public var dateOfBirth: Date?
    @NSManaged public var email: String?
    @NSManaged public var firstName: String?
    @NSManaged public var genderValue: Int16
    @NSManaged public var has2FA: Bool
    @NSManaged public var id: UUID?
    @NSManaged public var lastName: String?
    @NSManaged public var phone: String?
    @NSManaged public var totpSecret: NSSet?

}

// MARK: Generated accessors for totpSecret
extension Client {

    @objc(addTotpSecretObject:)
    @NSManaged public func addToTotpSecret(_ value: TotpCode)

    @objc(removeTotpSecretObject:)
    @NSManaged public func removeFromTotpSecret(_ value: TotpCode)

    @objc(addTotpSecret:)
    @NSManaged public func addToTotpSecret(_ values: NSSet)

    @objc(removeTotpSecret:)
    @NSManaged public func removeFromTotpSecret(_ values: NSSet)

}

extension Client : Identifiable {

}
