package com.devhyeon.kakaoimagesearch.ui.activities.main

import android.os.Bundle
import android.view.View
import com.devhyeon.kakaoimagesearch.databinding.ActivityMainBinding
import com.devhyeon.kakaoimagesearch.ui.activities.base.BaseActivity
import com.devhyeon.kakaoimagesearch.ui.fragments.imagesearch.ImageListFragment
import com.devhyeon.kakaoimagesearch.ui.fragments.imagesearch.ImageSearchFragment

class MainActivity : BaseActivity() {
    companion object {
        private val TAG = MainActivity::class.java.name
    }

    private lateinit var binding : ActivityMainBinding

    //화면에 보여줄 Fragment
    private val imageListFragment by lazy { ImageListFragment() }
    private val imageSearchFragment by lazy { ImageSearchFragment() }

    override fun initViewBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
    }

    override fun getViewRoot(): View {
        return binding.root
    }

    override fun addObserver() { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addFragment() //Fragment 삽입
    }

    /** Fragment 삽입 */
    private fun addFragment() {
        supportFragmentManager.beginTransaction().add(binding.flImageList.id, imageListFragment).commit()
        supportFragmentManager.beginTransaction().add(binding.flImageSearch.id, imageSearchFragment).commit()
    }
}