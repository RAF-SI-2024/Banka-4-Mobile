//
//  ClientAPIService.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/21/25.
//

import Foundation

final class ClientAPIService: ClientService {
    private let apiService = APIService.shared
    
    func getMe() async -> Result<Client, Error> {
        let result: Result<ClientDTO, Error> = await apiService.perform(request: ClientGetMeRequest())
        switch result {
        case .success(let client):
            return .success(client.toClient(in: PersistenceController.shared.container.viewContext))
        case .failure(let error):
            return .failure(error)
        }
    }
    
}
