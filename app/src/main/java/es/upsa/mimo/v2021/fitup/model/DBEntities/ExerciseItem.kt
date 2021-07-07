package es.upsa.mimo.v2021.fitup.model.DBEntities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import es.upsa.mimo.v2021.fitup.utils.extensions.createIntList
import es.upsa.mimo.v2021.fitup.utils.extensions.writeIntList

@Entity
data class ExerciseItem(var external_id: Int = 0, var name: String?, var exercise_base: Int = 0,
                        var description: String?, var category: Int = 0, var muscles: List<Int>?, val language: Int = 2, val image: String?
): Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.createIntList(),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(external_id)
        parcel.writeString(name)
        parcel.writeInt(exercise_base)
        parcel.writeString(description)
        parcel.writeInt(category)
        muscles?.let { parcel.writeIntList(it) }
        parcel.writeInt(language)
        parcel.writeString(image)
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