package com.devhyeon.kakaoimagesearch.network.kakao.repository

import com.devhyeon.kakaoimagesearch.network.kakao.reponse.KakaoImageResponse
import com.devhyeon.kakaoimagesearch.network.kakao.service.KakaoApiService

class KakaoRepositoryImp (private val kakaoApiService: KakaoApiService) : KakaoRepository {

    override suspend fun getSearchImage(
        query: String,
        sort: String,
        page: Int,
        size: Int,
        apiKey: String
    ): KakaoImageResponse {
        return kakaoApiService.getSearchImage(query, sort, page, size, apiKey)
    }

}