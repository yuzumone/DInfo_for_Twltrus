package net.yuzumone.twltrus.tdr.ui

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import net.yuzumone.twltrus.tdr.R
import net.yuzumone.twltrus.tdr.databinding.ActivityMainBinding
import net.yuzumone.twltrus.tdr.ui.detail.DetailFragment
import net.yuzumone.twltrus.tdr.ui.top.TopFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var model: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProviders.of(this).get(MainViewModel::class.java)
        model.transaction.observe(this, Observer {
            val fragment = DetailFragment.newInstance(it)
            supportFragmentManager.beginTransaction().replace(R.id.content, fragment)
                    .addToBackStack(null).commit()
        })
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
