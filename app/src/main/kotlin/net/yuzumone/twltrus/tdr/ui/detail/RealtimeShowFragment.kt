package net.yuzumone.twltrus.tdr.ui.detail

import android.net.Uri
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.ListFragment
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import net.yuzumone.twltrus.tdr.R
import net.yuzumone.twltrus.tdr.ui.common.RealtimeShowAdapter
import net.yuzumone.twltrus.tdr.model.RealtimeShow

class RealtimeShowFragment : ListFragment() {

    private val shows: ArrayList<RealtimeShow> by lazy {
        arguments!!.getParcelableArrayList<RealtimeShow>(ARG_SHOWS)
    }

    companion object {
        private const val ARG_SHOWS = "shows"
        fun newInstance(shows: ArrayList<RealtimeShow>): RealtimeShowFragment {
            return RealtimeShowFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_SHOWS, shows)
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = RealtimeShowAdapter(activity!!)
        adapter.addAll(shows)
        adapter.notifyDataSetChanged()
        listAdapter = adapter
        if (shows.isEmpty()) {
            val view = LayoutInflater.from(activity).inflate(R.layout.view_no_data, listView, false)
            listView.addHeaderView(view)
        }
        listView.setOnItemClickListener { _, _, position, _ ->
            val show = adapter.getItem(position)
            if (!show.FacilityURLSP.isNullOrBlank()) {
                val intent = CustomTabsIntent.Builder()
                        .setToolbarColor(ContextCompat.getColor(activity!!, R.color.colorPrimary))
                        .build()
                intent.launchUrl(activity, Uri.parse(show.FacilityURLSP))
            }
        }
    }
}