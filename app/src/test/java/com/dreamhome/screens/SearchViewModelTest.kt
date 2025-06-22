package com.dreamhome.screens

import app.cash.turbine.test
import com.dreamhome.data.entities.Property
import com.dreamhome.data.entities.SearchResult
import com.dreamhome.data.search.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {
    private lateinit var repository: SearchRepository
    private lateinit var viewModel: SearchViewModel

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
        repository = mock()
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @Test
    fun `emits Loading then Success when fetch succeeds`() = runTest {
        whenever(repository.getSearchResults()).thenReturn(searchResult)
        viewModel = SearchViewModel(repository)

        viewModel.state.test {
            assertEquals(SearchViewModel.State.Loading, awaitItem())
            assertEquals(SearchViewModel.State.Success(searchResult), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emits Loading then Error when fetch fails`() = runTest {
        whenever(repository.getSearchResults()).thenThrow(RuntimeException("Network error"))
        viewModel = SearchViewModel(repository)

        viewModel.state.test {
            assertEquals(SearchViewModel.State.Loading, awaitItem())
            val error = awaitItem()
            assertTrue(error is SearchViewModel.State.Error)
            assertEquals("Network error", (error as SearchViewModel.State.Error).message)
            cancelAndIgnoreRemainingEvents()
        }
    }
}