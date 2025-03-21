//
//  ClientEndpoint.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/21/25.
//

import Foundation

enum ClientEndpoint: Endpoint {
    
    case getMe
    
    var basePath: String {
        "/client"
    }
    
    var path: String {
        switch self {
        case .getMe:
            return "/me"
        }
    }
}
