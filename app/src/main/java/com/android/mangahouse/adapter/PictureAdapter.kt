package com.android.mangahouse.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.mangahouse.R
import com.bumptech.glide.Glide
import kotlin.math.sqrt


class PictureAdapter (val context: Context, val pictureList: List<String>): RecyclerView.Adapter<PictureAdapter.ViewHolder>() {
    inner class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
        val comicPicture: ImageView = view.findViewById(R.id.comicPicture)
        val comicPageNum: TextView = view.findViewById(R.id.comicPageNum)
    }

//    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(LayoutInflater.from(context).inflate(R.layout.picture_item, parent, false))
        return viewHolder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val picture = pictureList[position]
        Glide.with(context)
            .load(picture)
            .into(holder.comicPicture)
        holder.comicPageNum.text = "${position+1}/${pictureList.size}"
    }

    override fun getItemCount() = pictureList.size

}
