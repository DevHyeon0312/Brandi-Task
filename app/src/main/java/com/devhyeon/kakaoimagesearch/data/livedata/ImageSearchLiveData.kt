package com.devhyeon.kakaoimagesearch.data.livedata

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import com.devhyeon.kakaoimagesearch.utils.Status

/** 이미지 검색어를 공유하기 위한 MutableLiveData */
class ImageSearchLiveData : MutableLiveData<Status<String>>() {
    companion object {
        private lateinit var sInstance: ImageSearchLiveData
        @MainThread
        fun get(): ImageSearchLiveData {
            sInstance = if (Companion::sInstance.isInitialized) sInstance else ImageSearchLiveData()
            return sInstance
        }
    }
}