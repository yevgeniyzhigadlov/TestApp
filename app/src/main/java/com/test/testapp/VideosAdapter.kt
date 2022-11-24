package com.test.testapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.test.testapp.data.Video
import com.test.testapp.databinding.ItemVideoBinding


class VideosAdapter(var videos: List<Video>, val listener: IVideoEvent) :
    RecyclerView.Adapter<VideosAdapter.ViewHolder>() {

    class ViewHolder(var binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(video: Video?, listener: IVideoEvent) {
            if (video != null) {
                if (video.small_thumbnail_url != null && video.small_thumbnail_url!!.isNotEmpty()) {
                    Glide
                        .with(binding.root)
                        .load(video.small_poster_url)
                        .centerCrop()
                        .placeholder(R.drawable.placeholder)
                        .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(10)))
                        .into(binding.image)
                }
                if (video.selected) {
                    binding.image.alpha = 1f
                } else {
                    binding.image.alpha = 0.5f
                }
                binding.image.setOnClickListener {
                    listener.onClicked(video)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemVideoBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val media = videos[position]
        holder.bind(media, listener)
    }

    override fun getItemCount(): Int {
        return videos.size
    }
}