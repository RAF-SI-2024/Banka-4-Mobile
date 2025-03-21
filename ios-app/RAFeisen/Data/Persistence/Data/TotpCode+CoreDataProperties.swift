//
//  TotpCode+CoreDataProperties.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/21/25.
//
//

import Foundation
import CoreData


extension TotpCode {

    @nonobjc public class func fetchRequest() -> NSFetchRequest<TotpCode> {
        return NSFetchRequest<TotpCode>(entityName: "TotpCode")
    }

    @NSManaged public var secret: String?
    @NSManaged public var name: String?
    @NSManaged public var algorithm: String?
    @NSManaged public var digits: Int16
    @NSManaged public var period: Int16
    @NSManaged public var clientSecret: Client?

}

extension TotpCode : Identifiable {

}
