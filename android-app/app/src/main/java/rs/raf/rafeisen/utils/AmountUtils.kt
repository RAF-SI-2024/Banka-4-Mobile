package rs.raf.rafeisen.utils

import java.math.BigDecimal
import java.text.DecimalFormat

fun formatAmount(amount: BigDecimal, currency: String): String {
    val df = DecimalFormat("#,###.00")
    return "${df.format(amount)} $currency"
}
