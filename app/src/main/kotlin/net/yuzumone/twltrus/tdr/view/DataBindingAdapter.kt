package net.yuzumone.twltrus.tdr.view

import android.databinding.BindingAdapter
import android.widget.TextView
import net.yuzumone.twltrus.tdr.R

object DataBindingAdapter {

    @BindingAdapter("standby")
    @JvmStatic
    fun setStandbyTime(view: TextView, standbyTime: String) {
        view.text = view.context.getString(R.string.standby_time, standbyTime)
    }
}