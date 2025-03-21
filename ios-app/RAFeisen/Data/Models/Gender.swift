//
//  AgeType.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/20/25.
//

import Foundation

@objc
public enum Gender: Int16
{
    case male
    case female
}

extension Gender {
    var description: String? {
        switch self {
        case .male:
            return String(localized: "gender.male", comment: "Gender enum representable value")
        case .female:
            return String(localized: "gender.female", comment: "Gender enum representable value")
        }
        
    }
}
