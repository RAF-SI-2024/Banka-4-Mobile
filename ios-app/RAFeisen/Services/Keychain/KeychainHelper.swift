//
//  KeychainHelper.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/20/25.
//

import Foundation

final class KeychainHelper: SensitiveDataPersistency {
    
    static let standard = KeychainHelper()
    
    private init() {}
    
    func save(_ data: Data, service: String, account: String) {
        // Set the accessibility attribute to kSecAttrAccessibleAfterFirstUnlock
        let accessibility = kSecAttrAccessibleAfterFirstUnlock
        
        // Create query
        let query = [
            kSecValueData: data,
            kSecClass: kSecClassGenericPassword,
            kSecAttrService: service,
            kSecAttrAccount: account,
            kSecAttrAccessible: accessibility // Set the accessibility attribute in the query
        ] as CFDictionary
        
        // Add data in query to keychain
        var status = SecItemAdd(query, nil)
        
        if status == errSecDuplicateItem {
            // Item already exists, thus update it.
            let query = [
                kSecAttrService: service,
                kSecAttrAccount: account,
                kSecClass: kSecClassGenericPassword,
            ] as CFDictionary
            
            let attributesToUpdate = [kSecValueData: data, kSecAttrAccessible: accessibility] as CFDictionary
            
            // Update existing item
            status = SecItemUpdate(query, attributesToUpdate)
            
            if status != errSecSuccess {
                print("Error updating keychain item: \(status)")
            }
            
            var result: AnyObject?
            status = SecItemCopyMatching(query, &result)
            
            if status == errSecSuccess {
                if let item = result as? [String: AnyObject],
                   let currentAccessible = item[kSecAttrAccessible as String],
                    currentAccessible as! CFString != kSecAttrAccessibleAfterFirstUnlock {
                    // Item already exists but doesn't have correct accessibility level
                    delete(service: service, account: account)
                    
                    let query = [
                        kSecValueData: data,
                        kSecClass: kSecClassGenericPassword,
                        kSecAttrService: service,
                        kSecAttrAccount: account,
                        kSecAttrAccessible: accessibility
                    ] as CFDictionary
                    
                    let status = SecItemAdd(query, nil)
                    if status != errSecSuccess {
                        print("Error adding keychain item: \(status)")
                    }
                }
            }

            
            
        } else if status != errSecSuccess {
            print("Error adding keychain item: \(status)")
        }
    }
    
    func read(service: String, account: String) -> Data? {
        
        let query = [
            kSecAttrService: service,
            kSecAttrAccount: account,
            kSecClass: kSecClassGenericPassword,
            kSecReturnData: true
        ] as CFDictionary
        
        var result: AnyObject?
        SecItemCopyMatching(query, &result)
        
        return (result as? Data)
    }
    
    func delete(service: String, account: String) {
        
        let query = [
            kSecAttrService: service,
            kSecAttrAccount: account,
            kSecClass: kSecClassGenericPassword,
        ] as CFDictionary
        
        // Delete item from keychain
        SecItemDelete(query)
    }
}
