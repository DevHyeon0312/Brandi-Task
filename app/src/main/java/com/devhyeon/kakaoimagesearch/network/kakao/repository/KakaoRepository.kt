package com.devhyeon.kakaoimagesearch.network.kakao.repository

import com.devhyeon.kakaoimagesearch.network.kakao.reponse.ImageResponse
import retrofit2.Call

interface KakaoRepository {
    /**
     *  @param(query) : String  :   검색어
     *  @param(sort)  : String  :   정렬
     *  @param(page)  : Int     :   페이지
     *  @param(size)  : Int     :   페이지당 개수
     *  */
    suspend fun getSearchImage(query: String, sort: String, page: Int, size: Int, apiKey: String) : ImageResponse
}