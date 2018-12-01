package net.yuzumone.twltrus.tdr.ui.common

import android.content.Context
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import net.yuzumone.twltrus.tdr.R
import net.yuzumone.twltrus.tdr.databinding.ListRehabBinding
import net.yuzumone.twltrus.tdr.model.Rehab

class RehabAdapter(context: Context) : ArrayAdapter<Rehab>(context, 0) {

    private val inflater: LayoutInflater by lazy {
        LayoutInflater.from(context)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val binding: ListRehabBinding
        if (view == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.list_rehab, parent, false)
            view = binding.root
            view.tag = binding
        } else {
            binding = view.tag as ListRehabBinding
        }
        binding.rehab = getItem(position)
        return view!!
    }
}