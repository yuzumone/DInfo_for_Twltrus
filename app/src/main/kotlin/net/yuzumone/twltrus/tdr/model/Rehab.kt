package net.yuzumone.twltrus.tdr.model

import paperparcel.PaperParcel
import paperparcel.PaperParcelable

@PaperParcel
data class Rehab(
        val name: String,
        val date: String,
        val url: String) : PaperParcelable {
    companion object {
        @JvmField val CREATOR = PaperParcelRehab.CREATOR
    }
}