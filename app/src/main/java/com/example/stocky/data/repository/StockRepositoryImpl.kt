package com.example.stocky.data.repository

import coil.network.HttpException
import com.example.stocky.data.csv.CSVParser
import com.example.stocky.data.csv.CompanyListingsParser
import com.example.stocky.data.local.StockDatabase
import com.example.stocky.data.mapper.toCompanyListing
import com.example.stocky.data.mapper.toCompanyListingEntity
import com.example.stocky.data.remote.StockApi
import com.example.stocky.data.remote.StockApi.Companion.API_KEY
import com.example.stocky.domain.model.CompanyListing
import com.example.stocky.domain.repository.StockRepository
import com.example.stocky.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Singleton
import javax.inject.Inject

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api : StockApi,
    private val db : StockDatabase,
    private val companyListingsParser: CSVParser<CompanyListing>
): StockRepository{

    private val dao = db.dao

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>{
        return flow {
            emit(Resource.Loading(true))
            val localListings = dao.searchCompany(query).map { it.toCompanyListing() }
            emit(Resource.Success(localListings))

            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote

            if (shouldJustLoadFromCache){
                emit(Resource.Loading(false))
            }

            val remoteListings = try {
                val response = api.getListings(API_KEY)
                response.byteStream()
                companyListingsParser.parse(response.byteStream())

            }catch (e : IOException){
                e.printStackTrace()
                emit(Resource.Error("couldn"))
                null

            }catch (e : HttpException){
                e.printStackTrace()
                emit(Resource.Error("couldn"))
                null
            }


            remoteListings?.let {listings->
                dao.clearCompanyListings()
                dao.insertCompanyListings(
                    listings.map { it.toCompanyListingEntity() }
                )
                emit(Resource.Success(
                    dao
                        .searchCompany("")
                        .map { it.toCompanyListing() }

                ))

                emit(Resource.Loading(false))
            }

        }
    }
}