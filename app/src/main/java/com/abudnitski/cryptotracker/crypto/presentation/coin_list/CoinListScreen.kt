package com.abudnitski.cryptotracker.crypto.presentation.coin_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.abudnitski.cryptotracker.R
import com.abudnitski.cryptotracker.crypto.presentation.coin_list.components.CoinListItem
import com.abudnitski.cryptotracker.crypto.presentation.coin_list.components.previewCoin
import com.abudnitski.cryptotracker.ui.theme.CryptoTrackerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinListScreen(
    coinListState: CoinListState,
    queryText: String,
    onQueryChange: (String) -> Unit,
    onAction: (CoinListAction) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    if (coinListState.isLoading || coinListState.isDataInitError) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (coinListState.isDataInitError) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                ) {
                    Text(
                        text = stringResource(R.string.something_went_wrong),
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { onAction(CoinListAction.OnRetryClick) },
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                    ) {
                        Text(stringResource(R.string.try_again))
                    }
                }
            } else {
                CircularProgressIndicator()
            }
        }
    } else {
        Scaffold(
            modifier = modifier
                .fillMaxSize(),
            topBar = {
                SearchBar(
                    expanded = false,
                    onExpandedChange = {},
                    inputField = {
                        SearchBarDefaults.InputField(
                            query = queryText,
                            onQueryChange = { onQueryChange(it) },
                            onSearch = { onSearch(it) },
                            expanded = false,
                            onExpandedChange = { },
                            placeholder = {
                                Text(
                                    text = stringResource(R.string.search_coins),
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                                )
                            }
                        )
                    },
                    content = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                )
            }
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .padding(padding),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(coinListState.filteredCoins) { coinUi ->
                    CoinListItem(
                        coinUi = coinUi,
                        onClick = {
                            onAction(CoinListAction.OnCoinClick(coinUi))
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun CoinListScreenPreview() {
    CryptoTrackerTheme {
        CoinListScreen(
            coinListState = CoinListState(
                coins = (1..100).map {
                    previewCoin.copy(id = it.toString())
                }
            ),
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            onAction = {},
            queryText = "",
            onQueryChange = {},
            onSearch = {}
        )
    }
}
