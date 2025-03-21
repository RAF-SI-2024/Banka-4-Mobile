//
//  CacheMigrationTokenHandler.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/20/25.
//

import Foundation

final class CacheMigrationTokenHandler: TokenHandler {
    
    private let oldCacheTokenHandler: TokenHandler
    private let newCacheTokenHandler: TokenHandler
    
    init() {
        oldCacheTokenHandler = CacheTokenHandler(serviceName: "access-token")
        newCacheTokenHandler = CacheTokenHandler(serviceName: "access-token-v2")
    }
    
    func save(token: TokenUser) {
        newCacheTokenHandler.save(token: token)
        oldCacheTokenHandler.save(token: token)
    }
    
    func getToken() -> TokenUser? {
        let token = newCacheTokenHandler.getToken()
        if token == nil {
            let oldToken = oldCacheTokenHandler.getToken()
            if let oldToken = oldToken {
                save(token: oldToken)
                return oldToken
            }
        }
        return token
    }
    
    func removeToken() {
        newCacheTokenHandler.removeToken()
        oldCacheTokenHandler.removeToken()
    }
}
