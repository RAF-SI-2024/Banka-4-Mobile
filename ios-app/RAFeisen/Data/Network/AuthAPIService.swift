//
//  AuthAPIService.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/21/25.
//

import Foundation

final class AuthAPIService: AuthService {
    private let apiService = APIService.shared
    
    func clientLogin(params: ClientLoginRequestParams) async -> Result<TokenPair, Error> {
        let result: Result<TokenPairDTO, Error> = await apiService.perform(request: ClientLoginRequest(params: params))
        
        switch result {
        case .success(let response):
            return .success(response.toTokenPair())
        case .failure(let error):
            return  .failure(error)
        }
    }
    
}
