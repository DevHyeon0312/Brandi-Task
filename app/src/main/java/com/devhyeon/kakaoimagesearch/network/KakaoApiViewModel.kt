package com.devhyeon.kakaoimagesearch.network

import androidx.lifecycle.*
import com.devhyeon.kakaoimagesearch.network.kakao.KakaoAPI
import com.devhyeon.kakaoimagesearch.network.kakao.reponse.ImageResponse
import com.devhyeon.kakaoimagesearch.utils.Status
import kotlinx.coroutines.launch

class KakaoApiViewModel constructor(private val kakaoAPI: KakaoAPI) : ViewModel() {
    //API 요청에 따른 상태 및 결과
    private val _ImageResponse = MutableLiveData<Status<ImageResponse>>()
    val imageResponse : LiveData<Status<ImageResponse>> get() = _ImageResponse

    /** 이미지 검색결과 요청 */
    fun loadSearchImageData(scope: LifecycleCoroutineScope, query: String, sort: String, page: Int, size: Int, apiKey: String) {
        scope.launch {
            var response : ImageResponse? = null
            runCatching {
                _ImageResponse.value = Status.Run(null)
                response = kakaoAPI.loadSearchImageData(query, sort, page, size, apiKey)
            }.onSuccess {
                _ImageResponse.value = Status.Success(response!!)
            }.onFailure {
                _ImageResponse.value = Status.Failure(it.hashCode(), it.message!!)
            }
        }
    }
}