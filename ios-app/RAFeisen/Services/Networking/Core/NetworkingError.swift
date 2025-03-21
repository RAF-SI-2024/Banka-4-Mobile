//
//  NetworkingError.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/20/25.
//

enum NetworkingError: Error {
    case decodingError(Error)
    case malformedURL
    case missingData
    
    // Status code
    case unauthorized
    case serverError
    case unhandledResponseCode
    case error(Error)

    static func parseStatusCode(_ code: Int) -> NetworkingError {
        switch code {
        case 401: return .unauthorized
        case 500...: return .serverError
        default: return .unhandledResponseCode
        }
    }
}

