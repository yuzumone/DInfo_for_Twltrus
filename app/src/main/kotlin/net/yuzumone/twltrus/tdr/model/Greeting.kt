package net.yuzumone.twltrus.tdr.model

import paperparcel.PaperParcel
import paperparcel.PaperParcelable

@PaperParcel
data class Greeting(
        val name: String,
        val left: String,
        val right: String,
        val wait: String,
        val url: String) : PaperParcelable {
    companion object {
        val CREATOR = PaperParcelGreeting.CREATOR
    }
}