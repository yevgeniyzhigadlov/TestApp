package com.test.testapp.data

data class Video (
    var id: String? = null,
    var file_url: String? = null,
    var is_favorite: Boolean? = null,
    var poster_url: String? = null,
    var small_poster_url: String? = null,
    var small_thumbnail_url: String? = null,
    var thumbnail_url: String? = null,
    var selected: Boolean = false
)