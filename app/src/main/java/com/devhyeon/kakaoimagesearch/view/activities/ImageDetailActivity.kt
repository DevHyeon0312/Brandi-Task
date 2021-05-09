package com.devhyeon.kakaoimagesearch.view.activities

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.devhyeon.kakaoimagesearch.R
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

    var imageUrl : String? = null           //이미지 URL
    var displaySiteName : String? = null    //이미지 출처
    var dateTime : String? = null           //이미지 등록시간

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

        init()
        loadImage()
        addListener()
    }

    /** 데이터 init  */
    private fun init() {
        imageUrl = intent.getStringExtra("image_url")
        displaySiteName = intent.getStringExtra("display_sitename")
        dateTime = intent.getStringExtra("datetime")
    }

    /** 사용하는 Listener */
    private fun addListener() {
        binding.btnRefresh.setOnClickListener {
            loadImage()
        }
    }

    /** 이미지 로드 */
    private fun loadImage() {
        binding.errorView.toGone()
        binding.loaderView.toVisible()

        Glide
            .with(this@ImageDetailActivity)
            .load(imageUrl!!)
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .listener(createLogListener())
            .into(binding.ivImage)
    }

    /** 이미지 로드 CallBack */
    private fun createLogListener(): RequestListener<Drawable> {
        return object : RequestListener<Drawable> {
            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                binding.loaderView.toGone()
                binding.errorView.toGone()
                binding.tvDisplaySiteName.text = displaySiteName
                binding.tvDateTime.text = dateTime
                return false
            }

            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                binding.loaderView.toGone()
                binding.errorView.toVisible()
                binding.tvErrorMessage.text = getString(R.string.image_error)
                return false
            }
        }
    }
}