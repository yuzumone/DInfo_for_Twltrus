package net.yuzumone.twltrus.tdr.view

import android.databinding.BindingAdapter
import android.widget.TextView
import net.yuzumone.twltrus.tdr.R
import net.yuzumone.twltrus.tdr.model.OperatingHours

object DataBindingAdapter {

    @BindingAdapter("standby")
    @JvmStatic
    fun setStandbyTime(view: TextView, standbyTime: String?) {
        if (standbyTime == null) return
        view.text = view.context.getString(R.string.standby_time, standbyTime)
    }

    @BindingAdapter("operatingHours")
    @JvmStatic
    fun setOperatingHours(view: TextView, operatingHours: List<OperatingHours>?) {
        if (operatingHours == null) return
        view.text = operatingHours.joinToString(separator = "\n") { it.OperatingHoursFrom.toString() }
    }

    @BindingAdapter("greeting")
    @JvmStatic
    fun setGreetingOperatingHours(view: TextView, operatingHours: List<OperatingHours>?) {
        if (operatingHours == null) return
        view.text = operatingHours.joinToString(separator = "\n") {
            it.OperatingHoursFrom + " - " + it.OperatingHoursTo + "\t" + it.OperatingStatus
        }
    }
}
