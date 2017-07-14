package net.yuzumone.twltrus.tdr.fragment

import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.app.ListFragment
import android.support.v4.content.ContextCompat
import net.yuzumone.twltrus.tdr.R
import net.yuzumone.twltrus.tdr.adapter.ShowAdapter
import net.yuzumone.twltrus.tdr.model.Show

class ShowFragment : ListFragment() {

    private val shows: ArrayList<Show> by lazy {
        arguments.getParcelableArrayList<Show>(ARG_SHOWS)
    }

    companion object {
        val ARG_SHOWS = "shows"
        fun newInstance(shows: ArrayList<Show>): ShowFragment {
            return ShowFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_SHOWS, shows)
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = ShowAdapter(activity)
        adapter.addAll(shows)
        adapter.notifyDataSetChanged()
        listAdapter = adapter
        listView.setOnItemClickListener { parent, view, position, id ->
            val show = adapter.getItem(position)
            if (show.url != "") {
                val intent = CustomTabsIntent.Builder()
                        .setToolbarColor(ContextCompat.getColor(activity, R.color.colorPrimary))
                        .build()
                intent.launchUrl(activity, Uri.parse(show.url))
            }
        }
    }
}