package com.devhyeon.kakaoimagesearch.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.devhyeon.kakaoimagesearch.databinding.FragmentImageListBinding
import com.devhyeon.kakaoimagesearch.define.API_KEY
import com.devhyeon.kakaoimagesearch.viewmodels.KakaoApiViewModel
import com.devhyeon.kakaoimagesearch.adapters.ImageListAdapter
import com.devhyeon.kakaoimagesearch.view.base.BaseFragment
import com.devhyeon.kakaoimagesearch.data.livedata.ImageSearchLiveData
import com.devhyeon.kakaoimagesearch.utils.util.Status
import com.devhyeon.kakaoimagesearch.utils.util.toGone
import com.devhyeon.kakaoimagesearch.utils.util.toVisible
import org.koin.android.viewmodel.ext.android.viewModel

/** 이미지 검색결과 UI Fragment */
class ImageListFragment : BaseFragment() {
    companion object {
        private val TAG = ImageListFragment::class.java.name
    }

    //바인딩
    private var _binding: FragmentImageListBinding? = null
    private val binding get() = _binding!!

    //뷰모델
    private val kakaoApiViewModel: KakaoApiViewModel by viewModel()

    //검색어 LiveData
    private val imageLiveData = ImageSearchLiveData.get()

    //어댑터
    private var imageListAdapter: ImageListAdapter? = ImageListAdapter(this)

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        _binding = FragmentImageListBinding.inflate(inflater, container, false)
    }

    override fun getViewRoot(): View {
        return binding.root
    }

    override fun init() {
        binding.rvImageList.adapter = imageListAdapter
    }

    override fun onResume() {
        super.onResume()
    }

    override fun addObserver() {
        kakaoApiViewModel.imageResponse.observe(this@ImageListFragment, Observer {
            when(it) {
                is Status.Run -> {
                    binding.contentsView.toGone()
                    binding.rvImageLoader.toGone()
                    binding.emptyView.toGone()
                    binding.errorView.toGone()
                    binding.loaderView.toVisible()
                }
                is Status.Success -> {
                    imageListAdapter!!.imageList = it.data!!.documents
                    if(imageListAdapter!!.imageList.isEmpty()) {
                        binding.loaderView.toGone()
                        binding.contentsView.toGone()
                        binding.rvImageLoader.toGone()
                        binding.emptyView.toVisible()
                        binding.errorView.toGone()

                    } else {
                        binding.loaderView.toGone()
                        binding.contentsView.toVisible()
                        binding.rvImageLoader.toGone()
                        binding.emptyView.toGone()
                        binding.errorView.toGone()
                    }
                }
                is Status.Failure -> {
                    binding.loaderView.toGone()
                    binding.contentsView.toGone()
                    binding.rvImageLoader.toGone()
                    binding.emptyView.toGone()
                    binding.errorView.toVisible()
                }
            }
        })

        imageLiveData.observe(this@ImageListFragment, Observer {
            when(it) {
                is Status.Run -> {
                    println("ListFragment: RUN")
                }
                is Status.Success -> {
                    if (it.data!!.toString().isNotEmpty()) {
                        kakaoApiViewModel.loadSearchImageData(lifecycleScope,it.data.toString(),"accuracy",1,30, API_KEY)
                    }
                }
                is Status.Failure -> { }
            }
        })

    }

}