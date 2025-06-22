package com.dreamhome.data.entities

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class SearchItemTypeAdapterFactory : TypeAdapterFactory {
    override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
        if (!SearchItem::class.java.isAssignableFrom(type.rawType)) {
            return null
        }

        val propertyAdapter = gson.getDelegateAdapter(
            this,
            TypeToken.get(Property::class.java)
        )
        val highlightedPropertyAdapter = gson.getDelegateAdapter(
            this,
            TypeToken.get(HighlightedProperty::class.java)
        )
        val areaAdapter = gson.getDelegateAdapter(
            this,
            TypeToken.get(Area::class.java)
        )

        return object : TypeAdapter<T>() {
            override fun write(out: JsonWriter, value: T) {
                when (value) {
                    is Property -> propertyAdapter.write(out, value)
                    is HighlightedProperty -> highlightedPropertyAdapter.write(out, value)
                    is Area -> areaAdapter.write(out, value)
                    else -> throw IllegalArgumentException("Unknown type: $value")
                }
            }

            @Suppress("UNCHECKED_CAST")
            override fun read(reader: JsonReader): T {
                val jsonObject = JsonParser().parse(reader).asJsonObject
                val typeValue = jsonObject.get(KEY_TYPE).asString
                val adapter = when (typeValue) {
                    TYPE_PROPERTY -> propertyAdapter
                    TYPE_HIGHLIGHTED_PROPERTY -> highlightedPropertyAdapter
                    TYPE_AREA -> areaAdapter
                    else -> throw JsonParseException("Unknown type: $typeValue")
                }
                return adapter.fromJsonTree(jsonObject) as T
            }
        }
    }

    companion object {
        private const val KEY_TYPE = "type"
        private const val TYPE_PROPERTY = "Property"
        private const val TYPE_HIGHLIGHTED_PROPERTY = "HighlightedProperty"
        private const val TYPE_AREA = "Area"
    }
}