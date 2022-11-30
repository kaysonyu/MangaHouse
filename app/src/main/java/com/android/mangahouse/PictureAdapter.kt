package com.android.mangahouse

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PictureAdapter (val context: Context, val pictureList: List<String>): RecyclerView.Adapter<PictureAdapter.ViewHolder>() {
    inner class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
        val comicPicture: ImageView = view.findViewById(R.id.comicPicture)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(LayoutInflater.from(context).inflate(R.layout.picture_item, parent, false))

        return viewHolder
    }

    override fun onBindViewHolder(holder: PictureAdapter.ViewHolder, position: Int) {
        val picture = pictureList[position]
        Glide.with(context)
            .load(picture)
            .into(holder.comicPicture)
    }

    override fun getItemCount() = pictureList.size
}
