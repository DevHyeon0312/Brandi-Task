package com.devhyeon.kakaoimagesearch.viewmodels

import android.content.Context
import androidx.lifecycle.*
import com.devhyeon.kakaoimagesearch.R
import com.devhyeon.kakaoimagesearch.constants.MS_500
import com.devhyeon.kakaoimagesearch.constants.NETWORK_DISCONNECT
import com.devhyeon.kakaoimagesearch.constants.error.UNKNOWN_ERROR
import com.devhyeon.kakaoimagesearch.utils.Status
import com.devhyeon.kakaoimagesearch.utils.checkNetworkState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.annotations.NotNull

class IntroViewModel : ViewModel() {
    private val _networkState = MutableLiveData<Status<String>>()
    val networkState: LiveData<Status<String>> get() = _networkState

    private val _delayState = MutableLiveData<Status<Boolean>>()
    val delayState: LiveData<Status<Boolean>> get() = _delayState

    private val delayTime = MS_500

    /** 네트워크 연결상태 검사 진행 */
    fun runCheckNetworkState(@NotNull context: Context) {
        if(checkNetworkState(context)) {
            _networkState.value = Status.Success(context.getString(R.string.network_connect))
        } else {
            _networkState.value = Status.Failure(NETWORK_DISCONNECT, context.getString(R.string.network_disconnect))
        }
    }

    /** delayTime 밀리초간 Run 상태 유지 */
    fun runDelay() {
        viewModelScope.launch {
            runCatching {
                _delayState.value = Status.Run(false)
                delay(delayTime)
            }.onSuccess {
                _delayState.value = Status.Success(true)
            }.onFailure {
                _delayState.value = Status.Failure(UNKNOWN_ERROR, it.message!!)
            }
        }
    }

}