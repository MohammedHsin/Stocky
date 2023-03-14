package com.example.stocky.di

import com.example.stocky.data.csv.CSVParser
import com.example.stocky.data.csv.CompanyListingsParser
import com.example.stocky.data.repository.StockRepositoryImpl
import com.example.stocky.domain.model.CompanyListing
import com.example.stocky.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{

    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(
        companyListingsParser: CompanyListingsParser
    ): CSVParser<CompanyListing>


    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ) : StockRepository
}