package com.example.android.pixabaydemo.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "images_table")
class PxImage(
    var previewURL: String = "",
    var largeImageURL: String = "",
    var imageWidth: String = "",
    var imageHeight: String = "",
    var imageSize: String = "",
    var likes: String = "",
    var views: String = "",
    var downloads: String = "",
) {
    @PrimaryKey(autoGenerate = true)
    var imageId: Int = 0
}