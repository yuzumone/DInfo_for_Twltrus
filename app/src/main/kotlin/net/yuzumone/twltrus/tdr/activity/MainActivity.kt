package net.yuzumone.twltrus.tdr.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import net.yuzumone.twltrus.tdr.R
import net.yuzumone.twltrus.tdr.api.StatusApi
import net.yuzumone.twltrus.tdr.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit private var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        getStatus()
        binding.buttonTdl.setOnClickListener {
            PagerActivity.createIntent(this, PagerActivity.Park.TDR)
        }
        binding.buttonTds.setOnClickListener {
            PagerActivity.createIntent(this, PagerActivity.Park.TDS)
        }
    }

    private fun getStatus() = launch(UI) {
        try {
            val tdlJob = async { StatusApi.getTdlStatus() }
            val tdsJob = async { StatusApi.getTdsStatus() }
            val tdlStatus = tdlJob.await()
            val tdsStatus = tdsJob.await()
            binding.textTdlStatus.text = tdlStatus
            binding.textTdsStatus.text = tdsStatus
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
