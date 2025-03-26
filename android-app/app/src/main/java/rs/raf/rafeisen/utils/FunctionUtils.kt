package rs.raf.rafeisen.utils

import java.text.DecimalFormat

fun formatAmount(amount: Double, currency: String): String {
    val df = DecimalFormat("#,###.00")
    return "${df.format(amount)} $currency"
}
