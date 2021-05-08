package com.devhyeon.kakaoimagesearch.utils

import android.content.Context
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