package com.devhyeon.kakaoimagesearch.network.kakao.data

import com.google.gson.annotations.SerializedName

/**
 * @param(collection)       : String    : 컬렉션
 * @param(thumbnail_url)    : String    : 미리보기 이미지 URL
 * @param(image_url)        : String    : 이미지 URL
 * @param(height)           : Int       : 이미지의 세로 길이
 * @param(width)            : Int       : 이미지의 가로 길이
 * @param(display_sitename) : String    : 출처
 * @param(doc_url)          : String    : 문서 URL
 * @param(datetime)         : String    : 문서 작성시간, ISO 8601 [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]
 * */
data class ImageData(
    @SerializedName("collection")       val collection          : String,
    @SerializedName("thumbnail_url")    val thumbnail_url       : String,
    @SerializedName("image_url")        val image_url           : String,
    @SerializedName("height")           val height              : Int,
    @SerializedName("width")            val width               : Int,
    @SerializedName("display_sitename") val display_sitename    : String,
    @SerializedName("doc_url")          val doc_url             : String,
    @SerializedName("datetime")         val datetime            : String
)