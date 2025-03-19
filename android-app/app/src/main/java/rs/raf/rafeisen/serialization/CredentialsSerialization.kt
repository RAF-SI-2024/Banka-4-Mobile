package rs.raf.rafeisen.serialization

import androidx.datastore.core.Serializer
import kotlinx.serialization.json.Json
import rs.raf.rafeisen.security.Encryption
import rs.raf.rafeisen.security.readDecrypted
import rs.raf.rafeisen.security.writeEncrypted
import rs.raf.rafeisen.serialization.json.AppJson
import rs.raf.rafeisen.store.Credential
import java.io.InputStream
import java.io.OutputStream

class CredentialsSerialization(
    private val json: Json = AppJson,
    private val encryption: Encryption,
) : Serializer<Set<Credential>> {
    override val defaultValue: Set<Credential>
        get() = emptySet()

    override suspend fun readFrom(input: InputStream): Set<Credential> = input.readDecrypted(json, encryption)

    override suspend fun writeTo(
        t: Set<Credential>,
        output: OutputStream,
    ) = output.writeEncrypted(t, json, encryption)
}
