package com.devhyeon.kakaoimagesearch.ui.activities.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * Activity Class 에서 상속받는 추상클래스
 * 1. ViewBinding
 * 2. Observer
 * 에 대해 반드시 작성
 * */
abstract class BaseActivity : AppCompatActivity() {
    //viewBinding
    protected abstract fun initViewBinding()
    protected abstract fun getViewRoot() : View
    //옵저버
    protected abstract fun addObserver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        setContentView(getViewRoot())
        addObserver()
    }
}