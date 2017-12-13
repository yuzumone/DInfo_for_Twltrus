package net.yuzumone.twltrus.tdr.fragment

import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import net.yuzumone.twltrus.tdr.R
import net.yuzumone.twltrus.tdr.adapter.ShowAdapter
import net.yuzumone.twltrus.tdr.api.ShowApi
import net.yuzumone.twltrus.tdr.databinding.FragmentShowScheduleBinding
import net.yuzumone.twltrus.tdr.model.Park
import java.text.SimpleDateFormat
import java.util.*

class ShowScheduleFragment : Fragment() {

    private lateinit var binding: FragmentShowScheduleBinding
    private lateinit var adapter: ShowAdapter
    private var date = Date()
    private val format: SimpleDateFormat by lazy {
        SimpleDateFormat("yyyy/MM/dd (E)")
    }
    private val park: Park by lazy {
        arguments.getSerializable(ARG_PARK) as Park
    }

    companion object {
        private const val ARG_PARK = "park"
        fun newInstance(park: Park): ShowScheduleFragment {
            return ShowScheduleFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARK, park)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_show_schedule, container, false)
        initView()
        getSchedule(date)
        return binding.root
    }

    private fun initView() {
        adapter = ShowAdapter(activity)
        binding.list.adapter = adapter
        binding.list.setOnItemClickListener { adapterView, view, position, id ->
            val show = adapter.getItem(position)
            if (show.url != "") {
                val intent = CustomTabsIntent.Builder()
                        .setToolbarColor(ContextCompat.getColor(activity, R.color.colorPrimary))
                        .build()
                intent.launchUrl(activity, Uri.parse(show.url))
            }
        }
        binding.textDate.text = format.format(date)
        binding.buttonBack.setOnClickListener {
            val cal = Calendar.getInstance()
            cal.time = date
            cal.add(Calendar.DATE ,-1)
            date = cal.time
            getSchedule(date)
        }
        binding.buttonForward.setOnClickListener {
            val cal = Calendar.getInstance()
            cal.time = date
            cal.add(Calendar.DATE ,1)
            date = cal.time
            getSchedule(date)
        }
    }

    private fun getSchedule(date: Date) {
        binding.textDate.text = format.format(date)
        if (park == Park.TDR) {
            getTdl(date)
        } else if (park == Park.TDS) {
            getTds(date)
        }
    }

    private fun getTdl(date: Date) = launch(UI) {
        try {
            adapter.clear()
            val shows = async { ShowApi.getTdl(date) }.await()
            adapter.addAll(shows)
            adapter.notifyDataSetChanged()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getTds(date: Date) = launch(UI) {
        try {
            adapter.clear()
            val shows = async { ShowApi.getTds(date) }.await()
            adapter.addAll(shows)
            adapter.notifyDataSetChanged()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}