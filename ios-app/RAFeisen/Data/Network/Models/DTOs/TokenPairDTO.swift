//
//  TokenPairDTO.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/21/25.
//

struct TokenPairDTO: Decodable {
    let accessToken: String
    let refreshToken: String
}

// MARK: Mapper to TokenPair
extension TokenPairDTO {
    func toTokenPair() -> TokenPair {
        return TokenPair(accessToken: accessToken,
                         refreshToken: refreshToken)
    }
}
