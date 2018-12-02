package net.yuzumone.twltrus.tdr.ui.top

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import net.yuzumone.twltrus.tdr.R
import net.yuzumone.twltrus.tdr.data.api.CookieApi
import net.yuzumone.twltrus.tdr.data.api.StatusApi
import net.yuzumone.twltrus.tdr.databinding.FragmentTopBinding
import net.yuzumone.twltrus.tdr.model.Park
import net.yuzumone.twltrus.tdr.ui.detail.DetailFragment
import net.yuzumone.twltrus.tdr.utils.PrefUtil
import java.util.*

class TopFragment : Fragment() {

    private lateinit var binding: FragmentTopBinding
    private val pref by lazy { PrefUtil(activity!!) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentTopBinding.inflate(inflater, container, false)
        getStatus()
        binding.buttonTdl.setOnClickListener {
            val fragment = DetailFragment.newInstance(Park.TDL)
            requireFragmentManager().run {
                beginTransaction().replace(R.id.content, fragment).addToBackStack(null).commit()
            }
        }
        binding.buttonTds.setOnClickListener {
            val fragment = DetailFragment.newInstance(Park.TDS)
            requireFragmentManager().run {
                beginTransaction().replace(R.id.content, fragment).addToBackStack(null).commit()
            }
        }
        return binding.root
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