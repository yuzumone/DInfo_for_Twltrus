package net.yuzumone.twltrus.tdr.ui.top

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import net.yuzumone.twltrus.tdr.data.api.CookieApi
import net.yuzumone.twltrus.tdr.data.api.StatusApi
import net.yuzumone.twltrus.tdr.databinding.FragmentTopBinding
import net.yuzumone.twltrus.tdr.ui.MainViewModel
import net.yuzumone.twltrus.tdr.utils.PrefUtil
import java.util.*

class TopFragment : Fragment() {

    private lateinit var binding: FragmentTopBinding
    private lateinit var mainViewModel: MainViewModel
    private val pref by lazy { PrefUtil(activity!!) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        binding = FragmentTopBinding.inflate(inflater, container, false).apply {
            viewModel = mainViewModel
        }
        getStatus()
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