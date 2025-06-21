package com.dreamhome.data.entities

data class SearchResult(
    val items: List<SearchItem>
)

sealed class SearchItem {
    abstract val type: String
    abstract val id: String
    abstract val image: String
    abstract val area: String
}

data class Property(
    override val type: String,
    override val id: String,
    val askingPrice: String,
    val monthlyFee: String,
    val municipality: String,
    override val area: String,
    val daysOnHemnet: Int,
    val livingArea: Int,
    val numberOfRooms: Int,
    val streetAddress: String,
    override val image: String
) : SearchItem()

data class HighlightedProperty(
    override val type: String,
    override val id: String,
    val askingPrice: String,
    val monthlyFee: String,
    val municipality: String,
    override val area: String,
    val daysOnHemnet: Int,
    val livingArea: Int,
    val numberOfRooms: Int,
    val streetAddress: String,
    override val image: String
) : SearchItem()

data class Area(
    override val type: String,
    override val id: String,
    override val area: String,
    val rating: String,
    val averagePrice: String,
    override val image: String
) : SearchItem()