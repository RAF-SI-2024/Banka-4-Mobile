//
//  ClientGetMeRequest.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/21/25.
//

import Foundation

struct ClientGetMeRequest: APIRequest {
    
    let endpoint: Endpoint = ClientEndpoint.getMe
    let method = APIRequestMethod.get
    let body: Codable? = nil
    
    init() {
        
    }
    
}
