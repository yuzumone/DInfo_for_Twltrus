package net.yuzumone.twltrus.tdr.ui.detail

import android.net.Uri
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.ListFragment
import androidx.core.content.ContextCompat
import net.yuzumone.twltrus.tdr.R
import net.yuzumone.twltrus.tdr.ui.common.RehabAdapter
import net.yuzumone.twltrus.tdr.model.Rehab

class RehabFragment : ListFragment() {

    private val shows: ArrayList<Rehab> by lazy {
        arguments!!.getParcelableArrayList<Rehab>(ARG_REHAB)
    }

    companion object {
        private const val ARG_REHAB = "rehab"
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
        val adapter = RehabAdapter(activity!!)
        adapter.addAll(shows)
        adapter.notifyDataSetChanged()
        listAdapter = adapter
        listView.setOnItemClickListener { _, _, position, _ ->
            val rehab = adapter.getItem(position)
            if (rehab.url != "") {
                val intent = CustomTabsIntent.Builder()
                        .setToolbarColor(ContextCompat.getColor(activity!!, R.color.colorPrimary))
                        .build()
                intent.launchUrl(activity, Uri.parse(rehab.url))
            }
        }
    }
}