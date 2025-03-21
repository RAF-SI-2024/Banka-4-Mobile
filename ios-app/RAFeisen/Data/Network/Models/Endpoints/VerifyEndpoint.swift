//
//  VerifyEndpoint.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/21/25.
//

import Foundation

enum VerifyEndpoint: Endpoint {
    
    case regenerateAuthenticator
    case verifyNewAuthenticator
    
    var basePath: String {
        "/verify"
    }
    
    var path: String {
        switch self {
        case .regenerateAuthenticator:
            return "/regenerate-authenticator"
        case .verifyNewAuthenticator:
            return "/verify-new-authenticator"
        }
    }
}
