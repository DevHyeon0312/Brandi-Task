package com.devhyeon.kakaoimagesearch.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.devhyeon.kakaoimagesearch.R
import com.devhyeon.kakaoimagesearch.databinding.FragmentImageListBinding
import com.devhyeon.kakaoimagesearch.constants.API_KEY
import com.devhyeon.kakaoimagesearch.viewmodels.KakaoApiViewModel
import com.devhyeon.kakaoimagesearch.adapters.ImageListAdapter
import com.devhyeon.kakaoimagesearch.data.api.KakaoImageData
import com.devhyeon.kakaoimagesearch.view.base.BaseFragment
import com.devhyeon.kakaoimagesearch.data.livedata.ImageSearchLiveData
import com.devhyeon.kakaoimagesearch.constants.error.NETWORK_ERROR
import com.devhyeon.kakaoimagesearch.constants.error.UNKNOWN_ERROR
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
    private var imageListAdapter: ImageListAdapter? = ImageListAdapter(this)

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

    /** 데이터 init */
    override fun init() {
        binding.rvImageList.adapter = imageListAdapter
        page = 1
        showContents()
    }


    /** View 생성이 완료됨 */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addListener()
    }

    /** 사용되는 Listener */
    private fun addListener() {
        //새로고침 ClickListener
        binding.btnRefresh.setOnClickListener {
            kakaoApiViewModel.loadSearchImageData(lifecycleScope, query,sort,page,size, API_KEY)
        }
        //추가검색에서 에러발생시 새로고침 ClickListener
        binding.btnLoaderRefresh.setOnClickListener {
            kakaoApiViewModel.loadSearchImageData(lifecycleScope, query,sort,page,size, API_KEY)
        }
    }

    /** 사용되는 Observer 추가 */
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
                            imageListAdapter!!.clearItem()
                            showContents()
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

    /** 로딩 시작시 보여주는 화면 */
    private fun showLoader() {
        if(page == 1) {
            BindingView.Show(binding).firstLoaderView()
        } else {
            BindingView.Show(binding).moreLoaderView()
        }
    }

    /** 에러 발생시에 보여주는 화면 */
    private fun showError(errorCode : Int) {
        if(page == 1) {
            BindingView.Set(binding).errorMessage(errorCode,this@ImageListFragment)
            BindingView.Show(binding).firstErrorView()
        } else {
            BindingView.Show(binding).moreErrorView()
        }
    }

    /** 검색 성공시에 보여주는 화면 */
    private fun showContents() {
        if(imageListAdapter!!.imageList.isEmpty()) {
            BindingView.Show(binding).emptyView()
        } else {
            BindingView.Show(binding).notEmptyView()
        }
    }

    /** Observer 등에서 View 의 Visibility , Value 등의 직접변경을 막기 위해 작성
     * showLoader, showError, showContents 에서만 아래에 코드를 사용하기 때문에, 이 부분을 수정하면 해당 경우에 보여지는 View 를 전체적으로 변경 가능
     * ps. 변경코드를 직접 작성하는 코드들로 유지보수시에 고려해야하는 사항이 많아지는 부분을 사전에 예방하기 위함
     * */
    private sealed class BindingView(val binding: FragmentImageListBinding) {
        /** Visibility 에 대한 처리 */
        class Show(binding: FragmentImageListBinding) : BindingView(binding) {
            /* 최초검색 로딩 */
            fun firstLoaderView() {
                hideView()
                binding.loaderView.toVisible()
            }
            /* 추가검색 로딩 */
            fun moreLoaderView() {
                hideView()
                binding.contentsView.toVisible()
                binding.rvImageLoader.toVisible()
            }
            /* 검색결과 없음 */
            fun emptyView() {
                hideView()
                binding.emptyView.toVisible()
            }
            /* 검색결과 있음 */
            fun notEmptyView() {
                hideView()
                binding.contentsView.toVisible()
            }
            /* 최초검색시 에러 */
            fun firstErrorView() {
                hideView()
                binding.errorView.toVisible()
            }
            /* 더 불러올 때 에러 */
            fun moreErrorView() {
                hideView()
                binding.contentsView.toVisible()
                binding.btnLoaderRefresh.toVisible()
            }
            /* 뷰 숨기기 */
            private fun hideView() {
                binding.loaderView.toGone()
                binding.contentsView.toGone()
                binding.rvImageLoader.toGone()
                binding.btnLoaderRefresh.toGone()
                binding.emptyView.toGone()
                binding.errorView.toGone()
            }
        }

        /** 값에 대한 처리 */
        class Set(binding: FragmentImageListBinding) : BindingView(binding) {
            /* 에러메시지 수정 */
            fun errorMessage(errorCode : Int, fragment: Fragment) {
                when(errorCode) {
                    UNKNOWN_ERROR -> {
                        binding.tvErrorMessage.text = fragment.getString(R.string.unknown_error)
                    }
                    NETWORK_ERROR -> {
                        binding.tvErrorMessage.text = fragment.getString(R.string.network_disconnect)
                    }
                }
            }
        }
    }

}