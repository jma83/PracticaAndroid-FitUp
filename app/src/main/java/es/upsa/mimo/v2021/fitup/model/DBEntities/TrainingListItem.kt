package es.upsa.mimo.v2021.fitup.model.DBEntities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class TrainingListItem(@PrimaryKey var id: Int, var name: String?, var creationDate: Date?, var userItem: UserItem?, var exercises: List<ExerciseItem>?): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readSerializable() as Date?,
        parcel.readParcelable(UserItem::class.java.classLoader),
        parcel.readParcelableList(emptyList<ExerciseItem>(), ExerciseItem::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
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