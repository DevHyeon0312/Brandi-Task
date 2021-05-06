package com.devhyeon.kakaoimagesearch.network.kakao

import com.devhyeon.kakaoimagesearch.network.kakao.reponse.ImageResponse
import com.devhyeon.kakaoimagesearch.network.kakao.repository.KakaoRepository
import retrofit2.Call

class KakaoAPI (private val kakaoRepository: KakaoRepository) {

    /**
     *  @param(query) : String  :   검색어
     *  @param(sort)  : String  :   정렬
     *  @param(page)  : Int     :   페이지
     *  @param(size)  : Int     :   페이지당 개수
     *  @sample loadSearchImageData("영현","accuracy",1,10)
     *  */
    suspend fun loadSearchImageData(
        query: String,
        sort: String,
        page: Int,
        size: Int,
        apiKey: String
    ) : ImageResponse {
        return kakaoRepository.getSearchImage(query, sort, page, size, apiKey)
    }

}