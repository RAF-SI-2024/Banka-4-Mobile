package rs.raf.rafeisen.screen.landing

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import rs.raf.rafeisen.screen.landing.mock.mockCards
import rs.raf.rafeisen.screen.landing.model.CardDto
import rs.raf.rafeisen.R
import rs.raf.rafeisen.ui.theme.PurpleStart
import rs.raf.rafeisen.ui.theme.PurpleEnd
import rs.raf.rafeisen.ui.theme.BlueStart
import rs.raf.rafeisen.ui.theme.BlueEnd
import rs.raf.rafeisen.ui.theme.OrangeStart
import rs.raf.rafeisen.ui.theme.OrangeEnd
import rs.raf.rafeisen.ui.theme.GreenStart
import rs.raf.rafeisen.ui.theme.GreenEnd

fun getCardGradient(cardName: String): Brush {
    return when (cardName) {
        "Visa" -> Brush.horizontalGradient(
            colors = listOf(PurpleStart, PurpleEnd)
        )
        "MasterCard" -> Brush.horizontalGradient(
            colors = listOf(BlueStart, BlueEnd)
        )
        "DinaCard" -> Brush.horizontalGradient(
            colors = listOf(OrangeStart, OrangeEnd)
        )
        "American Express" -> Brush.horizontalGradient(
            colors = listOf(GreenStart, GreenEnd)
        )
        else -> Brush.horizontalGradient(
            colors = listOf(Color.Gray, Color.LightGray)
        )
    }
}

fun getCardImageResource(cardName: String): Int {
    return when (cardName) {
        "Visa" -> R.drawable.ic_visa
        "MasterCard" -> R.drawable.ic_mastercard
        "DinaCard" -> R.drawable.ic_dinacard
        "American Express" -> R.drawable.ic_amex
        else -> R.drawable.ic_default_card
    }
}

@Composable
fun CardSection(cards: List<CardDto>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        itemsIndexed(cards) { index, card ->
            CardItem(card = card, isLastItem = index == cards.lastIndex)
        }
    }
}

@Composable
fun CardItem(card: CardDto, isLastItem: Boolean) {
    val gradient = getCardGradient(card.cardName)
    val imageRes = getCardImageResource(card.cardName)

    Box(
        modifier = Modifier
            .padding(start = 16.dp, end = if (isLastItem) 16.dp else 0.dp)
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(25.dp))
                .background(gradient)
                .width(250.dp)
                .height(160.dp)
                .clickable {  }
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = card.cardName,
                contentScale = ContentScale.Fit,
                modifier = Modifier.width(60.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = card.cardName,
                color = Color.White,
                fontSize = 17.sp,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "**** **** **** ${card.cardNumber.takeLast(4)}",
                color = Color.White,
                fontSize = 22.sp,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCardSection() {
    CardSection(cards = mockCards)
}
