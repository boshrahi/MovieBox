package com.boshra.moviebox.framework.network

import com.google.gson.*
import java.lang.reflect.Type
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter : JsonSerializer<Date>,
    JsonDeserializer<Date> {
    private val dateFormat : DateFormat
    init {
        dateFormat = SimpleDateFormat("yyyy-MM-DD", Locale.US)
    }

    override fun serialize(
        src: Date, srcType: Type,
        context: JsonSerializationContext
    ): JsonElement {
        return JsonPrimitive(toJsonDate(src))
    }

    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement, type: Type,
        context: JsonDeserializationContext
    ): Date {
        return fromJsonDate(json.asString)
    }

    private fun toJsonDate(date: Date?): String {
        return if (date == null) ""
        else dateFormat.format(date)
    }

    private fun fromJsonDate(jsonDateStr: String): Date {
        return dateFormat.parse(jsonDateStr)?:Date()
    }
}
