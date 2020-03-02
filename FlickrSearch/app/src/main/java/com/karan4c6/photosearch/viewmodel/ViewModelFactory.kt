package com.karan4c6.photosearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.karan4c6.photosearch.model.PhotoDataSource

class ViewModelFactory(private val repository: PhotoDataSource):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PhotoViewModel(repository) as T
    }
}