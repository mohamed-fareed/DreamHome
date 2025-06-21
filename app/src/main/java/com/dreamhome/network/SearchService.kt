package com.dreamhome.network

import com.dreamhome.data.entities.SearchResult
import retrofit2.http.GET

interface SearchService {
    @GET("/search")
    suspend fun getSearchResults(): SearchResult
}