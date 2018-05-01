package net.yuzumone.twltrus.tdr.model

import android.os.Parcel
import android.os.Parcelable

data class RealtimeShow(
        val FacilityName: String,
        val UpdateTime: String,
        val FacilityURLSP: String?,
        val operatingHours: List<OperatingHours>?
        ) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.createTypedArrayList(OperatingHours))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(FacilityName)
        parcel.writeString(UpdateTime)
        parcel.writeString(FacilityURLSP)
        parcel.writeTypedList(operatingHours)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RealtimeShow> {
        override fun createFromParcel(parcel: Parcel): RealtimeShow {
            return RealtimeShow(parcel)
        }

        override fun newArray(size: Int): Array<RealtimeShow?> {
            return arrayOfNulls(size)
        }
    }
}