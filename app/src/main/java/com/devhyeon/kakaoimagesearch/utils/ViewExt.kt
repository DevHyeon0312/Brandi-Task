package com.devhyeon.kakaoimagesearch.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.bumptech.glide.Glide

/** View Visible */
fun View.toVisible() {
    this.visibility = View.VISIBLE
}
/** View Gone */
fun View.toGone() {
    this.visibility = View.GONE
}
/** View Invisible */
fun View.toInvisible() {
    this.visibility = View.GONE
}

/** 이미지 가져오기 */
fun ImageView.loadImage(url: String) {
    Glide
        .with(this)
        .load(url)
        .into(this)
}

/** 키보드 숨기기 */
fun View.hideKeyboard() {
    val context = this.context
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(this.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}

/** 디바이스 가로 크기 구해오기 */
val View.deviceWidth : Int
    get() {
        val displayMetrics = DisplayMetrics()
        val display = this.context.display
        display?.getRealMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

/** 뷰 가로세로 사이즈 변경 */
fun View.setSize(value: Int) {
    val lp = layoutParams
    lp?.let {
        lp.width = value
        lp.height = value
        layoutParams = lp
    }
}