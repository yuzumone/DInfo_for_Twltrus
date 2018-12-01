package net.yuzumone.twltrus.tdr.ui.detail

import android.net.Uri
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.ListFragment
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import net.yuzumone.twltrus.tdr.R
import net.yuzumone.twltrus.tdr.ui.common.GreetingAdapter
import net.yuzumone.twltrus.tdr.model.Greeting

class GreetingFragment : ListFragment() {

    private val greetings: ArrayList<Greeting> by lazy {
        arguments!!.getParcelableArrayList<Greeting>(ARG_GREETINGS)
    }

    companion object {
        private const val ARG_GREETINGS = "greetings"
        fun newInstance(greetings: ArrayList<Greeting>): GreetingFragment {
            return GreetingFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_GREETINGS, greetings)
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = GreetingAdapter(activity!!)
        adapter.addAll(greetings)
        adapter.notifyDataSetChanged()
        listAdapter = adapter
        if (greetings.isEmpty()) {
            val view = LayoutInflater.from(activity).inflate(R.layout.view_no_data, listView, false)
            listView.addHeaderView(view)
        }
        listView.setOnItemClickListener { _, _, position, _ ->
            val greeting = adapter.getItem(position)
            if (!greeting.FacilityURLSP.isNullOrBlank()) {
                val intent = CustomTabsIntent.Builder()
                        .setToolbarColor(ContextCompat.getColor(activity!!, R.color.colorPrimary))
                        .build()
                intent.launchUrl(activity, Uri.parse(greeting.FacilityURLSP))
            }
        }
    }
}