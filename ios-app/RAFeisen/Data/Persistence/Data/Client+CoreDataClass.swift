//
//  Client+CoreDataClass.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/21/25.
//
//

import Foundation
import CoreData

@objc(Client)
public class Client: NSManagedObject {
    
    var gender: Gender {
        get {
            return Gender(rawValue: genderValue) ?? .male
        }
        set {
            genderValue = newValue.rawValue
        }
    }
}
