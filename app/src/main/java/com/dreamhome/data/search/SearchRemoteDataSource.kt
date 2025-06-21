package com.dreamhome.data.search

import com.dreamhome.data.entities.SearchResult
import com.dreamhome.network.SearchService

internal interface SearchRemoteDataSource {
    suspend fun getSearchResults(): SearchResult
}

internal class SearchRemoteDataSourceImpl(
    private val searchService: SearchService
) : SearchRemoteDataSource {

    override suspend fun getSearchResults(): SearchResult {
        return searchService.getSearchResults()
    }
}