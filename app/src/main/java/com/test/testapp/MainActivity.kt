package com.test.testapp

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player.REPEAT_MODE_ONE
import com.test.testapp.data.Video
import com.test.testapp.databinding.ActivityMainBinding
import com.test.testapp.network.Common
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), IVideoEvent {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: VideosAdapter
    private var textShowing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.rvVideos.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.buttonText.setOnClickListener {
            if (textShowing) {
                binding.editText.visibility = View.GONE
            } else {
                binding.editText.visibility = View.VISIBLE

                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
                binding.editText.requestFocus()
            }
            textShowing = !textShowing
        }

        getVideos()
    }

    private fun getVideos() {
        Common.api.getVideos().enqueue(object : Callback<List<Video>> {
            override fun onResponse(call: Call<List<Video>>, response: Response<List<Video>>) {
                val videos = response.body()
                if (videos != null && videos.isNotEmpty()) {
                    adapter = VideosAdapter(videos, this@MainActivity)
                    binding.rvVideos.adapter = adapter

                    onClicked(videos[0])
                }
            }

            override fun onFailure(call: Call<List<Video>>, t: Throwable) {

            }
        })
    }

    override fun onClicked(video: Video?) {
        if (video != null) {
            if (video.file_url != null && video.file_url!!.isNotEmpty()) {
                binding.videoView.visibility = View.VISIBLE
                val player = ExoPlayer.Builder(this@MainActivity).build()
                binding.videoView.player = player
                val mediaItem: MediaItem = MediaItem.fromUri(Uri.parse(video.file_url))
                player.setMediaItem(mediaItem)
                player.repeatMode = REPEAT_MODE_ONE
                player.prepare()
                player.play()
            }

            adapter.videos.map {
                it.selected = video.id == it.id
            }
            adapter.notifyDataSetChanged()
        }
    }
}

interface IVideoEvent {
    fun onClicked(video: Video?)
}