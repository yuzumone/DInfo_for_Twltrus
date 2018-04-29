package net.yuzumone.twltrus.tdr.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import net.yuzumone.twltrus.tdr.R
import net.yuzumone.twltrus.tdr.api.CookieApi
import net.yuzumone.twltrus.tdr.api.StatusApi
import net.yuzumone.twltrus.tdr.databinding.ActivityMainBinding
import net.yuzumone.twltrus.tdr.model.Park
import net.yuzumone.twltrus.tdr.utils.PrefUtil
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val pref by lazy { PrefUtil(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        getStatus()
        binding.buttonTdl.setOnClickListener {
            PagerActivity.createIntent(this, Park.TDL)
        }
        binding.buttonTds.setOnClickListener {
            PagerActivity.createIntent(this, Park.TDS)
        }
    }

    private fun getStatus() = launch(UI) {
        try {
            binding.progress.visibility = View.VISIBLE
            if (pref.shouldGetCookie) {
                val cookieJob = async { CookieApi.getCookie() }
                val cookie = cookieJob.await()
                pref.date = Date()
                pref.cookie = cookie
            }
            val tdlJob = async { StatusApi.getTdlStatus() }
            val tdsJob = async { StatusApi.getTdsStatus() }
            val tdlStatus = tdlJob.await()
            val tdsStatus = tdsJob.await()
            binding.progress.visibility = View.GONE
            binding.textTdlStatus.text = tdlStatus
            binding.textTdsStatus.text = tdsStatus
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
