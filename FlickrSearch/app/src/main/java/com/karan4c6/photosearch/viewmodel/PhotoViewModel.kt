package com.karan4c6.photosearch.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.karan4c6.photosearch.data.OperationCallback
import com.karan4c6.photosearch.model.PhotoDataSource
import com.karan4c6.photosearch.model.Photo
import com.karan4c6.photosearch.model.Photos

class PhotoViewModel(private val repository: PhotoDataSource) : ViewModel() {

    companion object {
        val TAG = PhotoViewModel::class.java.simpleName
    }

    private val _photos = MutableLiveData<List<Photo>>().apply { value = emptyList() }
    val photos: LiveData<List<Photo>> = _photos

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    fun loadImages(searchText: String) {
        if (searchText.isNullOrEmpty()) {
            return
        }

        _isViewLoading.postValue(true)

        repository.retrievePhotos(object : OperationCallback<Photo> {

            override fun onError(error: String?) {
                Log.d(TAG, "onError(): $error")
                _isViewLoading.postValue(false)
                _onMessageError.postValue(error)
            }

            override fun onSuccess(data: List<Photo>?) {
                _isViewLoading.postValue(false)

                if (data != null && data.isNotEmpty()) {
                    _photos.value = data
                } else {
                    _isEmptyList.postValue(true)
                }
            }
        }, searchText)
    }

}