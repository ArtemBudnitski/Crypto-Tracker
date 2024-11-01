package com.abudnitski.cryptotracker.crypto.presentation.coin_list

import com.abudnitski.cryptotracker.core.domain.util.NetworkError

sealed interface CoinListEvent {
    data class Error(val error: NetworkError): CoinListEvent
}