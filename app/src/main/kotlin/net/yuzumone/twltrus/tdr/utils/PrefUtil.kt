package net.yuzumone.twltrus.tdr.utils

import android.content.Context
import android.content.SharedPreferences
import java.util.*

class PrefUtil(context: Context) {

    private val filename = "net.yuzumone.twltrus.tdr"
    private val prefs: SharedPreferences = context.getSharedPreferences(filename, 0)

    var date: Date
        get() {
            val time = prefs.getLong("date", 0)
            return Date(time)
        }
        set(value) = prefs.edit().putLong("date", value.time).apply()

    val shouldGetCookie: Boolean
        get() {
            val now = Date()
            val sqlDate1 = java.sql.Date(now.time)
            val sqlDate2 = java.sql.Date(date.time)
            return sqlDate1.toString() != sqlDate2.toString()
        }

    var cookie: String
        get() = prefs.getString("cookie", "")
        set(value) = prefs.edit().putString("cookie", value).apply()
}