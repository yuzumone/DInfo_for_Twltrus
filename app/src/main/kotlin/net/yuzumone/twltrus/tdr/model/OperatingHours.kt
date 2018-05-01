package net.yuzumone.twltrus.tdr.model

import android.os.Parcel
import android.os.Parcelable

data class OperatingHours(
        val OperatingHoursFrom: String?,
        val OperatingHoursTo: String?,
        val OperatingStatus: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(OperatingHoursFrom)
        parcel.writeString(OperatingHoursTo)
        parcel.writeString(OperatingStatus)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OperatingHours> {
        override fun createFromParcel(parcel: Parcel): OperatingHours {
            return OperatingHours(parcel)
        }

        override fun newArray(size: Int): Array<OperatingHours?> {
            return arrayOfNulls(size)
        }
    }
}