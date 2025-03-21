//
//  AuthService.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/21/25.
//

import Foundation

protocol AuthService {
    func clientLogin(params: ClientLoginRequestParams) async -> Result<TokenPair, Error>
}
