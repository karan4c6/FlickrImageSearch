package com.karan4c6.photosearch.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.karan4c6.photosearch.R
import com.karan4c6.photosearch.model.Photo

class PhotosAdapter(private var photos: List<Photo>) :
    RecyclerView.Adapter<PhotosAdapter.PViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): PViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_photo, parent, false)
        return PViewHolder(view)
    }

    override fun onBindViewHolder(vh: PViewHolder, position: Int) {
        val image = photos[position]
        val imageUrl =
            "https://farm${image.farm}.static.flickr.com/${image.server}/${image.id}_${image.secret}.jpg"

        Glide.with(vh.imageView.context).load(imageUrl).into(vh.imageView)
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    fun update(data: List<Photo>) {
        photos = data
        notifyDataSetChanged()
    }

    class PViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }
}