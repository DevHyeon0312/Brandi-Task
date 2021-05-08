package com.devhyeon.kakaoimagesearch.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.devhyeon.kakaoimagesearch.R
import com.devhyeon.kakaoimagesearch.databinding.ItemImageBinding
import com.devhyeon.kakaoimagesearch.data.api.KakaoImageData
import com.devhyeon.kakaoimagesearch.utils.*

class ImageListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var imageList: MutableList<KakaoImageData> = mutableListOf()

    /** 스크롤 감지 LiveData */
    private val _scrollState = MutableLiveData<Status<Boolean>>()
    val scrollState: LiveData<Status<Boolean>> get() = _scrollState
    fun scrollStateRun() {
        _scrollState.value = Status.Run()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemQuestionBinding = DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context), R.layout.item_image, parent, false
        )
        return TrackListViewHolder(itemQuestionBinding)
    }

    override fun getItemCount(): Int = if (imageList.isNullOrEmpty()) 0 else imageList.size

    private fun getItem(position: Int): KakaoImageData = imageList[position]

    fun addItem(list: List<KakaoImageData>) {
        imageList.addAll(list)
        notifyItemRangeChanged(itemCount, list.size)
    }

    fun createItem(list: List<KakaoImageData>) {
        imageList.clear()
        imageList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TrackListViewHolder).onBind(getItem(position))
        if(position == imageList.size-1) {
            if(_scrollState.value is Status.Run ) {
                _scrollState.value = (Status.Success(true))
            }
        }
    }
    
    private inner class TrackListViewHolder(private val viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
        fun onBind(imgData: KakaoImageData) {
            (viewDataBinding as ItemImageBinding).imgData = imgData
            //뷰 사이즈 재조정
            val viewSize = viewDataBinding.ivThumbnail.deviceWidth / 3
            viewDataBinding.ivThumbnail.setSize(viewSize)

            //이미지 load
            viewDataBinding.ivThumbnail.loadImage(imgData.thumbnail_url)
        }
    }
}