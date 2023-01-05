package com.android.mangahouse.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.mangahouse.request.ComicResp
import com.android.mangahouse.request.ComicSearchService
import com.android.mangahouse.R
import com.android.mangahouse.request.ServiceCreator
import com.android.mangahouse.adapter.SearchResultAdapter
import com.android.mangahouse.custom_components.OnItemClickListener
import com.android.mangahouse.fragment.SourceSiteDialogFragment
import com.android.mangahouse.sql.RecordsDao
import com.android.mangahouse.sql.SitesDao
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.flHistory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    private val recordsDao: RecordsDao = RecordsDao(this)
    private val sitesDao: SitesDao = SitesDao(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSupportActionBar(searchToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val historyRecords = recordsDao.getAll().reversed()
        flHistory.setList(historyRecords)
        flHistory.setOnItemClickListener (object : OnItemClickListener {
            override fun onClick(str: String) {
                searchView.setQuery(str, true)
            }
        })

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


        source_site_button.setOnClickListener {
            val fragmentManager = supportFragmentManager
            val sourceSiteDialogFragment = SourceSiteDialogFragment()
            sourceSiteDialogFragment.show(fragmentManager, "source_site_dialog")
        }
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
        val sitesList = sitesDao.getAll()
        var site: String = ""
        if (sitesList.size == 0) {
            site = "dmzj"
        }
        else {
            for (i in sitesList.indices) {
                if (i == sitesList.size - 1) {
                    site += sitesList[i].site
                }
                else {
                    site += "${sitesList[i].site},"
                }
            }
        }

        val searchRespService =
            ServiceCreator.create(
                ComicSearchService::class.java)
        val that = this

        searchRespService.getComicResp(name, site).enqueue(object : Callback<ComicResp> {
            override fun onResponse(call: Call<ComicResp>, response: Response<ComicResp>) {
                val comicResp = response.body()
                if (comicResp != null) {
                    val layoutManager = LinearLayoutManager(that)
                    searchResultView.layoutManager = layoutManager
                    val adapter =
                        SearchResultAdapter(
                            that,
                            comicResp.list
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