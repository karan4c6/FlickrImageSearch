package com.karan4c6.photosearch.model

import com.karan4c6.photosearch.data.OperationCallback

interface PhotoDataSource {

    fun retrievePhotos(callback: OperationCallback<Photo>, searchText: String)
    fun cancel()
}