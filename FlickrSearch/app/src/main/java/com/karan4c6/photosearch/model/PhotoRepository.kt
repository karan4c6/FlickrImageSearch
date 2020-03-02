package com.karan4c6.photosearch.model

import com.karan4c6.photosearch.data.ApiClient
import com.karan4c6.photosearch.data.OperationCallback
import com.karan4c6.photosearch.data.PhotoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotoRepository : PhotoDataSource {

    private var call: Call<PhotoResponse>? = null

    val API_KEY = "eff913d79fe6faba175604fcfbcc8776"
    val FORMAT = "json"

    companion object {
        val TAG = PhotoRepository::class.java.simpleName
    }

    override fun retrievePhotos(callback: OperationCallback<Photo>, searchText: String) {
        call = ApiClient.build()
            ?.getImageResults(
                apiKey = API_KEY,
                text = searchText,
                format = FORMAT,
                noJsonCallback = true
            )
        call?.enqueue(object : Callback<PhotoResponse> {

            override fun onFailure(call: Call<PhotoResponse>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(
                call: Call<PhotoResponse>,
                response: Response<PhotoResponse>
            ) {
                response?.body()?.let {
                    if (response.isSuccessful && (it.isSuccess())) {
                        callback.onSuccess(it.photos?.photos)
                    } else {
                        callback.onError(it.status)
                    }
                }
            }
        })
    }

    override fun cancel() {
        call?.let {
            it.cancel()
        }
    }
}