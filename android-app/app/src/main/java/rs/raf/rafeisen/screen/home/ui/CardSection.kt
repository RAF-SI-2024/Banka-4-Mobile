package rs.raf.rafeisen.screen.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import rs.raf.rafeisen.R
import rs.raf.rafeisen.domain.card.model.CardName
import rs.raf.rafeisen.domain.card.model.CardType
import rs.raf.rafeisen.screen.home.model.CardUIModel
import rs.raf.rafeisen.ui.theme.BlueEnd
import rs.raf.rafeisen.ui.theme.BlueStart
import rs.raf.rafeisen.ui.theme.GreenEnd
import rs.raf.rafeisen.ui.theme.GreenStart
import rs.raf.rafeisen.ui.theme.OrangeEnd
import rs.raf.rafeisen.ui.theme.OrangeStart
import rs.raf.rafeisen.ui.theme.PurpleEnd
import rs.raf.rafeisen.ui.theme.PurpleStart

private const val NUMBER_OF_DIGITS_SHOWN = 4

@Composable
fun CardSection(cards: List<CardUIModel>, modifier: Modifier = Modifier) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(items = cards, key = { it.cardNumber }) { card ->
            CardItem(card = card)
        }
    }
}

@Composable
fun CardItem(card: CardUIModel, modifier: Modifier = Modifier) {
    val gradient = getCardGradient(card.cardName)
    val imageRes = getCardImageResource(card.cardName)

    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .clip(MaterialTheme.shapes.large)
                .background(gradient)
                .width(250.dp)
                .height(160.dp)
                .clickable { }
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = card.cardName.toString(),
                contentScale = ContentScale.Fit,
                modifier = Modifier.width(60.dp),
            )
            Column {
                Text(
                    text = card.cardName.toString(),
                    color = Color.White,
                    fontSize = 12.sp,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "**** **** **** ${card.cardNumber.takeLast(NUMBER_OF_DIGITS_SHOWN)}",
                    color = Color.White,
                    fontSize = 22.sp,
                    style = MaterialTheme.typography.headlineSmall,
                )
                Text(
                    text = "Expires: ${card.expirationDate}",
                    color = Color.White,
                    fontSize = 12.sp,
                    style = MaterialTheme.typography.labelMedium,
                )
            }
        }
    }
}

private fun getCardGradient(cardName: CardName): Brush =
    when (cardName) {
        CardName.Visa -> Brush.horizontalGradient(colors = listOf(PurpleStart, PurpleEnd))
        CardName.MasterCard -> Brush.horizontalGradient(colors = listOf(BlueStart, BlueEnd))
        CardName.DinaCard -> Brush.horizontalGradient(colors = listOf(OrangeStart, OrangeEnd))
        CardName.AmericanExpress -> Brush.horizontalGradient(colors = listOf(GreenStart, GreenEnd))
    }

private fun getCardImageResource(cardName: CardName): Int =
    when (cardName) {
        CardName.Visa -> R.drawable.ic_visa
        CardName.MasterCard -> R.drawable.master_card
        CardName.DinaCard -> R.drawable.ic_dinacard
        CardName.AmericanExpress -> R.drawable.ic_amex
    }

@Preview(showBackground = true)
@Composable
private fun PreviewCardSection() {
    CardSection(
        cards = listOf(
            CardUIModel(
                cardNumber = "1234567890123456",
                cardName = CardName.Visa,
                cardType = CardType.Debit,
                expirationDate = LocalDate.parse("2025-12-31"),
                clientFullName = "Petar Petrović",
            ),
        ),
    )
}
