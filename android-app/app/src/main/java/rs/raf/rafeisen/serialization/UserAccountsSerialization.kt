package rs.raf.rafeisen.serialization

import androidx.datastore.core.Serializer
import kotlinx.serialization.json.Json
import rs.raf.rafeisen.security.Encryption
import rs.raf.rafeisen.security.readDecrypted
import rs.raf.rafeisen.security.writeEncrypted
import rs.raf.rafeisen.serialization.json.AppJson
import rs.raf.rafeisen.store.UserAccount
import java.io.InputStream
import java.io.OutputStream

class UserAccountsSerialization(
    private val json: Json = AppJson,
    private val encryption: Encryption,
) : Serializer<List<UserAccount>> {
    override val defaultValue: List<UserAccount>
        get() = emptyList()

    override suspend fun readFrom(input: InputStream): List<UserAccount> = input.readDecrypted(json, encryption)

    override suspend fun writeTo(
        t: List<UserAccount>,
        output: OutputStream,
    ) = output.writeEncrypted(t, json, encryption)
}
