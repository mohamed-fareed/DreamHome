package com.dreamhome.data.search

import com.dreamhome.data.entities.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class SearchRepositoryTest {

    private lateinit var remoteDataSource: SearchRemoteDataSource
    private lateinit var localDataSource: SearchLocalDataSource
    private lateinit var repository: SearchRepositoryImpl

    private val property = Property(
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
    private val searchResult = SearchResult(listOf(property))

    @Before
    fun setUp() {
        remoteDataSource = mock()
        localDataSource = mock()
        repository = SearchRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun `returns local results if available`() = runTest {
        whenever(localDataSource.getSearchResults()).thenReturn(searchResult)

        val result = repository.getSearchResults()

        assertEquals(searchResult, result)
        verify(localDataSource).getSearchResults()
        verifyNoMoreInteractions(remoteDataSource)
    }

    @Test
    fun `fetches from remote and saves if local is null`() = runTest {
        whenever(localDataSource.getSearchResults()).thenReturn(null)
        whenever(remoteDataSource.getSearchResults()).thenReturn(searchResult)

        val result = repository.getSearchResults()

        assertEquals(searchResult, result)
        verify(localDataSource).getSearchResults()
        verify(remoteDataSource).getSearchResults()
        verify(localDataSource).saveSearchResults(searchResult)
    }
}