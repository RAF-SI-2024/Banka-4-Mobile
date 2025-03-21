//
//  AuthEndpoint.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/21/25.
//

import Foundation

enum AuthEndpoint: Endpoint {
    
    case clientLogin
    
    var basePath: String {
        "/auth"
    }
    
    var path: String {
        switch self {
        case .clientLogin:
            return "/client/login"
        }
    }
}
