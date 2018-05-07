package net.yuzumone.twltrus.tdr.model

import android.os.Parcel
import android.os.Parcelable

data class Restaurant(
        val FacilityName: String,
        val FacilityURLSP: String?,
        val StandbyTimeMin: String?,
        val StandbyTimeMax: String?,
        val UpdateTime: String,
        val operatingHours: List<OperatingHours>?
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.createTypedArrayList(OperatingHours))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(FacilityName)
        parcel.writeString(FacilityURLSP)
        parcel.writeString(StandbyTimeMin)
        parcel.writeString(StandbyTimeMax)
        parcel.writeString(UpdateTime)
        parcel.writeTypedList(operatingHours)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Restaurant> {
        override fun createFromParcel(parcel: Parcel): Restaurant {
            return Restaurant(parcel)
        }

        override fun newArray(size: Int): Array<Restaurant?> {
            return arrayOfNulls(size)
        }
    }
}