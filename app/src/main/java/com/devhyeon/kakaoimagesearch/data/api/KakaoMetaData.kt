package com.devhyeon.kakaoimagesearch.data.api

import com.google.gson.annotations.SerializedName


/**
 * @param(total_count)      : Int       : 검색된 문서 수
 * @param(pageable_count)   : Int       : total_count 중 노출 가능 문서 수
 * @param(is_end)           : Boolean   : 현재 페이지가 마지막 페이지인지 여부, 값이 false면 page를 증가시켜 다음 페이지를 요청할 수 있음
 * */

data class KakaoMetaData(
    @SerializedName("total_count")      val total_count      : Int,
    @SerializedName("pageable_count")   val pageable_count   : Int,
    @SerializedName("is_end")           val is_end           : Boolean
)