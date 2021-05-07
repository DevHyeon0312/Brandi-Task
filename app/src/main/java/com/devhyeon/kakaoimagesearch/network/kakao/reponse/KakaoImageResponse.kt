package com.devhyeon.kakaoimagesearch.network.kakao.reponse

import com.devhyeon.kakaoimagesearch.data.api.KakaoImageData
import com.devhyeon.kakaoimagesearch.data.api.KakaoMetaData
import com.google.gson.annotations.SerializedName

/**
 * @param(meta)       : MetaData
 * @param(documents)  : List<ImageData>
 * */
data class KakaoImageResponse(
    @SerializedName("meta")        val meta      : KakaoMetaData,
    @SerializedName("documents")   val documents : List<KakaoImageData>
)