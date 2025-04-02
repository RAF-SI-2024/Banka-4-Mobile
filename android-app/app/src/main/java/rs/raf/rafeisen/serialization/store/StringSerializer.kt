package rs.raf.rafeisen.serialization.store

import androidx.datastore.core.Serializer
import java.io.InputStream
import java.io.OutputStream
import rs.raf.rafeisen.security.Encryption

class StringSerializer(private val encryption: Encryption? = null) : Serializer<String> {
    override val defaultValue: String
        get() = ""

    override suspend fun writeTo(t: String, output: OutputStream) {
        encryption?.encrypt(t, output) ?: output.use {
            it.write(t.toByteArray())
        }
    }

    override suspend fun readFrom(input: InputStream): String = encryption?.decrypt(input) ?: String(input.readBytes())
}
