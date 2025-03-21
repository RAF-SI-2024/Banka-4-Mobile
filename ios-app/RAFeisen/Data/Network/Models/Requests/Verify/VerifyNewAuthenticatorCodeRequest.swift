//
//  VerifyNewAuthenticatorCodeRequest.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/21/25.
//

import Foundation

struct VerifyNewAuthenticatorCodeRequest: APIRequest {
    
    let endpoint: Endpoint = VerifyEndpoint.verifyNewAuthenticator
    let method = APIRequestMethod.post
    let body: Codable?
    
    init(code: String) {
        let encoder = JSONEncoder()
        body = try? encoder.encode(["content": code])

    }
}
