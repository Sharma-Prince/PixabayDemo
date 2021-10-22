package com.example.android.pixabaydemo.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class PxImageRepository(private val pxImageDao: PxImageDao) {
    val allImage: LiveData<List<PxImage>> = pxImageDao.getAllImage()


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(pxImage: PxImage){
        pxImageDao.insert(pxImage)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(){
        pxImageDao.delete()
    }
}