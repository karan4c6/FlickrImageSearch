package com.karan4c6.photosearch.data

import com.google.gson.annotations.SerializedName
import com.karan4c6.photosearch.model.Photos

data class PhotoResponse(
    @SerializedName("stat")
    val status: String?,
    @SerializedName("photos")
    val photos: Photos?
) {
    fun isSuccess(): Boolean = status.equals("ok", true)
}

