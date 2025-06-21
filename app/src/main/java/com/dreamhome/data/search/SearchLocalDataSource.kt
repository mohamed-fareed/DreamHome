package com.dreamhome.data.search

import android.content.SharedPreferences
import com.dreamhome.data.entities.SearchResult
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

internal interface SearchLocalDataSource {
    suspend fun saveSearchResults(result: SearchResult)
    suspend fun getSearchResults(): SearchResult?
}

internal class SearchLocalDataSourceImpl(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson,
    private val ioDispatcher: CoroutineContext = Dispatchers.IO
) : SearchLocalDataSource {

    override suspend fun saveSearchResults(result: SearchResult) =
        withContext(ioDispatcher) {
            val jsonString = gson.toJson(result)

            sharedPreferences.edit()
                .putString(KEY_SEARCH_RESULT, jsonString)
                .putLong(KEY_LAST_FETCH, System.currentTimeMillis())
                .apply()
        }

    override suspend fun getSearchResults(): SearchResult? =
        withContext(ioDispatcher) {
            val lastFetch = sharedPreferences.getLong(KEY_LAST_FETCH, 0)
            if (System.currentTimeMillis() - lastFetch > CACHE_TTL) {
                return@withContext null
            }

            sharedPreferences.getString(KEY_SEARCH_RESULT, null)?.let { jsonString ->
                gson.fromJson(jsonString, SearchResult::class.java)
            }
        }

    companion object {
        private const val KEY_SEARCH_RESULT = "saved_search_result"
        private const val KEY_LAST_FETCH = "last_fetch_timestamp"
        private const val CACHE_TTL = 60 * 1000 // 1 minute in milliseconds
    }
}