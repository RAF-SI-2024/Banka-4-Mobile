//
//  TokenHandler.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/20/25.
//

import Foundation

typealias Token = String

struct TokenUser: Codable {
    let email: String
    let token: Token
}

protocol StoreTokenHandler {
    func save(token: TokenUser)
}

protocol RemoveTokenHandler {
    func removeToken()
}

protocol GetTokenHandler {
    func getToken() -> TokenUser?
}

protocol TokenHandler: StoreTokenHandler, RemoveTokenHandler, GetTokenHandler { }

extension TokenHandler {
    
    var hasToken: Bool {
        getToken() != nil
    }
}

