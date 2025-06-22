package com.dreamhome.data.entities

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SearchItemTypeAdapterFactoryTest {

    private lateinit var gson: Gson

    @Before
    fun setUp() {
        gson = GsonBuilder()
            .registerTypeAdapterFactory(SearchItemTypeAdapterFactory())
            .create()
    }

    @Test
    fun `test serialize and deserialize Property`() {
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
        val json = gson.toJson(property, SearchItem::class.java)
        val deserialized = gson.fromJson(json, SearchItem::class.java)
        assertTrue(deserialized is Property)
        assertEquals(property, deserialized)
    }

    @Test
    fun `test serialize and deserialize HighlightedProperty`() {
        val highlighted = HighlightedProperty(
            type = "HighlightedProperty",
            id = "high123",
            askingPrice = "750000",
            monthlyFee = "3000",
            municipality = "Göteborg",
            area = "Hisingen",
            daysOnHemnet = 5,
            livingArea = 90.0f,
            numberOfRooms = 4.0f,
            streetAddress = "Exempelvägen 2",
            image = "http://deamhome.com/highlighted.jpg"
        )
        val json = gson.toJson(highlighted, SearchItem::class.java)
        val deserialized = gson.fromJson(json, SearchItem::class.java)
        assertTrue(deserialized is HighlightedProperty)
        assertEquals(highlighted, deserialized)
    }

    @Test
    fun `test serialize and deserialize Area`() {
        val area = Area(
            type = "Area",
            id = "area123",
            area = "Vasastan",
            rating = "A",
            averagePrice = "600000",
            image = "http://deamhome.com/area.jpg"
        )
        val json = gson.toJson(area, SearchItem::class.java)
        val deserialized = gson.fromJson(json, SearchItem::class.java)
        assertTrue(deserialized is Area)
        assertEquals(area, deserialized)
    }

    @Test(expected = com.google.gson.JsonParseException::class)
    fun `test deserialize unknown type throws exception`() {
        val json = """{"type":"UnknownType"}"""
        gson.fromJson(json, SearchItem::class.java)
    }
}