package com.example.android.pixabaydemo.adapter


data class PixaItem(
    var id: Int = -1,
    var previewURL: String = "",
    var webformatURL: String = "",
    var largeImageURL: String = "",
    var views: Int = -1,
    var likes: Int = -1,
    var user: String = "",
    var imageWidth: Long = -1,
    var imageHeight: Long = -1,
    var imageSize: Long = -1,
    var downloads: Long = -1,
)