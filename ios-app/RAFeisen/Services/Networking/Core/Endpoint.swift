//
//  Endpoint.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/20/25.
//

protocol Endpoint {
    var basePath: String { get }
    var path: String { get }
    var fullPath: String { get }
}

extension Endpoint {
    var fullPath: String {
        basePath + path
    }
}

