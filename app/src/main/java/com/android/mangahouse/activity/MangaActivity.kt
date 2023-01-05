package com.android.mangahouse.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import com.android.mangahouse.request.ComicChapterResp
import com.android.mangahouse.request.ComicSearchService
import com.android.mangahouse.R
import com.android.mangahouse.`object`.Manga
import com.android.mangahouse.request.ServiceCreator
import com.android.mangahouse.adapter.ChapterAdapter
import com.android.mangahouse.sql.MangasDao
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_manga.*
import kotlinx.android.synthetic.main.fragment_subscribe.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread


class MangaActivity : AppCompatActivity() {
    lateinit var manga: Manga
    lateinit var adapter: ChapterAdapter
    var chapterList = ArrayList<ComicChapterResp.Chapter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manga)

        setSupportActionBar(detailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val site = intent.getStringExtra("site")
        val comicId = intent.getStringExtra("comicId")

        val layoutManager = GridLayoutManager(this, 3)
        chapterRecycleView.layoutManager = layoutManager
        adapter =
            ChapterAdapter(
                this,
                comicId,
                site,
                chapterList
            )
        chapterRecycleView.adapter = adapter

        val mangasDao = MangasDao(this)

        if (site != null && comicId != null) {
            refreshManga(site, comicId)

            subFab.setOnClickListener {
                if (mangasDao.isHasManga(manga)) {
//                    有->无
                    Snackbar.make(it, "取消订阅", Snackbar.LENGTH_SHORT)
                        .setAction("确定") {
                            mangasDao.deleteManga(manga)
                            Glide.with(this).load(R.drawable.ic_to_subscribe).into(subFab)
                            continueRead.visibility = View.GONE
                            LocalBroadcastManager.getInstance(this)
                                .sendBroadcast(Intent("collection-updated"))
                        }
                        .show()
                } else {
//                    无->有
                    Toast.makeText(this, "已订阅", Toast.LENGTH_SHORT).show()

                    mangasDao.addManga(manga)

                    Glide.with(this).load(R.drawable.ic_subscribed).into(subFab)
                    continueRead.visibility = View.VISIBLE
                    LocalBroadcastManager.getInstance(this)
                        .sendBroadcast(Intent("collection-updated"))
                }
            }

            continueRead.setOnClickListener {
                val mangaQuery = mangasDao.getManga(Manga(site, "", comicId, "", "", 1, 1))
                val inent = Intent(this, ReadActivity::class.java).apply {
                    putExtra("site", mangaQuery.site)
                    putExtra("comicId", mangaQuery.comicId)
                    putExtra("chapterId", mangaQuery.chapterNum)
                }

                this.startActivity(inent)
            }


            if (mangasDao.isHasManga(Manga(site, "", comicId, "", "", 1, 1))) {
                Glide.with(this).load(R.drawable.ic_subscribed).into(subFab)
                continueRead.visibility = View.VISIBLE
            } else {
                Glide.with(this).load(R.drawable.ic_to_subscribe).into(subFab)
                continueRead.visibility = View.GONE
            }

            val that = this
            detail_swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
            detail_swipeRefresh.setOnRefreshListener {
                thread {
                    that.runOnUiThread {
                        refreshManga(site, comicId)
                    }
                }
            }
        }
    }



    private fun refreshManga(site: String, comicId: String) {
        val searchRespService = ServiceCreator.create(ComicSearchService::class.java)
        val that = this
        searchRespService.getComicChapterResp(site, comicId).enqueue(object : Callback<ComicChapterResp> {
            override fun onResponse(call: Call<ComicChapterResp>, response: Response<ComicChapterResp>) {
                val comicResp = response.body()
                if (comicResp != null) {
                    chapterList.clear()
                    collapsingToolbarLayout.title = comicResp.name
                    Glide.with(that).load(comicResp.cover_image_url).into(detailImage)
                    comicResp.chapters.forEach {
                        chapterList.add(it)
                    }
                    adapter.notifyDataSetChanged()

                    detail_swipeRefresh.isRefreshing = false

                    manga = Manga(comicResp.site,
                        comicResp.source_name,
                        comicResp.comicid,
                        comicResp.cover_image_url,
                        comicResp.name,
                        1,
                        1)
                }
            }

            override fun onFailure(call: Call<ComicChapterResp>, t: Throwable) {
                t.printStackTrace()
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

}
