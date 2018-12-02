package net.yuzumone.twltrus.tdr.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import net.yuzumone.twltrus.tdr.R
import net.yuzumone.twltrus.tdr.data.api.*
import net.yuzumone.twltrus.tdr.databinding.FragmentDetailBinding
import net.yuzumone.twltrus.tdr.model.Park
import net.yuzumone.twltrus.tdr.ui.common.PagerAdapter
import net.yuzumone.twltrus.tdr.utils.PrefUtil
import net.yuzumone.twltrus.tdr.utils.convertArrayList

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val park: Park by lazy {
        arguments!!.getSerializable(ARG_PARK) as Park
    }
    private val adapter: PagerAdapter by lazy {
        PagerAdapter(fragmentManager!!)
    }
    private val pref by lazy { PrefUtil(activity!!) }

    companion object {
        private const val ARG_PARK = "park"
        fun newInstance(park: Park): DetailFragment {
            return DetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARK, park)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding.pager.adapter = adapter
        binding.tab.setupWithViewPager(binding.pager)
        setHasOptionsMenu(true)
        refresh()
        return binding.root
    }

    private fun refresh() {
        adapter.clear()
        if (park == Park.TDL) {
            activity!!.setTitle(R.string.tdl)
            getTDL()
        } else if (park == Park.TDS) {
            activity!!.setTitle(R.string.tds)
            getTDS()
        }
    }

    private fun getTDL() = launch(UI) {
        binding.progress.visibility = View.VISIBLE
        try {
            val isClose = async { StatusApi.isCloseTdl() }.await()
            if (isClose) {
                val showFragment = ShowScheduleFragment.newInstance(park)
                val rehab = async(CommonPool) { RehabApi.getTdl() }.await()
                val rehabFragment = RehabFragment.newInstance(rehab.convertArrayList())
                adapter.add(getString(R.string.show), showFragment)
                adapter.add(getString(R.string.rehab), rehabFragment)
            } else {
                val showJob = async(CommonPool) { ShowApi.getRealtimeTdl(pref.cookie) }
                val attractionJob = async(CommonPool) { AttractionApi.getTdl(pref.cookie) }
                val restaurantJob = async(CommonPool) { RestaurantApi.getTdl(pref.cookie) }
                val greetingJob = async(CommonPool) { GreetingApi.getTdl(pref.cookie) }
                val rehabJob = async(CommonPool) { RehabApi.getTdl() }
                val shows = showJob.await()
                val attractions = attractionJob.await()
                val restaurants = restaurantJob.await()
                val greetings = greetingJob.await()
                val rehab = rehabJob.await()
                val showFragment = RealtimeShowFragment.newInstance(shows.convertArrayList())
                val attractionFragment = AttractionFragment.newInstance(attractions.convertArrayList())
                val restaurantFragment = RestaurantFragment.newInstance(restaurants.convertArrayList())
                val greetingFragment = GreetingFragment.newInstance(greetings.convertArrayList())
                val rehabFragment = RehabFragment.newInstance(rehab.convertArrayList())
                adapter.add(getString(R.string.show), showFragment)
                adapter.add(getString(R.string.attraction), attractionFragment)
                adapter.add(getString(R.string.restaurant), restaurantFragment)
                adapter.add(getString(R.string.greeting), greetingFragment)
                adapter.add(getString(R.string.rehab), rehabFragment)
                binding.pager.adapter = adapter
            }
        } catch (e: Exception) {
            Toast.makeText(activity, R.string.no_data, Toast.LENGTH_SHORT).show()
        }
        binding.progress.visibility = View.GONE
    }

    private fun getTDS() = launch(UI) {
        binding.progress.visibility = View.VISIBLE
        try {
            val isClose = async { StatusApi.isCloseTds() }.await()
            if (isClose) {
                val showFragment = ShowScheduleFragment.newInstance(park)
                val rehab = async(CommonPool) { RehabApi.getTds() }.await()
                val rehabFragment = RehabFragment.newInstance(rehab.convertArrayList())
                adapter.add(getString(R.string.show), showFragment)
                adapter.add(getString(R.string.rehab), rehabFragment)
            } else {
                val showJob = async(CommonPool) { ShowApi.getRealtimeTds(pref.cookie) }
                val attractionJob = async(CommonPool) { AttractionApi.getTds(pref.cookie) }
                val restaurantJob = async(CommonPool) { RestaurantApi.getTds(pref.cookie) }
                val greetingJob = async(CommonPool) { GreetingApi.getTds(pref.cookie) }
                val rehabJob = async(CommonPool) { RehabApi.getTds() }
                val shows = showJob.await()
                val attractions = attractionJob.await()
                val restaurants = restaurantJob.await()
                val greetings = greetingJob.await()
                val rehab = rehabJob.await()
                val showFragment = RealtimeShowFragment.newInstance(shows.convertArrayList())
                val attractionFragment = AttractionFragment.newInstance(attractions.convertArrayList())
                val restaurantFragment = RestaurantFragment.newInstance(restaurants.convertArrayList())
                val greetingFragment = GreetingFragment.newInstance(greetings.convertArrayList())
                val rehabFragment = RehabFragment.newInstance(rehab.convertArrayList())
                adapter.add(getString(R.string.show), showFragment)
                adapter.add(getString(R.string.attraction), attractionFragment)
                adapter.add(getString(R.string.restaurant), restaurantFragment)
                adapter.add(getString(R.string.greeting), greetingFragment)
                adapter.add(getString(R.string.rehab), rehabFragment)
                binding.pager.adapter = adapter
            }
        } catch (e: Exception) {
            Toast.makeText(activity, R.string.no_data, Toast.LENGTH_SHORT).show()
        }
        binding.progress.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_pager, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_refresh -> {
                refresh()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}