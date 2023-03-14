package com.example.stocky.domain.repository

import com.example.stocky.domain.model.CompanyListing
import com.example.stocky.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote : Boolean,
        query : String
    ) : Flow<Resource<List<CompanyListing>>>
}