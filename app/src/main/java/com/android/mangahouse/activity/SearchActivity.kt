package com.android.mangahouse.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.mangahouse.request.ComicResp
import com.android.mangahouse.request.ComicSearchService
import com.android.mangahouse.R
import com.android.mangahouse.request.ServiceCreator
import com.android.mangahouse.adapter.SearchResultAdapter
import com.android.mangahouse.custom_components.OnItemClickListener
import com.android.mangahouse.sql.RecordsDao
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.flHistory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    private val recordsDao: RecordsDao = RecordsDao(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSupportActionBar(searchToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        recordsDao.addRecords("测试")
        val historyRecords = recordsDao.getAll().reversed()
//        flHistory.setTextList(historyRecords)
        flHistory.setList(historyRecords)
        flHistory.setOnItemClickListener (object : OnItemClickListener {
            override fun onClick(str: String) {
                searchView.setQuery(str, true)
            }
        })
//        Log.d("hhhh", historyRecords.size.toString())
//        if (historyRecords.size > 0) {
//            flHistory.setTextList(historyRecords)
//        }


//        val historySearch = listOf("")
//        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, historySearch)
//        listView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(str: String?): Boolean {
                if (str != null) {
//                    submitSearch("title", str)
                    submitSearch(str)
                }
                return false
            }
            override fun onQueryTextChange(str: String?): Boolean {
//                adapter.filter.filter(str)
                searchResultView.visibility = View.GONE
                historyLayout.visibility = View.VISIBLE
                return false
            }
        })

        deleteHistory.setOnClickListener {
            recordsDao.deleteAll()

            val searchRecords = recordsDao.getAll().reversed()
            flHistory.setList(searchRecords)

        }

//        flHistory.setOnLongClickListener {
//            Log.d("11111", "hhh")
//            false
//        }

//        flHistory.setOnClickItemListener { _, text ->
//            searchView.setQuery(text, true)
//        }


//        searchView.setOnQueryTextFocusChangeListener(object : View.OnFocusChangeListener {
//            override fun onFocusChange(v: View?, hasFocus: Boolean) {
//                Log.d("uuuu", "hhhhh")
//                if (flHistory.visibility == View.GONE) {
//                    Log.d("uuuu", "hhhhh")
//
//                }
//
//            }
//
//        })
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
        val searchRespService =
            ServiceCreator.create(
                ComicSearchService::class.java)
        val that = this

        searchRespService.getComicResp(name).enqueue(object : Callback<ComicResp> {
            override fun onResponse(call: Call<ComicResp>, response: Response<ComicResp>) {
                val comicResp = response.body()
                if (comicResp != null) {
                    val layoutManager = LinearLayoutManager(that)
                    searchResultView.layoutManager = layoutManager
                    val adapter =
                        SearchResultAdapter(
                            that,
                            comicResp.search_result
                        )
                    searchResultView.adapter = adapter
                    searchResultView.visibility = View.VISIBLE

                    if (recordsDao.isHasRecord(name)) {
                        recordsDao.deleteRecord(name)
                    }
                    recordsDao.addRecord(name)
                    val searchRecords = recordsDao.getAll().reversed()
                    flHistory.setList(searchRecords)

                    historyLayout.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<ComicResp>, t: Throwable) {
                t.printStackTrace()
            }

        })

    }






}