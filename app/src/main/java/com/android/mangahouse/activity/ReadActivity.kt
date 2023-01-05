package com.android.mangahouse.activity

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.mangahouse.R
import com.android.mangahouse.`object`.Manga
import com.android.mangahouse.adapter.PictureAdapter
import com.android.mangahouse.request.ComicContentResp
import com.android.mangahouse.request.ComicResp
import com.android.mangahouse.request.ComicSearchService
import com.android.mangahouse.request.ServiceCreator
import com.android.mangahouse.sql.MangasDao
import kotlinx.android.synthetic.main.activity_read.*
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.picture_item.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReadActivity : AppCompatActivity() {
    var site: String? = null
    var comicId: String? = null
    var chapterId: Int = 1
    var imageUrlList = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)

        site = intent.getStringExtra("site")
        comicId = intent.getStringExtra("comicId")
        chapterId = intent.getIntExtra("chapterId", 1)

        val site = site
        val comicId = comicId
        val chapterId = chapterId

        val adapter =
            PictureAdapter(
                this,
                imageUrlList
            )
        readView.adapter = adapter

        val searchRespService = ServiceCreator.create(ComicSearchService::class.java)
        if (site != null && comicId != null) {
            val that = this
            searchRespService.getComicContentResp(site, comicId, chapterId).enqueue(object : Callback<ComicContentResp> {
                override fun onResponse(call: Call<ComicContentResp>, response: Response<ComicContentResp>) {
                    val comicResp = response.body()
                    if (comicResp != null) {
                        comicResp.image_urls.forEach {
                            imageUrlList.add(it)
                        }
                        adapter.notifyDataSetChanged()

                        val mangasDao = MangasDao(that)
                        val mangaQuery = mangasDao.getManga(Manga(site, comicId, "", "", 1, 1))
                        if (mangaQuery.chapterNum == chapterId) {
                            readView.currentItem = mangaQuery.pageNum - 1
                        }
                    }
                }

                override fun onFailure(call: Call<ComicContentResp>, t: Throwable) {
                    t.printStackTrace()
                }

            })
        }

        val readSetting = getSharedPreferences("settings", Context.MODE_PRIVATE)
        val screenOrientation = readSetting.getInt("screenOrientation", 0)

        readView.orientation = if (screenOrientation == 0) {RecyclerView.HORIZONTAL}
                                else {RecyclerView.VERTICAL}

        readView.setPageTransformer { page, position ->
            if (position <= 0.0f) {
                if (readView.orientation == RecyclerView.HORIZONTAL) {
                    page.translationX = 0.0f
                } else {
                    page.translationY = 0.0f
                }
                page.translationZ = 0.0f
            } else {
                if (readView.orientation == RecyclerView.HORIZONTAL) {
                    page.translationX = (-page.width * position)
                }
                else {
                    page.translationY = (-page.width * position)
                }
                page.translationZ = -position
            }
        }
    }

    override fun onStop() {
        val mangasDao = MangasDao(this)
        val site_ = site
        val comicId_ = comicId
        val chapterId_ = chapterId
        if (site_ != null && comicId_ != null) {
            if (mangasDao.isHasManga(Manga(site_, comicId_, "", "", 1, 1))) {
                mangasDao.updateManga(Manga(site_, comicId_, "", "", chapterId_, comicPageNum.text.split('/').get(0).toInt()))
                Toast.makeText(this, "阅读记录已更新", Toast.LENGTH_SHORT).show()
            }
        }
        super.onStop()
    }



}

