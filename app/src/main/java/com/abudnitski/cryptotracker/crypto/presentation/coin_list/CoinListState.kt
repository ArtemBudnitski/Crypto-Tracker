package com.abudnitski.cryptotracker.crypto.presentation.coin_list

import androidx.compose.runtime.Immutable
import com.abudnitski.cryptotracker.crypto.presentation.model.CoinUi

@Immutable
data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<CoinUi> = emptyList(),
    val filteredCoins: List<CoinUi> = emptyList(),
    val selectedCoin: CoinUi? = null,
    val searchText: String = "",
    val isDataInitError: Boolean = false
)
