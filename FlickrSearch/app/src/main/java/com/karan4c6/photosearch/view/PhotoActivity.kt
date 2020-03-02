package com.karan4c6.photosearch.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.karan4c6.photosearch.R
import com.karan4c6.photosearch.di.Injection
import com.karan4c6.photosearch.model.Photo
import com.karan4c6.photosearch.viewmodel.PhotoViewModel
import kotlinx.android.synthetic.main.activity_photo.*
import kotlinx.android.synthetic.main.layout_error.*

class PhotoActivity : AppCompatActivity() {

    private lateinit var viewModel: PhotoViewModel
    private lateinit var adapter: PhotosAdapter

    companion object {
        val TAG = PhotoActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        setupViewModel()
        setupUI()
    }

    //ui
    private fun setupUI() {
        adapter = PhotosAdapter(
            viewModel.photos.value ?: emptyList()
        )
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = adapter

        btSearch.setOnClickListener {
            viewModel.loadImages(etSearch.text.toString())
        }
    }

    //viewmodel
    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory()).get(
            PhotoViewModel::class.java
        )
        viewModel.photos.observe(this, renderPhotos)

        viewModel.isViewLoading.observe(this, isViewLoadingObserver)
        viewModel.onMessageError.observe(this, onMessageErrorObserver)
        viewModel.isEmptyList.observe(this, emptyListObserver)
    }

    //observers
    private val renderPhotos = Observer<List<Photo>> {
        Log.v(TAG, "data updated $it")
        layoutError.visibility = View.GONE
        layoutEmpty.visibility = View.GONE
        adapter.update(it)
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        Log.v(TAG, "isViewLoading $it")
        val visibility = if (it) View.VISIBLE else View.GONE
        progressBar.visibility = visibility
    }

    private val onMessageErrorObserver = Observer<Any> {
        Log.v(TAG, "onMessageError $it")
        layoutError.visibility = View.VISIBLE
        layoutEmpty.visibility = View.GONE
        textViewError.text = "Error $it"
    }

    private val emptyListObserver = Observer<Boolean> {
        Log.v(TAG, "emptyListObserver $it")
        layoutEmpty.visibility = View.VISIBLE
        layoutError.visibility = View.GONE
    }

}
