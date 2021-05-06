package com.devhyeon.kakaoimagesearch.network.kakao.repository

import com.devhyeon.kakaoimagesearch.network.kakao.reponse.ImageResponse
import com.devhyeon.kakaoimagesearch.network.kakao.service.KakaoApiService
import retrofit2.Call

class KakaoRepositoryImp (private val kakaoApiService: KakaoApiService) : KakaoRepository {

    override suspend fun getSearchImage(
        query: String,
        sort: String,
        page: Int,
        size: Int,
        apiKey: String
    ): ImageResponse {
        return kakaoApiService.getSearchImage(query, sort, page, size, apiKey)
    }

}