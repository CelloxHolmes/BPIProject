package com.bpi.utils

import androidx.room.TypeConverter
import com.bpi.model.Currency
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CurrencyTypeConverter {
    @TypeConverter
    fun fromString(value: String): Currency {
        val type = object : TypeToken<Currency>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromCurrency(currency: Currency): String {
        return Gson().toJson(currency)
    }
}
