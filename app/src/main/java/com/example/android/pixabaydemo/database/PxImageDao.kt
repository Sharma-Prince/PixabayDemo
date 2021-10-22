package com.example.android.pixabaydemo.database


import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PxImageDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pxImage: PxImage)

    @Query("DELETE FROM images_table")
    suspend fun delete()

    @Query("Select * from images_table order by imageId ASC")
    fun getAllImage(): LiveData<List<PxImage>>
}