package com.dreamhome.data.search

import android.content.SharedPreferences
import com.dreamhome.data.entities.Property
import com.dreamhome.data.entities.SearchItemTypeAdapterFactory
import com.dreamhome.data.entities.SearchResult
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.whenever

class SearchLocalDataSourceTest {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var gson: Gson
    private lateinit var dataSource: SearchLocalDataSourceImpl

    @Before
    fun setUp() {
        sharedPreferences = mock(SharedPreferences::class.java)
        editor = mock(SharedPreferences.Editor::class.java)

        val searchItemAdapterFactory = SearchItemTypeAdapterFactory()
        gson = GsonBuilder()
            .registerTypeAdapterFactory(searchItemAdapterFactory)
            .create()

        whenever(sharedPreferences.edit()).thenReturn(editor)
        whenever(editor.putString(anyString(), anyString())).thenReturn(editor)
        whenever(editor.putLong(anyString(), anyLong())).thenReturn(editor)
        whenever(editor.apply()).then {}

        dataSource = SearchLocalDataSourceImpl(sharedPreferences, gson)
    }

    @Test
    fun `saveSearchResults stores data in SharedPreferences`() = runTest {
        val property = Property(
            type = "Property",
            id = "prop123",
            askingPrice = "500000",
            monthlyFee = "2500",
            municipality = "Stockholm",
            area = "Södermalm",
            daysOnHemnet = 10,
            livingArea = 75.0f,
            numberOfRooms = 3.0f,
            streetAddress = "Testgatan 1",
            image = "http://deamhome.com/image.jpg"
        )
        val result = SearchResult(listOf(property))

        dataSource.saveSearchResults(result)

        verify(editor).putString(eq("saved_search_result"), anyString())
        verify(editor).putLong(eq("last_fetch_timestamp"), anyLong())
        verify(editor).apply()
    }

    @Test
    fun `getSearchResults returns null if cache expired`() = runTest {
        whenever(sharedPreferences.getLong(eq("last_fetch_timestamp"), anyLong()))
            .thenReturn(System.currentTimeMillis() - 2 * 60 * 1000) // 2 minutes ago

        val result = dataSource.getSearchResults()
        assertNull(result)
    }

    @Test
    fun `getSearchResults returns SearchResult if cache valid`() = runTest {
        val property = Property(
            type = "Property",
            id = "prop123",
            askingPrice = "500000",
            monthlyFee = "2500",
            municipality = "Stockholm",
            area = "Södermalm",
            daysOnHemnet = 10,
            livingArea = 75.0f,
            numberOfRooms = 3.0f,
            streetAddress = "Testgatan 1",
            image = "http://deamhome.com/image.jpg"
        )
        val result = SearchResult(listOf(property))
        val json = gson.toJson(result)

        whenever(sharedPreferences.getLong(eq("last_fetch_timestamp"), anyLong()))
            .thenReturn(System.currentTimeMillis())
        whenever(sharedPreferences.getString(eq("saved_search_result"), isNull()))
            .thenReturn(json)

        val loaded = dataSource.getSearchResults()
        assertNotNull(loaded)
        assertEquals(result, loaded)
    }
}