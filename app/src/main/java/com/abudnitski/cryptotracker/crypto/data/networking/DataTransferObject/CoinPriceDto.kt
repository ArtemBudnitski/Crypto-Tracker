package com.abudnitski.cryptotracker.crypto.data.networking.DataTransferObject

import kotlinx.serialization.Serializable

@Serializable
data class CoinPriceDto(
    val priceUsd: Double,
    val time: Long
)
