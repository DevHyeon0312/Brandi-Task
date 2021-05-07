package com.devhyeon.kakaoimagesearch.ui.livedata

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import com.devhyeon.kakaoimagesearch.utils.Status

class ExpansionLiveData(symbol: String) : MutableLiveData<Status<String>>() {

    companion object {
        private lateinit var sInstance: ExpansionLiveData
        @MainThread
        fun get(symbol: String): ExpansionLiveData {
            sInstance = if (::sInstance.isInitialized) sInstance else ExpansionLiveData(symbol)
            return sInstance
        }
    }

}