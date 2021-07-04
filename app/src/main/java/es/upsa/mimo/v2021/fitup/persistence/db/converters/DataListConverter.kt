package es.upsa.mimo.v2021.fitup.persistence.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import es.upsa.mimo.v2021.fitup.model.DBEntities.ExerciseItem
import java.lang.reflect.Type

class DataListConverter {

    @TypeConverter
    public fun fromOptionValuesList(optionValues: List<ExerciseItem>?): String? {
        if (optionValues != null && optionValues.isEmpty()) {
            return (null);
        }
        val gson = Gson();
        val type =
            object : TypeToken<List<ExerciseItem>?>() {}.type
        val json: String = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter
    public fun toOptionValuesList(optionValuesString: String): List<ExerciseItem>? {
        val gson = Gson();
        val type: Type = object : TypeToken<List<ExerciseItem>?>() {}.type
        val productCategoriesList: List<ExerciseItem> = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }

}