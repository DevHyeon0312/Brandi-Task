package com.devhyeon.kakaoimagesearch.utils.util

import android.content.Context
import android.net.ConnectivityManager

/** 네트워크 연결 여부 */
//코드를 호출하는 순간에 네트워크 연결 여부를 반환합니다.
fun checkNetworkState(context: Context) : Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.allNetworks
    return (activeNetwork.isNotEmpty())
}