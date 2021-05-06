package com.devhyeon.kakaoimagesearch.network.kakao.service

import com.devhyeon.kakaoimagesearch.define.KAKAO_SEARCH_IMAGE_URL
import com.devhyeon.kakaoimagesearch.network.kakao.reponse.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/** 카카오 API 서비스 */
interface KakaoApiService {

    /**
     *  @param(query) : String  :   검색어
     *  @param(sort)  : String  :   정렬
     *  @param(page)  : Int     :   페이지
     *  @param(size)  : Int     :   페이지당 개수
     *  @sample v2/search/image?query=영현&sort=accuracy&page=1&size=10
     *  */
    @GET(KAKAO_SEARCH_IMAGE_URL)
    suspend fun getSearchImage(
        @Query("query")          term     : String,
        @Query("sort")           sort     : String,
        @Query("page")           page     : Int,
        @Query("size")           size     : Int,
        @Header("Authorization") apiKey   : String
    ): ImageResponse

}