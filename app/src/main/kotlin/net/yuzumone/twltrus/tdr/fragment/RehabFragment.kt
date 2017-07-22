package net.yuzumone.twltrus.tdr.fragment

import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.app.ListFragment
import android.support.v4.content.ContextCompat
import net.yuzumone.twltrus.tdr.R
import net.yuzumone.twltrus.tdr.adapter.RehabAdapter
import net.yuzumone.twltrus.tdr.model.Rehab

class RehabFragment : ListFragment() {

    private val shows: ArrayList<Rehab> by lazy {
        arguments.getParcelableArrayList<Rehab>(ARG_REHAB)
    }

    companion object {
        val ARG_REHAB = "rehab"
        fun newInstance(rehab: ArrayList<Rehab>): RehabFragment {
            return RehabFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_REHAB, rehab)
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = RehabAdapter(activity)
        adapter.addAll(shows)
        adapter.notifyDataSetChanged()
        listAdapter = adapter
        listView.setOnItemClickListener { parent, view, position, id ->
            val rehab = adapter.getItem(position)
            if (rehab.url != "") {
                val intent = CustomTabsIntent.Builder()
                        .setToolbarColor(ContextCompat.getColor(activity, R.color.colorPrimary))
                        .build()
                intent.launchUrl(activity, Uri.parse(rehab.url))
            }
        }
    }
}