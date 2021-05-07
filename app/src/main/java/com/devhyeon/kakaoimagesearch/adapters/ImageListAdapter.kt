package com.devhyeon.kakaoimagesearch.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.devhyeon.kakaoimagesearch.R
import com.devhyeon.kakaoimagesearch.databinding.ItemImageBinding
import com.devhyeon.kakaoimagesearch.data.api.KakaoImageData
import com.devhyeon.kakaoimagesearch.utils.util.loadImage
import kotlin.properties.Delegates

class ImageListAdapter(val fragment : Fragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var imageList: List<KakaoImageData> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemQuestionBinding = DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context), R.layout.item_image, parent, false
        )
        return TrackListViewHolder(itemQuestionBinding)
    }

    override fun getItemCount(): Int = if (imageList.isNullOrEmpty()) 0 else imageList.size

    private fun getItem(position: Int): KakaoImageData = imageList[position]

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TrackListViewHolder).onBind(getItem(position))
    }
    
    private inner class TrackListViewHolder(private val viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
        fun onBind(imgData: KakaoImageData) {
            (viewDataBinding as ItemImageBinding).imgData = imgData
            //이미지 load
            viewDataBinding.ivThumbnail.loadImage(imgData.thumbnail_url)
        }
    }
}