package net.yuzumone.twltrus.tdr.view

import android.databinding.BindingAdapter
import android.widget.TextView
import net.yuzumone.twltrus.tdr.R
import net.yuzumone.twltrus.tdr.model.OperatingHours
import net.yuzumone.twltrus.tdr.model.Restaurant

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

    @BindingAdapter("restaurant_standby")
    @JvmStatic
    fun setRestaurantStandbyTime(view:TextView, restaurant: Restaurant) {
        val max = restaurant.StandbyTimeMax
        val min = restaurant.StandbyTimeMin
        if (max == null || min == null) return
        if (max.toInt() == min.toInt()) {
            view.text = view.context.getString(R.string.standby_time, min)
        } else {
            view.text = view.context.getString(R.string.diff_standby_time, min, max)
        }
    }

    @BindingAdapter("restaurant_run")
    @JvmStatic
    fun setRestaurantRun(view: TextView, operatingHours: List<OperatingHours>?) {
        operatingHours ?: return
        view.text = operatingHours.joinToString(separator = "\n") {
            val start = it.OperatingHoursFrom ?: ""
            val end = it.OperatingHoursTo ?: ""
            val status = it.OperatingStatus ?: ""
            "$start - $end \t $status"
        }
    }
}
