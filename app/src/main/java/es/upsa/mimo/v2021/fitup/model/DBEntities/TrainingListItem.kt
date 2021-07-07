package es.upsa.mimo.v2021.fitup.model.DBEntities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class TrainingListItem(var name: String?, var creationDate: Date?,var exercises: MutableList<ExerciseItem>?,
                            var userItem: UserItem?): Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        Date(parcel.readLong()),
        arrayListOf<ExerciseItem>().apply {
            parcel.readList(this as List<ExerciseItem>, ExerciseItem::class.java.classLoader)
        },
        parcel.readParcelable(UserItem::class.java.classLoader)

    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeLong(creationDate?.time ?: 0)
        parcel.writeParcelable(userItem, flags)
        parcel.writeParcelableList(exercises, Parcelable.PARCELABLE_WRITE_RETURN_VALUE)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TrainingListItem> {
        override fun createFromParcel(parcel: Parcel): TrainingListItem {
            return TrainingListItem(parcel)
        }

        override fun newArray(size: Int): Array<TrainingListItem?> {
            return arrayOfNulls(size)
        }
    }

}