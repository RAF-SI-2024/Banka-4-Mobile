package rs.raf.rafeisen.security

import java.io.InputStream
import java.io.OutputStream

interface Encryption {
    fun encrypt(raw: String, outputStream: OutputStream)

    fun decrypt(inputStream: InputStream): String
}
