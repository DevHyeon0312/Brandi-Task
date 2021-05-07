package com.devhyeon.kakaoimagesearch.ui.fragments.imagesearch

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.devhyeon.kakaoimagesearch.databinding.FragmentImageSearchBinding
import com.devhyeon.kakaoimagesearch.define.MS_1000
import com.devhyeon.kakaoimagesearch.define.error.UNKNOWN_ERROR
import com.devhyeon.kakaoimagesearch.ui.fragments.base.BaseFragment
import com.devhyeon.kakaoimagesearch.ui.livedata.ExpansionLiveData
import com.devhyeon.kakaoimagesearch.utils.Status
import kotlinx.coroutines.*
import java.util.*

/** 이미지 검색 UI Fragment */
class ImageSearchFragment : BaseFragment() {
    companion object {
        private val TAG = ImageSearchFragment::class.java.name
    }

    //바인딩
    private var _binding: FragmentImageSearchBinding? = null
    private val binding get() = _binding!!

    private val imageLiveData = ExpansionLiveData.get("ImageSearch")

    //텍스트변경 코루틴 Job
    var job : Job? = null

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        _binding = FragmentImageSearchBinding.inflate(inflater, container, false)
    }

    override fun getViewRoot(): View {
        return binding.root
    }


    override fun init() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                changeTextCancel()
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                changeText(s)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun addObserver() {

    }


    /** 텍스트 변경시작 */
    //현재 실행중인 코루틴 작업을 정지한다.
    private fun changeTextCancel() {
        if(job != null && job!!.isActive) {
            job!!.cancel()
        }
    }

    /** 텍스트 변경완료 */
    //코루틴 작업을 시작한다.
    private fun changeText(s: Editable?) {
        job = lifecycleScope.launch {
            runCatching {
                imageLiveData.postValue(Status.Run(""))
                delay(MS_1000)
            }.onSuccess {
                imageLiveData.postValue(Status.Success(s.toString()))
            }.onFailure {
                imageLiveData.postValue(Status.Failure(UNKNOWN_ERROR,it.message!!))
            }
        }
    }
}