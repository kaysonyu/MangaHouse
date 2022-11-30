package com.android.mangahouse

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_manga.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_subscribe.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSupportActionBar(searchToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        val historySearch = listOf("电锯人","死亡笔记")

        val historySearch = listOf("")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, historySearch)
        listView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(str: String?): Boolean {
                if (str != null) {
//                    submitSearch("title", str)
                    submitSearch(str)
                }
                return false
            }
            override fun onQueryTextChange(str: String?): Boolean {
                adapter.filter.filter(str)
                return false
            }
        })
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

//    private fun submitSearch(option: String, key: String) {
//        val searchRespService = ServiceCreator.create(ComicSearchService::class.java)
//        val that = this
//
//        searchRespService.getComicResp(option, key).enqueue(object : Callback<ComicResp> {
//            override fun onResponse(call: Call<ComicResp>, response: Response<ComicResp>) {
//                val comicResp = response.body()
//                if (comicResp != null) {
//                    val layoutManager = LinearLayoutManager(that)
//                    searchResultView.layoutManager = layoutManager
//                    val adapter = SearchResultAdapter(that, comicResp.data)
//                    searchResultView.adapter = adapter
//                }
//            }
//
//            override fun onFailure(call: Call<ComicResp>, t: Throwable) {
//                t.printStackTrace()
//            }
//
//        })
//
//    }

    private fun submitSearch(name: String) {
        val searchRespService = ServiceCreator.create(ComicSearchService::class.java)
        val that = this

        searchRespService.getComicResp(name).enqueue(object : Callback<ComicResp> {
            override fun onResponse(call: Call<ComicResp>, response: Response<ComicResp>) {
                val comicResp = response.body()
                if (comicResp != null) {
                    val layoutManager = LinearLayoutManager(that)
                    searchResultView.layoutManager = layoutManager
                    val adapter = SearchResultAdapter(that, comicResp.search_result)
                    searchResultView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<ComicResp>, t: Throwable) {
                t.printStackTrace()
            }

        })

    }






}