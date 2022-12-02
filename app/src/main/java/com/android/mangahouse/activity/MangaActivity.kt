package com.android.mangahouse.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.android.mangahouse.request.ComicChapterResp
import com.android.mangahouse.request.ComicSearchService
import com.android.mangahouse.R
import com.android.mangahouse.`object`.Manga
import com.android.mangahouse.request.ServiceCreator
import com.android.mangahouse.adapter.ChapterAdapter
import com.android.mangahouse.sql.MangasDao
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_manga.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MangaActivity : AppCompatActivity() {
    lateinit var manga: Manga;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manga)

        setSupportActionBar(detailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val comicId = intent.getStringExtra("comicId")

        val searchRespService =
            ServiceCreator.create(
                ComicSearchService::class.java)
        if (comicId != null) {
            val that = this
//            searchRespService.getComicChapterResp(comicId).enqueue(object : Callback<ComicChapterResp> {
//                override fun onResponse(call: Call<ComicChapterResp>, response: Response<ComicChapterResp>) {
//                    val comicResp = response.body()
//                    if (comicResp != null) {
//                        collapsingToolbarLayout.title = comicResp.data.title
//                        Glide.with(that).load(comicResp.data.cover).into(detailImage)
//
//                        val layoutManager = GridLayoutManager(that, 3)
//                        chapterRecycleView.layoutManager = layoutManager
//                        val adapter = ChapterAdapter(that, comicResp.data.chapterList)
//                        chapterRecycleView.adapter = adapter
//                    }
//                }
//
//                override fun onFailure(call: Call<ComicChapterResp>, t: Throwable) {
//                    t.printStackTrace()
//                }
//
//            })
            searchRespService.getComicChapterResp(comicId).enqueue(object : Callback<ComicChapterResp> {
                override fun onResponse(call: Call<ComicChapterResp>, response: Response<ComicChapterResp>) {
                    val comicResp = response.body()
                    if (comicResp != null) {
                        collapsingToolbarLayout.title = comicResp.name
                        Glide.with(that).load(comicResp.cover_image_url).into(detailImage)

                        val layoutManager = GridLayoutManager(that, 3)
                        chapterRecycleView.layoutManager = layoutManager
                        val adapter =
                            ChapterAdapter(
                                that,
                                comicResp.comicid,
                                comicResp.chapters
                            )
                        chapterRecycleView.adapter = adapter

                        manga = Manga(comicResp.site,
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

            subFab.setOnClickListener {
                val mangasDao = MangasDao(this)
                mangasDao.addManga(manga)

            }
        }


//        collapsingToolbarLayout.title = mangaName
//        detailImage.setImageResource(mangaImage)

//        catalogView.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, catalog)

//        detail_swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
//        detail_swipeRefresh.setOnRefreshListener {
//            thread {
//                Thread.sleep(1000)
//                runOnUiThread {
//                    detail_swipeRefresh.isRefreshing = false
//                }
//            }
//        }

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
