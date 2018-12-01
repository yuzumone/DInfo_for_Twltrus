package net.yuzumone.twltrus.tdr.ui.common

import android.content.Context
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import net.yuzumone.twltrus.tdr.R
import net.yuzumone.twltrus.tdr.databinding.ListGreetingBinding
import net.yuzumone.twltrus.tdr.model.Greeting

class GreetingAdapter(context: Context) : ArrayAdapter<Greeting>(context, 0) {

    private val inflater: LayoutInflater by lazy {
        LayoutInflater.from(context)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val binding: ListGreetingBinding
        if (view == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.list_greeting, parent, false)
            view = binding.root
            view.tag = binding
        } else {
            binding = view.tag as ListGreetingBinding
        }
        binding.greeting = getItem(position)
        return view!!
    }
}