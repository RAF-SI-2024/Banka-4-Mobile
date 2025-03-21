//
//  VerifyService.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/21/25.
//

import Foundation

protocol VerifyService {
    func regenerateAuthenticator() async -> Result<RegenerateAuthenticatorResponse, Error>
    func verifyNewAuthenticatorCode(code: String) async -> Result<Void, Error>
}
