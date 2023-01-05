package com.android.mangahouse.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.mangahouse.R
import com.android.mangahouse.`object`.Manga
import com.android.mangahouse.activity.MangaActivity
import com.android.mangahouse.request.ComicResp
import com.bumptech.glide.Glide

class RecommendMangaAdapter (val context: Context, val mangaList: List<ComicResp.Data>): RecyclerView.Adapter<RecommendMangaAdapter.ViewHolder>() {

    inner class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
        val mangaImage: ImageView = view.findViewById(R.id.mangaImage)
        val mangaName: TextView = view.findViewById(R.id.mangaName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(LayoutInflater.from(context).inflate(R.layout.manga_item, parent, false))

        viewHolder.itemView.setOnClickListener {
            val manga = mangaList[viewHolder.adapterPosition]
            val inent = Intent(context, MangaActivity::class.java).apply {
                putExtra("comicId", manga.comicid)
                putExtra("site", manga.site)
            }
            context.startActivity(inent)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val manga = mangaList[position]
        holder.mangaName.text = manga.name
        Glide.with(context)
            .load(manga.cover_image_url)
            .into(holder.mangaImage)
    }

    override fun getItemCount() = mangaList.size

}