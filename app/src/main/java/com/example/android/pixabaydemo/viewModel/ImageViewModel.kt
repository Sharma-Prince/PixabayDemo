package com.example.android.pixabaydemo.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.android.pixabaydemo.database.PxImage
import com.example.android.pixabaydemo.database.PxImageDatabase
import com.example.android.pixabaydemo.database.PxImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ImageViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PxImageRepository
    val allImage: LiveData<List<PxImage>>
    init {
        val dao = PxImageDatabase.getDatabase(application).getImageDao()
        repository = PxImageRepository(dao)
        allImage = repository.allImage
    }
    fun deleteNode() = viewModelScope.launch(Dispatchers.IO) {
        repository.delete()
    }
    fun insertNode(pxImage: PxImage) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(pxImage)
    }
}


