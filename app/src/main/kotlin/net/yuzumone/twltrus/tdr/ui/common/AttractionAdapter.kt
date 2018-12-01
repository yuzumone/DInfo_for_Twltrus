package net.yuzumone.twltrus.tdr.ui.common

import android.content.Context
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import net.yuzumone.twltrus.tdr.R
import net.yuzumone.twltrus.tdr.databinding.ListAttractionBinding
import net.yuzumone.twltrus.tdr.model.Attraction

class AttractionAdapter(context: Context) : ArrayAdapter<Attraction>(context, 0) {

    private val inflater: LayoutInflater by lazy {
        LayoutInflater.from(context)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val binding: ListAttractionBinding
        if (view == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.list_attraction, parent, false)
            view = binding.root
            view.tag = binding
        } else {
            binding = view.tag as ListAttractionBinding
        }
        binding.attraction = getItem(position)
        return view!!
    }
}