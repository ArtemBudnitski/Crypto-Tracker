package com.abudnitski.cryptotracker.crypto.data.networking.DataTransferObject

import kotlinx.serialization.Serializable

@Serializable
data class CoinHistoryDto(
    val data: List<CoinPriceDto>
)
