package com.example.stocky.presentation.company_listings

sealed class CompanyListingsEvent{
    object Refresh : CompanyListingsEvent()
    data class onSearchQueryChange(val query : String) : CompanyListingsEvent()
}
