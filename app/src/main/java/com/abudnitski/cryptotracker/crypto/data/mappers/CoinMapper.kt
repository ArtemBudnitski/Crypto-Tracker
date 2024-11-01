package com.abudnitski.cryptotracker.crypto.data.mappers

import com.abudnitski.cryptotracker.crypto.data.networking.DataTransferObject.CoinDto
import com.abudnitski.cryptotracker.crypto.data.networking.DataTransferObject.CoinPriceDto
import com.abudnitski.cryptotracker.crypto.domain.Coin
import com.abudnitski.cryptotracker.crypto.domain.CoinPrice
import java.time.Instant
import java.time.ZoneId

fun CoinDto.toCoin(): Coin {
    return Coin(
        id = id,
        rank = rank,
        name = name,
        symbol = symbol,
        marketCapUsd = marketCapUsd,
        priceUsd = priceUsd,
        changePercent24Hr = changePercent24Hr,
    )
}

fun CoinPriceDto.toCoinPrice(): CoinPrice {
    return CoinPrice(
        priceUsd = priceUsd,
        dateTime = Instant
            .ofEpochMilli(time)
            .atZone(ZoneId.systemDefault())
    )
}
