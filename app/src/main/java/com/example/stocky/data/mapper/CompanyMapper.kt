package com.example.stocky.data.mapper

import com.example.stocky.data.local.CompanyListingEntity
import com.example.stocky.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing() : CompanyListing{
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListing.toCompanyListingEntity() : CompanyListingEntity{
    return CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}