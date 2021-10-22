package com.example.android.pixabaydemo.viewModel


import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class Communicator : ViewModel() {
    var search = MutableLiveData<Any>()
    var largeImageURL = MutableLiveData<Any>()
    var imageWidth = MutableLiveData<Any>()
    var imageHeight = MutableLiveData<Any>()
    var imageSize = MutableLiveData<Any>()
    var downloads = MutableLiveData<Any>()
    var views = MutableLiveData<Any>()
    var likes = MutableLiveData<Any>()

}