package com.android.mangahouse.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
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
import com.google.android.material.snackbar.Snackbar
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

        val site = intent.getStringExtra("site")
        val comicId = intent.getStringExtra("comicId")

        val mangasDao = MangasDao(this)

        val searchRespService =
            ServiceCreator.create(
                ComicSearchService::class.java)
        if (site != null && comicId != null) {
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
                if (mangasDao.isHasManga(manga)) {
//                    有->无
                    Snackbar.make(it, "取消订阅", Snackbar.LENGTH_SHORT)
                        .setAction("确定") {

                            mangasDao.deleteManga(manga)

                            Glide.with(this).load(R.drawable.ic_to_subscribe).into(subFab)
                            continueRead.visibility = View.GONE

                        }
                        .show()
                }
                else {
//                    无->有
                    Toast.makeText(this, "已订阅", Toast.LENGTH_SHORT).show()

                    mangasDao.addManga(manga)

                    Glide.with(this).load(R.drawable.ic_subscribed).into(subFab)
                    continueRead.visibility = View.VISIBLE


                }


            }

            continueRead.setOnClickListener {
                val mangaQuery = mangasDao.getManga(Manga(site, comicId, "", "", 1, 1))
                val inent = Intent(this, ReadActivity::class.java).apply {
                    putExtra("site", "dmzj")
                    putExtra("comicId", mangaQuery.comicId)
                    putExtra("chapterId", mangaQuery.chapterNum)
                }

                this.startActivity(inent)
            }



            if (mangasDao.isHasManga(Manga(site, comicId, "", "", 1, 1))) {
                Glide.with(this).load(R.drawable.ic_subscribed).into(subFab)
                continueRead.visibility = View.VISIBLE
            }
            else {
                Glide.with(this).load(R.drawable.ic_to_subscribe).into(subFab)
                continueRead.visibility = View.GONE
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
