package com.devhyeon.kakaoimagesearch.view.activities

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.devhyeon.kakaoimagesearch.databinding.ActivityImageDetailBinding
import com.devhyeon.kakaoimagesearch.utils.*
import com.devhyeon.kakaoimagesearch.view.base.BaseActivity

/**
 * 이미지 상세화면 : 전체화면 표시
 * - 세로가 긴경우에 스크롤 되는지 여부는 기기를 가로모드로 확인하면 쉽게 확인 가능합니다.
 * */
class ImageDetailActivity : BaseActivity() {
    companion object {
        private val TAG = ImageDetailActivity::class.java.name
    }

    var imageUrl : String? = null
    var displaySiteName : String? = null
    var dateTime : String? = null
    var width : Int = 0
    var height : Int = 0

    private lateinit var binding : ActivityImageDetailBinding

    override fun initViewBinding() {
        binding = ActivityImageDetailBinding.inflate(layoutInflater)
    }

    override fun getViewRoot(): View {
        return binding.root
    }

    override fun addObserver() { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imageUrl = intent.getStringExtra("image_url")
        displaySiteName = intent.getStringExtra("display_sitename")
        dateTime = intent.getStringExtra("datetime")
        height = intent.getIntExtra("height",0)
        width = intent.getIntExtra("width",0)

        //이미지 로드
        Glide
            .with(this@ImageDetailActivity)
            .load(imageUrl!!)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.ivImage)

        binding.tvDisplaySiteName.text = displaySiteName
        binding.tvDateTime.text = dateTime
    }
}