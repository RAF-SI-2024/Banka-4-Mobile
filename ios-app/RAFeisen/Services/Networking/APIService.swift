//
//  APIService.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/20/25.
//

import Foundation

protocol TokenRefreshDelegate: AnyObject {
    func shouldRefreshToken()
}

class APIService {
    static let shared = APIService(baseUrlString: "http://localhost:8080")
    
    weak var tokenRefreshDelegate: TokenRefreshDelegate?

    private let baseUrlString: String
    
    private var token: Token?
    private let tokenHandler: TokenHandler = CacheMigrationTokenHandler()
    
    init(baseUrlString: String) {
        self.baseUrlString = baseUrlString
    }
    
    func perform<T: Decodable>(request: APIRequest) async -> Result<T, Error> {
        guard var urlRequest = request.urlRequest(baseURLString: baseUrlString) else {
            return .failure(NetworkingError.malformedURL)
        }
        
        let sessionConfiguration = URLSessionConfiguration.default
        sessionConfiguration.httpAdditionalHeaders = sessionConfiguration.httpAdditionalHeaders ?? [:]
        if let token = getToken() {
            sessionConfiguration.httpAdditionalHeaders?["Authorization"] = "Bearer \(token)"
        }
        let urlSession = URLSession(configuration: sessionConfiguration)
        
        
        do {
            let (data, response) = try await urlSession.data(for: urlRequest)
            
            guard let httpResponse = response as? HTTPURLResponse else {
                return .failure(NSError(domain: "Invalid response", code: 1000, userInfo: nil))
            }
            guard 200..<300 ~= httpResponse.statusCode else {
                debugPrint(httpResponse)
                return .failure(NSError(domain: "Invalid response not 200", code: httpResponse.statusCode, userInfo: nil))
            }
            
            do {
                let items = try decoder.decode(T.self, from: data)
                return .success(items)
            } catch(let error) {
                return .failure(NetworkingError.decodingError(error))
            }
            
        } catch {
            return .failure(error)
        }
        
    }
    
    
    private lazy var decoder: JSONDecoder = {
        let decoder = JSONDecoder()
        let formatter = DateFormatters.shared.apiResponse
        decoder.dateDecodingStrategy = .formatted(formatter)
        return decoder
    }()
    
    private func getToken() -> String? {
        if let token = token {
            return token
        } else if let token = tokenHandler.getToken()?.token {
            return token
        }
        return nil
    }

}
