package com.android.mangahouse.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.mangahouse.request.ComicResp
import com.android.mangahouse.activity.MangaActivity
import com.android.mangahouse.R
import com.bumptech.glide.Glide

class SearchResultAdapter (val context: Context, val searchResultList: List<ComicResp.Data>): RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {

    inner class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
        val comicCover: ImageView = view.findViewById(R.id.searchCover)
        val comicTitle: TextView = view.findViewById(R.id.searchTitle)
        val comicAuthor: TextView = view.findViewById(R.id.searchAuthor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(LayoutInflater.from(context).inflate(R.layout.search_item, parent, false))

        viewHolder.itemView.setOnClickListener {

            val searchResult = searchResultList[viewHolder.adapterPosition]

            val inent = Intent(context, MangaActivity::class.java).apply {
//                putExtra("comicId", searchResult.comicId)
                putExtra("comicId", searchResult.comicid)
            }
            context.startActivity(inent)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val searchResult = searchResultList[position]
        Glide.with(context).load(searchResult.cover_image_url).into(holder.comicCover)
        holder.comicTitle.text = searchResult.name
        holder.comicAuthor.text = searchResult.source_name
    }

    override fun getItemCount() = searchResultList.size

}