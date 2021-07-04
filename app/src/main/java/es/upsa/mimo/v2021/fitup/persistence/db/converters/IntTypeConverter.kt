package es.upsa.mimo.v2021.fitup.persistence.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class IntTypeConverter(private val gson: Gson = Gson()) {

    @TypeConverter
    public fun fromIntList(optionValues: List<Int>?): String? {
        if (optionValues != null && optionValues.isEmpty()) {
            return (null);
        }
        val gson = Gson();
        val type =
            object : TypeToken<List<Int>?>() {}.type
        val json: String = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter
    public fun toIntList(optionValuesString: String): List<Int>? {
        val gson = Gson();
        val type: Type = object : TypeToken<List<Int>?>() {}.type
        val productCategoriesList: List<Int> = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }
}