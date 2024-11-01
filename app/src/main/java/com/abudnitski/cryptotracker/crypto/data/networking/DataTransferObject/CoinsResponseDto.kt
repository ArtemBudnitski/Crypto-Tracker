package com.abudnitski.cryptotracker.crypto.data.networking.DataTransferObject

import kotlinx.serialization.Serializable

@Serializable
data class CoinsResponseDto(
    val data: List<CoinDto>
)
