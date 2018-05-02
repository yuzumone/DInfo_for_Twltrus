package net.yuzumone.twltrus.tdr.model

import android.os.Parcel
import android.os.Parcelable

data class Greeting(
        val FacilityName: String,
        val UpdateTime: String,
        val FacilityURLSP: String?,
        val operatinghours: List<OperatingHours>?,
        val StandbyTime: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.createTypedArrayList(OperatingHours),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(FacilityName)
        parcel.writeString(UpdateTime)
        parcel.writeString(FacilityURLSP)
        parcel.writeTypedList(operatinghours)
        parcel.writeString(StandbyTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Greeting> {
        override fun createFromParcel(parcel: Parcel): Greeting {
            return Greeting(parcel)
        }

        override fun newArray(size: Int): Array<Greeting?> {
            return arrayOfNulls(size)
        }
    }
}