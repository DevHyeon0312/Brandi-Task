package com.devhyeon.kakaoimagesearch.network.kakao.data

import com.google.gson.annotations.SerializedName


/**
 * @param(total_count)      : Int       : 검색된 문서 수
 * @param(pageable_count)   : Int       : total_count 중 노출 가능 문서 수
 * @param(is_end)           : Boolean   : 현재 페이지가 마지막 페이지인지 여부, 값이 false면 page를 증가시켜 다음 페이지를 요청할 수 있음
 * */

data class MetaData(
    @SerializedName("total_count")      val collection          : Int,
    @SerializedName("pageable_count")   val thumbnail_url       : Int,
    @SerializedName("is_end")           val image_url           : Boolean
)