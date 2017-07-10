package net.yuzumone.twltrus.tdr.model

import paperparcel.PaperParcel
import paperparcel.PaperParcelable

@PaperParcel
data class Restaurant(
        val name: String,
        val wait: String,
        val run: String,
        val left: String,
        val right: String,
        val url: String) : PaperParcelable {
    companion object {
        @JvmField val CREATOR = PaperParcelRestaurant.CREATOR
    }
}