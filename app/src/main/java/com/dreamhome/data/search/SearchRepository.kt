package com.dreamhome.data.search

import com.dreamhome.data.entities.SearchResult

interface SearchRepository {
    suspend fun getSearchResults(): SearchResult
}

internal class SearchRepositoryImpl(
    private val remoteDataSource: SearchRemoteDataSource,
    private val localDataSource: SearchLocalDataSource
) : SearchRepository {

    override suspend fun getSearchResults(): SearchResult {
        return localDataSource.getSearchResults() ?: remoteDataSource.getSearchResults().also {
            localDataSource.saveSearchResults(it)
        }
    }
}