//
//  VerifyAPIService.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/21/25.
//

import Foundation

final class VerifyAPIService: VerifyService {
    private let apiService = APIService.shared
    
    func regenerateAuthenticator() async -> Result<RegenerateAuthenticatorResponse, any Error> {
        let result: Result<RegenerateAuthenticatorResponse, Error> = await apiService.perform(request: RegenerateAuthenticatorRequest())
        switch result {
        case .success(let regenerateAuthenticatorResponse):
            return .success(regenerateAuthenticatorResponse)
        case .failure(let error):
            return .failure(error)
        }
    }
    
    func verifyNewAuthenticatorCode(code: String) async -> Result<Void, Error> {
        let result: Result<Int, Error> = await apiService.perform(request: VerifyNewAuthenticatorCodeRequest(code: code))
        switch result {
        case .success(let success):
            return .success(())
        case .failure(let error):
            return .failure(error)
        }
    }

    
}

