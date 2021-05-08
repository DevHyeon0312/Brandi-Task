package com.devhyeon.kakaoimagesearch.utils

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi

/** 네트워크 연결 여부 */
//코드를 호출하는 순간에 네트워크 연결 여부를 반환합니다.
fun checkNetworkState(context: Context) : Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if(Build.VERSION.SDK_INT < 23 ) {
        val activeNetwork = connectivityManager.allNetworks
        return (activeNetwork.isNotEmpty())
    } else {
        val activeNetwork = connectivityManager.activeNetwork
        return (activeNetwork != null)
    }
}