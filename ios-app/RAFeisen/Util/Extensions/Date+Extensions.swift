//
//  Date+Extensions.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/20/25.
//

import Foundation

extension Date {
  static func random() -> Date {
    let randomTime = TimeInterval(Int32.random(in: 0...Int32.max))
    return Date(timeIntervalSince1970: randomTime)
  }
}
