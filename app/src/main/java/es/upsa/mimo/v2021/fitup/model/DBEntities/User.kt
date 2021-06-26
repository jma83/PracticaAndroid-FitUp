package es.upsa.mimo.v2021.fitup.model.DBEntities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class User(@PrimaryKey var id: Int = 0, var email: String, var name: String,
                var password: String, var birthdate: Date, var userToken: String) {
}