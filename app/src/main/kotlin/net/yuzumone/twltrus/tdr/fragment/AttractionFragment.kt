package net.yuzumone.twltrus.tdr.fragment

import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.app.ListFragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import net.yuzumone.twltrus.tdr.R
import net.yuzumone.twltrus.tdr.adapter.AttractionAdapter
import net.yuzumone.twltrus.tdr.model.Attraction

class AttractionFragment : ListFragment() {

    private val attractions: ArrayList<Attraction> by lazy {
        arguments!!.getParcelableArrayList<Attraction>(ARG_ATTRACTIONS)
    }

    companion object {
        val ARG_ATTRACTIONS = "attractions"
        fun newInstance(attractions: ArrayList<Attraction>): AttractionFragment {
            return AttractionFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_ATTRACTIONS, attractions)
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = AttractionAdapter(activity!!)
        adapter.addAll(attractions)
        adapter.notifyDataSetChanged()
        listAdapter = adapter
        if (attractions.isEmpty()) {
            val view = LayoutInflater.from(activity).inflate(R.layout.view_no_data, listView, false)
            listView.addHeaderView(view)
        }
        listView.setOnItemClickListener { parent, view, position, id ->
            val attraction = adapter.getItem(position)
            if (attraction.url != "") {
                val intent = CustomTabsIntent.Builder()
                        .setToolbarColor(ContextCompat.getColor(activity!!, R.color.colorPrimary))
                        .build()
                intent.launchUrl(activity, Uri.parse(attraction.url))
            }
        }
    }
}