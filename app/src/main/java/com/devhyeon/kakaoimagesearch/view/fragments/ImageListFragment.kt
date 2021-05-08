package com.devhyeon.kakaoimagesearch.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.devhyeon.kakaoimagesearch.R
import com.devhyeon.kakaoimagesearch.databinding.FragmentImageListBinding
import com.devhyeon.kakaoimagesearch.define.API_KEY
import com.devhyeon.kakaoimagesearch.viewmodels.KakaoApiViewModel
import com.devhyeon.kakaoimagesearch.adapters.ImageListAdapter
import com.devhyeon.kakaoimagesearch.data.api.KakaoImageData
import com.devhyeon.kakaoimagesearch.view.base.BaseFragment
import com.devhyeon.kakaoimagesearch.data.livedata.ImageSearchLiveData
import com.devhyeon.kakaoimagesearch.define.error.NETWORK_ERROR
import com.devhyeon.kakaoimagesearch.define.error.UNKNOWN_ERROR
import com.devhyeon.kakaoimagesearch.utils.Status
import com.devhyeon.kakaoimagesearch.utils.toGone
import com.devhyeon.kakaoimagesearch.utils.toVisible
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
    private var imageListAdapter: ImageListAdapter? = ImageListAdapter()

    //API 요청에 필요한 데이터
    private val sort = "accuracy"   //고정 accuracy(정확도순) 또는 recency(최신순)
    private val size = 30           //고정
    private var page = 1            //1 ~ 50 까지 요청 가능. 단, isEnd 가 false 일때
    private var query = ""          //검색어

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
        page = 1

        binding.btnRefresh.setOnClickListener {
            kakaoApiViewModel.loadSearchImageData(lifecycleScope, query,sort,page,size, API_KEY)
        }
    }

    override fun addObserver() {
        /** API 결과 */
        kakaoApiViewModel.imageResponse.observe(this@ImageListFragment, Observer {
            when(it) {
                is Status.Run -> {
                    //해당 뷰 보여주기
                    showLoader()
                }
                is Status.Success -> {
                    //아이템 추가
                    val response = it.data!!
                    addItem(response.documents)
                    //해당 뷰 보여주기
                    showContents()
                    //추가조회가 가능하면, 스크롤 진행상태로 변경
                    if (!response.meta.is_end) {
                        imageListAdapter!!.scrollStateRun()
                    }
                }
                is Status.Failure -> {
                    //해당 뷰 보여주기
                    showError(it.errorCode!!)
                }
            }
        })

        /** 검색어 변경 감지 */
        var isRun = false
        imageLiveData.observe(this@ImageListFragment, Observer {
            when(it) {
                is Status.Run -> {
                    isRun = true
                }
                is Status.Success -> {
                    if(isRun) {
                        if (it.data!!.toString().isNotEmpty()) {
                            page = 1
                            query = it.data.toString()
                            kakaoApiViewModel.loadSearchImageData(lifecycleScope, query, sort, page, size, API_KEY)
                        } else {
                            showEmpty()
                        }
                    }
                }
                is Status.Failure -> {}
            }
        })

        /** Bind 결과 감지 */
        imageListAdapter!!.scrollState.observe(this@ImageListFragment, Observer {
            when(it) {
                is Status.Run -> {}
                is Status.Success -> {
                    if(it.data!! && page < 50) {
                        page += 1
                        query = it.data.toString()
                        kakaoApiViewModel.loadSearchImageData(lifecycleScope, query, sort, page, size, API_KEY)
                    }
                }
                is Status.Failure -> {}
            }
        })
    }

    /** 데이터 추가 */
    private fun addItem(list : List<KakaoImageData>) {
        if(page == 1) {
            imageListAdapter!!.createItem(list)
        } else {
            imageListAdapter!!.addItem(list)
        }
    }

    /** 로딩 시작 */
    private fun showLoader() {
        if(page == 1) {
            showFirstLoader()
        } else {
            showMoreLoader()
        }
    }

    /** 최초검색 로딩 */
    private fun showFirstLoader() {
        binding.loaderView.toVisible()
        binding.contentsView.toGone()
        binding.rvImageLoader.toGone()
        binding.emptyView.toGone()
        binding.errorView.toGone()
    }

    /** 추가검색 로딩 */
    private fun showMoreLoader() {
        binding.loaderView.toGone()
        binding.contentsView.toVisible()
        binding.rvImageLoader.toVisible()
        binding.emptyView.toGone()
        binding.errorView.toGone()
    }

    /** 검색 성공 */
    private fun showContents() {
        if(imageListAdapter!!.imageList.isEmpty()) {
            showEmpty()
        } else {
            showNotEmpty()
        }
    }

    /** 검색결과 없음 */
    private fun showEmpty() {
        binding.loaderView.toGone()
        binding.contentsView.toGone()
        binding.rvImageLoader.toGone()
        binding.emptyView.toVisible()
        binding.errorView.toGone()
    }

    /** 검색결과 있음 */
    private fun showNotEmpty() {
        binding.loaderView.toGone()
        binding.contentsView.toVisible()
        binding.rvImageLoader.toGone()
        binding.emptyView.toGone()
        binding.errorView.toGone()
    }

    /** 에러 */
    private fun showError(errorCode : Int) {
        setErrorMessage(errorCode)
        binding.loaderView.toGone()
        binding.contentsView.toGone()
        binding.rvImageLoader.toGone()
        binding.emptyView.toGone()
        binding.errorView.toVisible()
    }

    private fun setErrorMessage(errorCode : Int) {
        when(errorCode) {
            UNKNOWN_ERROR -> {
                binding.tvErrorMessage.text = getString(R.string.unknown_error)
            }
            NETWORK_ERROR -> {
                binding.tvErrorMessage.text = getString(R.string.network_disconnect)
            }
        }
    }
}