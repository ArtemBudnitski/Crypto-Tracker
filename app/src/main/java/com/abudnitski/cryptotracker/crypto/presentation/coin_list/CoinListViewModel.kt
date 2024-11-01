package com.abudnitski.cryptotracker.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abudnitski.cryptotracker.core.domain.util.onError
import com.abudnitski.cryptotracker.core.domain.util.onSuccess
import com.abudnitski.cryptotracker.crypto.domain.Coin
import com.abudnitski.cryptotracker.crypto.domain.CoinDataSource
import com.abudnitski.cryptotracker.crypto.presentation.coin_detail.DataPoint
import com.abudnitski.cryptotracker.crypto.presentation.model.CoinUi
import com.abudnitski.cryptotracker.crypto.presentation.model.toCoinUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class CoinListViewModel(
    private val coinDataSource: CoinDataSource
) : ViewModel() {
    private val _uiState = MutableStateFlow(CoinListState())
    val uiState = _uiState
        .onStart { loadCoins() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            CoinListState()
        )

    private val _events= Channel<CoinListEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: CoinListAction) {
        when (action) {
            is CoinListAction.OnCoinClick -> {
                selectCoin(action.coinUi)
            }
        }
    }

    private fun selectCoin(coinUi: CoinUi){
        _uiState.update { it.copy(
            selectedCoin = coinUi
        ) }

        viewModelScope.launch {
            coinDataSource
                .getCoinHistory(
                    coinId = coinUi.id,
                    start = ZonedDateTime.now().minusDays(5),
                    end = ZonedDateTime.now()
                )
                .onSuccess { history ->
                    val dataPointHistory = history
                        .sortedBy { it.dateTime }
                        .map{
                            DataPoint(
                                x = it.dateTime.hour.toFloat(),
                                y = it.priceUsd.toFloat(),
                                xLabel = DateTimeFormatter
                                    .ofPattern("ha\nM/d")
                                    .format(it.dateTime)
                            )
                        }

                    _uiState.update {
                        it.copy(
                            selectedCoin =  it.selectedCoin?.copy(
                                coinPriceHistory = dataPointHistory
                            )
                        )
                    }
                }
                .onError { error ->
                    _events.send(CoinListEvent.Error(error))

                }
        }
    }

    private fun loadCoins() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            coinDataSource
                .getCoins()
                .onSuccess { coins: List<Coin> ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            coins = coins.map { coin ->
                                coin.toCoinUi()
                            }
                        )
                    }
                }
                .onError { error ->
                    _uiState.update { it.copy(isLoading = false) }
                    _events.send(CoinListEvent.Error(error))
                }
        }
    }

}