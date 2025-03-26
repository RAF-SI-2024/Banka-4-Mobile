package rs.raf.rafeisen.screen.landing.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.PaddingValues
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
import rs.raf.rafeisen.screen.landing.data.CardDto
import rs.raf.rafeisen.R
import rs.raf.rafeisen.model.CardName
import rs.raf.rafeisen.ui.theme.PurpleStart
import rs.raf.rafeisen.ui.theme.PurpleEnd
import rs.raf.rafeisen.ui.theme.BlueStart
import rs.raf.rafeisen.ui.theme.BlueEnd
import rs.raf.rafeisen.ui.theme.OrangeStart
import rs.raf.rafeisen.ui.theme.OrangeEnd
import rs.raf.rafeisen.ui.theme.GreenStart
import rs.raf.rafeisen.ui.theme.GreenEnd


fun getCardGradient(cardName: CardName): Brush {
    return when (cardName) {
        CardName.Visa  -> Brush.horizontalGradient(
            colors = listOf(PurpleStart, PurpleEnd)
        )
        CardName.MasterCard -> Brush.horizontalGradient(
            colors = listOf(BlueStart, BlueEnd)
        )
        CardName.DinaCard -> Brush.horizontalGradient(
            colors = listOf(OrangeStart, OrangeEnd)
        )
        CardName.AmericanExpress -> Brush.horizontalGradient(
            colors = listOf(GreenStart, GreenEnd)
        )
        else -> Brush.horizontalGradient(
            colors = listOf(Color.Gray, Color.LightGray)
        )
    }
}

fun getCardImageResource(cardName: CardName): Int {
    return when (cardName) {
        CardName.Visa -> R.drawable.ic_visa
        CardName.MasterCard -> R.drawable.master_card
        CardName.DinaCard -> R.drawable.ic_dinacard
        CardName.AmericanExpress -> R.drawable.ic_amex
        else -> R.drawable.ic_default_card
    }
}

@Composable
fun CardSection(cards: List<CardDto>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = cards,
            key = { it.cardNumber }
        ) { card ->
            CardItem(card = card)
        }
    }
}

@Composable
fun CardItem(card: CardDto) {
    val gradient = getCardGradient(card.cardName)
    val imageRes = getCardImageResource(card.cardName)

    Box {
        Column(
            modifier = Modifier
                .clip(MaterialTheme.shapes.large)
                .background(gradient)
                .width(250.dp)
                .height(160.dp)
                .clickable { }
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = card.cardName.toString(),
                contentScale = ContentScale.Fit,
                modifier = Modifier.width(60.dp)
            )
            Column {
                Text(
                    text = card.cardName.toString(),
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
}


@Preview(showBackground = true)
@Composable
fun PreviewCardSection() {
    CardSection(cards = mockCards)
}
