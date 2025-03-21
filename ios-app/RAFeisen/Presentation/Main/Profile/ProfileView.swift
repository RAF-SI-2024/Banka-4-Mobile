//
//  ProfileView.swift
//  RAFeisen
//
//  Created by Luka Korica on 3/21/25.
//

import SwiftUI

struct ProfileView: View {
    @Binding var client: Client?
    var logoutAction: () -> Void
    
    private var initials: String {
        let first = client?.firstName?.first ?? "?"
        let last = client?.lastName?.first ?? "?"
        return "\(first)\(last)"
    }
    
    private var formattedDateOfBirth: String {
        client?.dateOfBirth?.formatted(date: .abbreviated, time: .omitted) ?? "Not provided"
    }
    
    var body: some View {
        NavigationStack {
            ScrollView {
                VStack(spacing: 20) {
                    // Profile Header
                    ZStack {
                        Circle()
                            .fill(genderColor)
                            .frame(width: 100, height: 100)
                            .shadow(radius: 5)
                        
                        Text(initials)
                            .font(.system(size: 40, weight: .bold))
                            .foregroundColor(.white)
                    }
                    .padding(.top, 30)
                    
                    // Personal Information
                    VStack(alignment: .leading, spacing: 15) {
                        SectionHeader(title: "Personal Information")
                        
                        InfoRow(icon: "person.fill", label: "Name", value: fullName)
                        InfoRow(icon: "envelope.fill", label: "Email", value: client?.email ?? "Not provided")
                        InfoRow(icon: "phone.fill", label: "Phone", value: client?.phone ?? "Not provided")
                        InfoRow(icon: "house.fill", label: "Address", value: client?.address ?? "Not provided")
                        InfoRow(icon: "calendar", label: "Date of Birth", value: formattedDateOfBirth)
                        InfoRow(icon: "person.2.square.stack.fill", label: "Gender", value: client?.gender.description ?? "No gender")
                    }
                    .padding()
                    .background(Color(.systemBackground))
                    .cornerRadius(15)
                    .shadow(color: Color.black.opacity(0.1), radius: 5, x: 0, y: 2)
                    
                    // Security Section
                    VStack(alignment: .leading, spacing: 15) {
                        SectionHeader(title: "Security Settings")
                        
                        HStack {
                            Image(systemName: "lock.shield.fill")
                                .foregroundColor(.blue)
                            Text("2FA Enabled")
                            Spacer()
                            Text((client?.has2FA ?? false) ? "Yes" : "No")
                                .foregroundColor((client?.has2FA ?? false) ? .green : .red)
                        }
                        
                        if (client?.has2FA ?? false) {
                            HStack {
                                Image(systemName: "key.fill")
                                    .foregroundColor(.purple)
                                Text("TOTP Codes Configured")
                                Spacer()
                                Text("\(client?.totpSecret?.count ?? 0)")
                                    .foregroundColor(.secondary)
                            }
                        }
                    }
                    .padding()
                    .background(Color(.systemBackground))
                    .cornerRadius(15)
                    .shadow(color: Color.black.opacity(0.1), radius: 5, x: 0, y: 2)
                    
                    // Logout Button
                    Button(action: logoutAction) {
                        HStack {
                            Spacer()
                            Text("profile.logout")
                                .font(.headline)
                                .foregroundColor(.white)
                            Spacer()
                        }
                        .padding()
                        .background(Color.black)
                        .cornerRadius(10)
                    }
                    .padding(.top, 20)
                }
                .scrollBounceBehavior(.basedOnSize)
                .padding(.horizontal)
            }
            .navigationTitle("navigation.profileview.title")
        }
    }
    
    private var fullName: String {
        [client?.firstName, client?.lastName].compactMap { $0 }.joined(separator: " ")
    }
    
    private var genderColor: Color {
        switch client?.gender {
        case .male: return .blue
        case .female: return .pink
        default: return .purple
        }
    }
}

// MARK: - Helper Views
struct SectionHeader: View {
    let title: String
    
    var body: some View {
        Text(title)
            .font(.headline)
            .foregroundColor(.secondary)
            .padding(.bottom, 5)
    }
}

struct InfoRow: View {
    let icon: String
    let label: String
    let value: String
    
    var body: some View {
        HStack {
            Image(systemName: icon)
                .foregroundColor(.gray)
                .frame(width: 25)
            Text(label)
                .foregroundColor(.primary)
            Spacer()
            Text(value)
                .foregroundColor(.secondary)
                .multilineTextAlignment(.trailing)
        }
    }
}

// MARK: - Preview
#Preview {
    let context = PersistenceController.preview.container.viewContext
    let client = Client(context: context)
    client.firstName = "John"
    client.lastName = "Doe"
    client.email = "john.doe@example.com"
    client.phone = "+1 555 123 4567"
    client.address = "123 Main St, New York"
    client.dateOfBirth = Calendar.current.date(byAdding: .year, value: -30, to: Date())
    client.gender = .male
    client.has2FA = true
    
    return ProfileView(client: .constant(client), logoutAction: {})
        .environment(\.managedObjectContext, context)
}
