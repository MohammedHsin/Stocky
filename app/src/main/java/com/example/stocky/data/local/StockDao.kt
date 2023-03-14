package com.example.stocky.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyListings(
        companyListingEntities: List<CompanyListingEntity>
    )


    @Query("delete from CompanyListingEntity")
    suspend fun clearCompanyListings()


    @Query("""
        SELECT *
        FROM CompanyListingEntity
        WHERE LOWER(name) LIKE "%" || LOWER(:query) || "%" OR
            UPPER(:query) == symbol
        """)
    suspend fun searchCompany(query: String) : List<CompanyListingEntity>
}