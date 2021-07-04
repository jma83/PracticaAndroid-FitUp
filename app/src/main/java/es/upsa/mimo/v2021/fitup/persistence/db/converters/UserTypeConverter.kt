package es.upsa.mimo.v2021.fitup.persistence.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import es.upsa.mimo.v2021.fitup.model.DBEntities.UserItem

class UserTypeConverter {
    @TypeConverter
    fun fromUserToString(user: UserItem): String = Gson().toJson(user)

    @TypeConverter
    fun toStringToUser(string: String): UserItem? = Gson().fromJson(string, UserItem::class.java)

}