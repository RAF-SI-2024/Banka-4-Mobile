//
//  GenderDTO.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/21/25.
//


enum GenderDTO: String, Decodable {
    case male = "MALE"
    case female = "FEMALE"
}


// MARK: Mapper to Gender
extension GenderDTO {
    func toGender() -> Gender {
        switch self {
        case .male:
            return .male
        case .female:
            return .female
        @unknown default:
            fatalError("Unsupported gender \(self)")
        }
    }
}
