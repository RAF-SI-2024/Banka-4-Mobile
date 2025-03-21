//
//  CacheTokenHandler.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/20/25.
//

import Foundation

final class CacheTokenHandler: TokenHandler {
    
    private let accountName = "rafeisen"
    private let serviceName: String
    
    init(serviceName: String) {
        self.serviceName = serviceName
    }
    
    func save(token: TokenUser) {
        do {
            let data = try JSONEncoder().encode(token)
            KeychainHelper.standard.save(data, service: serviceName, account: accountName)
        } catch {
            print("Error encoding token: \(error)")
        }
    }
    
    func getToken() -> TokenUser? {
        guard let data = KeychainHelper.standard.read(service: serviceName, account: accountName) else {
            return nil
        }
        do {
            return try JSONDecoder().decode(TokenUser.self, from: data)
        } catch {
            print("Error decoding token: \(error)")
            return nil
        }
    }
    
    func removeToken() {
        KeychainHelper.standard.delete(service: serviceName, account: accountName)
    }
}
