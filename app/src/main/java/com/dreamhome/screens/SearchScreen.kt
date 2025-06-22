package com.dreamhome.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dreamhome.R
import com.dreamhome.ui.theme.DreamHomeTheme
import coil3.compose.AsyncImage
import com.dreamhome.data.entities.Area
import com.dreamhome.data.entities.HighlightedProperty
import com.dreamhome.data.entities.Property
import com.dreamhome.ui.theme.Purple40
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            is SearchViewModel.State.Loading -> {
                CircularProgressIndicator()
            }

            is SearchViewModel.State.Success -> {
                val result = (state as SearchViewModel.State.Success).result

                LazyColumn {
                    items(result.items.size) {
                        when (val item = result.items[it]) {
                            is HighlightedProperty -> {
                                HighlightedProperty(
                                    modifier = Modifier.padding(12.dp),
                                    property = item
                                )
                            }

                            is Property -> {
                                Property(
                                    modifier = Modifier.padding(12.dp),
                                    property = item
                                )
                            }

                            is Area -> {
                                Area(
                                    modifier = Modifier.padding(12.dp),
                                    area = item
                                )
                            }
                        }
                    }
                }
            }

            is SearchViewModel.State.Error -> {
                Text((state as SearchViewModel.State.Error).message)
            }
        }
    }
}

@Composable
fun Area(
    modifier: Modifier = Modifier,
    area: Area
) {
    Column(modifier = modifier) {
        PropertyImage(
            imageUrl = area.image
        )
    }
}

@Composable
fun Property(
    modifier: Modifier = Modifier,
    property: Property
) {
    Column(modifier = modifier) {
        PropertyImage(
            imageUrl = property.image
        )
        PropertyAddress(
            address = property.streetAddress,
            municipality = "${property.municipality}, ${property.area}"
        )
        PropertyDetails(
            price = property.askingPrice,
            livingArea = property.livingArea,
            numberOfRooms = property.numberOfRooms,
            daysOnHemnet = property.daysOnHemnet
        )
    }
}

@Composable
fun HighlightedProperty(
    modifier: Modifier = Modifier,
    property: HighlightedProperty
) {
    Column(modifier = modifier) {
        PropertyImage(
            imageUrl = property.image,
            isHighlighted = true
        )
        PropertyAddress(
            address = property.streetAddress,
            municipality = "${property.municipality}, ${property.area}"
        )
        PropertyDetails(
            price = property.askingPrice,
            livingArea = property.livingArea,
            numberOfRooms = property.numberOfRooms,
            daysOnHemnet = property.daysOnHemnet
        )
    }
}

@Composable
fun PropertyImage(
    modifier: Modifier = Modifier,
    imageUrl: String = "https://upload.wikimedia.org/wikipedia/commons/f/f9/Navigat%C3%B8rernes_Hus_01.jpg",
    isHighlighted: Boolean = false
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = "Property Image",
        modifier = modifier
            .height(150.dp)
            .then(
                when {
                    isHighlighted -> Modifier.border(
                        width = 2.dp,
                        color = Color(0xFFFFD700),
                    )

                    else -> Modifier
                }
            ),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun PropertyAddress(
    modifier: Modifier = Modifier,
    address: String = "Hagagatan 1, 2tr",
    municipality: String = "Vasastan Odengatan, Stockholm"
) {
    Column {
        Text(address)

        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                modifier = Modifier
                    .width(24.dp),
                painter = painterResource(id = R.drawable.property_24dp),
                contentDescription = "Property Type Icon",
                tint = Purple40
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = municipality,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun PropertyDetails(
    modifier: Modifier = Modifier,
    price: String = "6 150 000 kr",
    livingArea: Float = 71f,
    numberOfRooms: Float = 2.5f,
    daysOnHemnet: Int = 11
) {
    Row(
        modifier = modifier
    ) {
        Text(text = price, textAlign = TextAlign.Start)
        Spacer(modifier = Modifier.width(16.dp))

        Text(text = "$livingArea m²")
        Spacer(modifier = Modifier.width(16.dp))

        Text(text = "$numberOfRooms rum")

        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End,
            text = "$daysOnHemnet dagar"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DreamHomeTheme {
        Column {
            PropertyImage()
            PropertyAddress()
            PropertyDetails()
        }
    }
}