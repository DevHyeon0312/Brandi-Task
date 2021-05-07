package com.devhyeon.kakaoimagesearch.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devhyeon.kakaoimagesearch.R
import com.devhyeon.kakaoimagesearch.databinding.ItemImageBinding
import com.devhyeon.kakaoimagesearch.network.kakao.data.ImageData
import kotlin.properties.Delegates

class ImageListAdapter(val fragment : Fragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var imageList: List<ImageData> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemQuestionBinding = DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context), R.layout.item_image, parent, false
        )
        return TrackListViewHolder(itemQuestionBinding)
    }

    override fun getItemCount(): Int = if (imageList.isNullOrEmpty()) 0 else imageList.size

    private fun getItem(position: Int): ImageData = imageList[position]

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TrackListViewHolder).onBind(getItem(position))
    }
    
    private inner class TrackListViewHolder(private val viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
        fun onBind(imgData: ImageData) {
            (viewDataBinding as ItemImageBinding).imgData = imgData
            //이미지
            Glide
                .with(fragment)
                .load(imgData.thumbnail_url)
                .into(viewDataBinding.ivTrackArt)

        }
    }
}