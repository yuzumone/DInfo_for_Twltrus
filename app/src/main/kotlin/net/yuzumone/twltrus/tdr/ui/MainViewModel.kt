package net.yuzumone.twltrus.tdr.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.yuzumone.twltrus.tdr.model.Park

class MainViewModel : ViewModel() {

    val transaction = MutableLiveData<Park>()

    fun actionTdl() {
        transaction.value = Park.TDL
    }

    fun actionTds() {
        transaction.value = Park.TDS
    }
}