package com.devhyeon.kakaoimagesearch.network.kakao.reponse

import com.devhyeon.kakaoimagesearch.network.kakao.data.ImageData
import com.devhyeon.kakaoimagesearch.network.kakao.data.MetaData
import com.google.gson.annotations.SerializedName

/**
 * @param(meta)       : MetaData
 * @param(documents)  : List<ImageData>
 * */
data class ImageResponse(
    @SerializedName("meta")        val meta      : MetaData,
    @SerializedName("documents")   val documents : List<ImageData>
)