package com.test.testapp.network

import com.test.testapp.data.Video
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("/api/backgrounds/?group=video&category_id=1")
    fun getVideos(): Call<List<Video>>
}