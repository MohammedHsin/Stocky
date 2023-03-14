package com.example.stocky.presentation.company_listings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stocky.domain.repository.StockRepository
import com.example.stocky.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListingsViewModel @Inject constructor(
    private val repo: StockRepository
) : ViewModel() {

    private val _state = mutableStateOf(CompanyListingsState())
    val state: State<CompanyListingsState> = _state

    private var searchJob : Job? = null

    fun onEvent(event: CompanyListingsEvent) {
        when (event) {
            is CompanyListingsEvent.Refresh -> {
                getCompanyListings(fetchFromRemote = true)
            }
            is CompanyListingsEvent.onSearchQueryChange -> {
                _state.value = _state.value.copy(
                    searchQuery = event.query
                )
                searchJob?.cancel()
                searchJob = viewModelScope.launch {

                    delay(500L)
                    getCompanyListings()
                }
            }
        }
    }




    private fun getCompanyListings(
        query : String = _state.value.searchQuery.lowercase(),
        fetchFromRemote : Boolean =false
    ){
        viewModelScope.launch {
            repo.getCompanyListings(fetchFromRemote , query).collect{result->
                when(result){
                    is Resource.Success ->{
                        result.data?.let {listings->
                            _state.value = _state.value.copy(
                                companies = listings
                            )
                        }
                    }
                    is Resource.Loading ->{
                        _state.value = _state.value.copy(
                            isLoading = result.isLoading
                        )
                    }
                    is Resource.Error -> Unit
                }
            }
        }
    }

}