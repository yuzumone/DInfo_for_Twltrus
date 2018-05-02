package net.yuzumone.twltrus.tdr.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import net.yuzumone.twltrus.tdr.R
import net.yuzumone.twltrus.tdr.adapter.PagerAdapter
import net.yuzumone.twltrus.tdr.api.*
import net.yuzumone.twltrus.tdr.databinding.ActivityPagerBinding
import net.yuzumone.twltrus.tdr.extension.convertArrayList
import net.yuzumone.twltrus.tdr.fragment.*
import net.yuzumone.twltrus.tdr.model.Park
import net.yuzumone.twltrus.tdr.utils.PrefUtil

class PagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPagerBinding
    private val park: Park by lazy {
        intent.extras.getSerializable(ARG_PARK) as Park
    }
    private val adapter: PagerAdapter by lazy {
        PagerAdapter(supportFragmentManager)
    }
    private val pref by lazy { PrefUtil(this) }

    companion object {
        private const val ARG_PARK = "park"
        fun createIntent(context: Context, park: Park) {
            val intent = Intent(context, PagerActivity::class.java)
            intent.putExtra(ARG_PARK, park)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pager)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.pager.adapter = adapter
        binding.tab.setupWithViewPager(binding.pager)
        refresh()
    }

    private fun refresh() {
        adapter.clear()
        if (park == Park.TDL) {
            setTitle(R.string.tdl)
            getTDL()
        } else if (park == Park.TDS) {
            setTitle(R.string.tds)
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
                val restaurantJob = async(CommonPool) { RestaurantApi.getTdl() }
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
            }
        } catch (e: Exception) {
            Toast.makeText(this@PagerActivity, R.string.no_data, Toast.LENGTH_SHORT).show()
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
                val restaurantJob = async(CommonPool) { RestaurantApi.getTds() }
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
            }
        } catch (e: Exception) {
            Toast.makeText(this@PagerActivity, R.string.no_data, Toast.LENGTH_SHORT).show()
        }
        binding.progress.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_pager, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.menu_refresh -> {
                refresh()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}