package com.android.mangahouse.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.mangahouse.R
import com.android.mangahouse.`object`.MangaTag
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.tag_item.*

class TagAdapter (val context: Context, val tagList: List<MangaTag>): RecyclerView.Adapter<TagAdapter.ViewHolder>() {

    inner class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
        val tagName: TextView = view.findViewById(R.id.tagName)
        val tagRecyclerView: RecyclerView = view.findViewById(R.id.tagRecycleView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(LayoutInflater.from(context).inflate(R.layout.tag_item, parent, false))

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mangaTag = tagList[position]
        holder.tagName.text = mangaTag.tagName

        val mangaList = mangaTag.mangaList

        val layoutManager = GridLayoutManager(context, 3)
        holder.tagRecyclerView.layoutManager = layoutManager
//        holder.tagRecyclerView.layoutManager = layoutManager.apply {
//            orientation = RecyclerView.HORIZONTAL
//        }
        val mangaListAdapter = RecommendMangaAdapter(context, mangaTag.mangaList)
        holder.tagRecyclerView.adapter = mangaListAdapter
    }

    override fun getItemCount() = tagList.size

}