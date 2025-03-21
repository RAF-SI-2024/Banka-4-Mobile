//
//  RegenerateAuthenticatorResponse.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/21/25.
//

import Foundation

struct RegenerateAuthenticatorResponse: Decodable {
    let url: String
    let tokenSecret: String
}
