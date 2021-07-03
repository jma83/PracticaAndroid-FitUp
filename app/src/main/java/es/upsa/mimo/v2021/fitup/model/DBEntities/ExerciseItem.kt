package es.upsa.mimo.v2021.fitup.model.DBEntities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import es.upsa.mimo.v2021.fitup.extensions.createIntList
import es.upsa.mimo.v2021.fitup.extensions.writeIntList

@Entity
data class ExerciseItem(@PrimaryKey var id: Int = 0, var external_id: Int, var name: String?, var exercise_base: Int,
                        var description: String?, var category: Int, var muscles: List<Int>?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.createIntList()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(external_id)
        parcel.writeString(name)
        parcel.writeInt(exercise_base)
        parcel.writeString(description)
        parcel.writeInt(category)
        muscles?.let { parcel.writeIntList(it) }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ExerciseItem> {
        override fun createFromParcel(parcel: Parcel): ExerciseItem {
            return ExerciseItem(parcel)
        }

        override fun newArray(size: Int): Array<ExerciseItem?> {
            return arrayOfNulls(size)
        }
    }

}