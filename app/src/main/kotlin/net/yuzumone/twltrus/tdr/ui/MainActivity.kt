package net.yuzumone.twltrus.tdr.ui

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import net.yuzumone.twltrus.tdr.R
import net.yuzumone.twltrus.tdr.R.string.refresh
import net.yuzumone.twltrus.tdr.data.api.CookieApi
import net.yuzumone.twltrus.tdr.data.api.StatusApi
import net.yuzumone.twltrus.tdr.databinding.ActivityMainBinding
import net.yuzumone.twltrus.tdr.model.Park
import net.yuzumone.twltrus.tdr.ui.top.TopFragment
import net.yuzumone.twltrus.tdr.utils.PrefUtil
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                binding.toolbar.elevation = 0F
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                binding.toolbar.elevation = 8F
                setTitle(R.string.app_name)
            }
        }
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.content, TopFragment()).commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
