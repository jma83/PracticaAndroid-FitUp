package es.upsa.mimo.v2021.fitup.persistence.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import es.upsa.mimo.v2021.fitup.model.DBEntities.ExerciseItem
import es.upsa.mimo.v2021.fitup.model.DBEntities.TrainingListItem
import es.upsa.mimo.v2021.fitup.model.DBEntities.UserItem
import es.upsa.mimo.v2021.fitup.persistence.db.converters.DataListConverter
import es.upsa.mimo.v2021.fitup.persistence.db.converters.DateTypeConverter
import es.upsa.mimo.v2021.fitup.persistence.db.converters.IntTypeConverter
import es.upsa.mimo.v2021.fitup.persistence.db.converters.UserTypeConverter

@Database(entities = arrayOf(ExerciseItem::class, TrainingListItem::class, UserItem::class), version = 1, exportSchema = false)
@TypeConverters(DateTypeConverter::class, DataListConverter::class, IntTypeConverter::class, UserTypeConverter::class)
abstract class FitUpDatabase : RoomDatabase() {
    abstract fun UserDao(): UserDao;
    abstract fun TrainingListDao(): TrainingListDao;
    abstract fun ExerciseItemsDao(): ExerciseItemsDao;

    companion object {
        @Volatile private var instance: FitUpDatabase? = null
        private val LOCK = Any()
        private val DB_NAME = "fitup_db"

        fun get() =
            instance

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK){
            instance
                ?: buildDatabase(
                    context
                )
                    .also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            FitUpDatabase::class.java,
            DB_NAME
        )
            .build()
    }
}