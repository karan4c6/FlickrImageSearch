package com.karan4c6.photosearch.di

import androidx.lifecycle.ViewModelProvider
import com.karan4c6.photosearch.model.PhotoDataSource
import com.karan4c6.photosearch.model.PhotoRepository
import com.karan4c6.photosearch.viewmodel.ViewModelFactory

object Injection {

    private val photoDataSource: PhotoDataSource = PhotoRepository()
    private val photoViewModelFactory = ViewModelFactory(photoDataSource)

    fun providerRepository(): PhotoDataSource {
        return photoDataSource
    }

    fun provideViewModelFactory(): ViewModelProvider.Factory{
        return photoViewModelFactory
    }
}