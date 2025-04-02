package rs.raf.rafeisen.utils

fun String.removePrefixIgnoreCase(s: String): String =
    if (startsWith(s, ignoreCase = true)) {
        substring(s.length)
    } else {
        this
    }
