//
//  ClientLoginRequest.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/21/25.
//

import Foundation

struct ClientLoginRequestParams: Encodable {
    let email: String
    let password: String
}

struct ClientLoginRequest: APIRequest {
    
    let endpoint: Endpoint = AuthEndpoint.clientLogin
    let method = APIRequestMethod.post
    let body: Codable?
    
    init(params: ClientLoginRequestParams) {
        let encoder = JSONEncoder()
        body = try? encoder.encode(params)
        
    }
}
