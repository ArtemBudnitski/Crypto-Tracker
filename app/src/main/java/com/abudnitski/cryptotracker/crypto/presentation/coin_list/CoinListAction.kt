package com.abudnitski.cryptotracker.crypto.presentation.coin_list

import com.abudnitski.cryptotracker.crypto.presentation.model.CoinUi

sealed class CoinListAction {
    data class OnCoinClick(val coinUi: CoinUi) : CoinListAction()
    data object OnRetryClick : CoinListAction()
}