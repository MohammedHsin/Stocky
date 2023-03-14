package com.example.stocky.presentation.company_listings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination(start = true)
fun CompanyListingsScreen(
    navigator: DestinationsNavigator, viewModel: CompanyListingsViewModel = hiltViewModel()
) {

    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = viewModel.state.value.isRefreshing
    )

    val state = viewModel.state

    Column(Modifier.fillMaxSize()) {

        OutlinedTextField(
            value = state.value.searchQuery,
            onValueChange = {
                viewModel.onEvent(CompanyListingsEvent.onSearchQueryChange(it))
            },
            Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            placeholder = { Text(text = "Search...") },
            maxLines = 1,
            singleLine = true
        )

        SwipeRefresh(state = swipeRefreshState,
            onRefresh = { viewModel.onEvent(CompanyListingsEvent.Refresh) }
        ) {

            LazyColumn(Modifier.fillMaxSize()){
                items(state.value.companies){company->
                    CompanyItem(company = company , modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            // todo : navigate to detail screen
                        }
                        .padding(16.dp)
                    )
                }
            }

        }

    }
}














