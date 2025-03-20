package rs.raf.rafeisen.serialization

import androidx.datastore.core.Serializer
import kotlinx.serialization.json.Json
import rs.raf.rafeisen.security.Encryption
import rs.raf.rafeisen.security.readDecrypted
import rs.raf.rafeisen.security.writeEncrypted
import rs.raf.rafeisen.serialization.json.AppJson
import rs.raf.rafeisen.model.TotpSecret
import java.io.InputStream
import java.io.OutputStream

class TotpSecretsSerialization(
    private val json: Json = AppJson,
    private val encryption: Encryption
) : Serializer<List<TotpSecret>> {
    override val defaultValue: List<TotpSecret>
        get() = emptyList()

    override suspend fun readFrom(input: InputStream): List<TotpSecret> =
        input.readDecrypted(json, encryption)

    override suspend fun writeTo(t: List<TotpSecret>, output: OutputStream) =
        output.writeEncrypted(t, json, encryption)
}
