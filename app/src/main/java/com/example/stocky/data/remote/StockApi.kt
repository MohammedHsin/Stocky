package com.example.stocky.data.remote
import okhttp3.ResponseBody
import retrofit2.http.Query
import retrofit2.http.GET

interface StockApi {

    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(
        @Query("apikey") apiKey :String
    ) : ResponseBody

    companion object{
        const val API_KEY = "L9V300FUIGUAWS3A"
        const val BASE_URL = "https://alphavantage.co"
    }

}