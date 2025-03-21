//
//  ClientService.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/21/25.
//

import Foundation

protocol ClientService {
    func getMe() async -> Result<Client, Error>
}
