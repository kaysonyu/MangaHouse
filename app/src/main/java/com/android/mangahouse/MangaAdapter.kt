package com.android.mangahouse

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MangaAdapter (val context: Context, val mangaList: List<Manga>): RecyclerView.Adapter<MangaAdapter.ViewHolder>() {

    inner class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
        val mangaImage: ImageView = view.findViewById(R.id.mangaImage)
        val mangaName: TextView = view.findViewById(R.id.mangaName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(LayoutInflater.from(context).inflate(R.layout.manga_item, parent, false))

        viewHolder.itemView.setOnClickListener {
            var manga = mangaList[viewHolder.adapterPosition]
            var inent = Intent(context, MangaActivity::class.java).apply {
                putExtra("manga_name", manga.name)
                putExtra("manga_image", manga.imageId)
            }
            context.startActivity(inent)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val manga = mangaList[position]
        holder.mangaName.text = manga.name
        holder.mangaImage.setImageResource(manga.imageId)
//        Glide.with(context).load(manga.imageId).into(holder.mangaImage)
    }

    override fun getItemCount() = mangaList.size

}