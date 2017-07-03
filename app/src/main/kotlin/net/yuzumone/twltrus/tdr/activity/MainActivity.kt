package net.yuzumone.twltrus.tdr.activity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import net.yuzumone.twltrus.tdr.R
import net.yuzumone.twltrus.tdr.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit private var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        binding.buttonTdl.setOnClickListener {
            val tdlIntent = Intent(this, TDLActivity::class.java)
            startActivity(tdlIntent)
        }
        binding.buttonTds.setOnClickListener {
            val tdsIntent = Intent(this, TDSActivity::class.java)
            startActivity(tdsIntent)
        }
    }
}
