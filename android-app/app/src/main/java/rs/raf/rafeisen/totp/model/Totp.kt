package rs.raf.rafeisen.totp.model

import androidx.room.Entity

@Entity(primaryKeys = ["userId", "secret"])
data class Totp(val userId: String, val issuer: String, val secret: String)
