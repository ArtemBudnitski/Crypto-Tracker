package com.abudnitski.cryptotracker.crypto.presentation.coin_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abudnitski.cryptotracker.crypto.domain.Coin
import com.abudnitski.cryptotracker.crypto.presentation.model.CoinUi
import com.abudnitski.cryptotracker.crypto.presentation.model.toCoinUi
import com.abudnitski.cryptotracker.ui.theme.CryptoTrackerTheme

@Composable
fun CoinListItem(
    coinUi: CoinUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val containerColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    Card(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp, horizontal = 8.dp),
        colors = CardColors(
            containerColor = containerColor.copy(alpha = 0.03f),
            contentColor = Color.Black,
            disabledContainerColor = containerColor.copy(alpha = 0.03f),
            disabledContentColor = Color.Black
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = coinUi.iconRes),
                contentDescription = coinUi.name,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(85.dp)
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = coinUi.symbol,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = containerColor
                )
                Text(
                    text = coinUi.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    color = containerColor
                )
            }
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "$ ${coinUi.priceUsd.formatted}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = containerColor
                )
                Spacer(modifier = Modifier.height(8.dp))
                PriceChange(
                    change = coinUi.changePercent24Hr
                )
            }
        }
    }
}


@PreviewLightDark
@Composable
private fun CoinListItemPreview() {
    CryptoTrackerTheme {
        CoinListItem(
            coinUi = previewCoin,
            onClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}

internal val previewCoin = Coin(
    id = "bitcoin",
    rank = 1,
    name = "Bitcoin",
    symbol = "BTC",
    marketCapUsd = 1212452435.12,
    priceUsd = 621232.12,
    changePercent24Hr = -0.1
).toCoinUi()