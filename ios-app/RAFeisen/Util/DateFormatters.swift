//
//  DateFormatters.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/21/25.
//

import Foundation

final class DateFormatters {
    
    static let shared = DateFormatters()
    
    lazy var apiResponse: DateFormatter = {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy-MM-dd"
        return dateFormatter
    }()
    
}
