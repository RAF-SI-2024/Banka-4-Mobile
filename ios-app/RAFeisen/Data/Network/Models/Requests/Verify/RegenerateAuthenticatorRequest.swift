//
//  RegenerateAuthenticatorRequest.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/21/25.
//

import Foundation

struct RegenerateAuthenticatorRequest: APIRequest {
    
    let endpoint: Endpoint = VerifyEndpoint.regenerateAuthenticator
    let method = APIRequestMethod.get
    let body: Codable? = nil
    
    init() {
        
    }
}
