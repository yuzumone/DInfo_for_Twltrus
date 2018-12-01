package net.yuzumone.twltrus.tdr.ui.common

import android.content.Context
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import net.yuzumone.twltrus.tdr.R
import net.yuzumone.twltrus.tdr.databinding.ListRealtimeShowBinding
import net.yuzumone.twltrus.tdr.databinding.ListShowBinding
import net.yuzumone.twltrus.tdr.model.RealtimeShow
import net.yuzumone.twltrus.tdr.model.Show

class RealtimeShowAdapter(context: Context) : ArrayAdapter<RealtimeShow>(context, 0) {

    private val inflater: LayoutInflater by lazy {
        LayoutInflater.from(context)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val binding: ListRealtimeShowBinding
        if (view == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.list_realtime_show, parent, false)
            view = binding.root
            view.tag = binding
        } else {
            binding = view.tag as ListRealtimeShowBinding
        }
        binding.show = getItem(position)
        return view
    }
}