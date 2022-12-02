package com.android.mangahouse.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.mangahouse.request.ComicContentResp
import com.android.mangahouse.request.ComicSearchService
import com.android.mangahouse.R
import com.android.mangahouse.`object`.Manga
import com.android.mangahouse.request.ServiceCreator
import com.android.mangahouse.adapter.PictureAdapter
import com.android.mangahouse.sql.MangasDao
import kotlinx.android.synthetic.main.activity_read.*
import kotlinx.android.synthetic.main.picture_item.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//class ReadActivity : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_read)
//
//        val chapterId = intent.getStringExtra("chapterId")
//
//
//
//        val searchRespService = ServiceCreator.create(ComicSearchService::class.java)
//        if (chapterId != null) {
//            val that = this
//            searchRespService.getComicContentResp(chapterId).enqueue(object : Callback<ComicContentResp> {
//                override fun onResponse(call: Call<ComicContentResp>, response: Response<ComicContentResp>) {
//                    val comicResp = response.body()
//                    if (comicResp != null) {
//                        for (list in comicResp.data)
//                            Log.d("yuyuyu", "--${list}--")
//
////                        Glide.with(that).load(comicResp.data.get(3)).into(test_)
////                        tttt.text = comicResp.data.get(3)
//
//                        val layoutManager = GridLayoutManager(that, 1)
//                        contentRecycleView.layoutManager = layoutManager
//                        val adapter = PictureAdapter(that, comicResp.data)
//                        contentRecycleView.adapter = adapter
//                    }
//                }
//
//                override fun onFailure(call: Call<ComicContentResp>, t: Throwable) {
//                    t.printStackTrace()
//                }
//
//            })
//        }
//    }
//
//
//}

class ReadActivity : AppCompatActivity() {
    var site: String? = null
    var comicId: String? = null
    var chapterId: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)

        site = intent.getStringExtra("site")
        comicId = intent.getStringExtra("comicId")
        chapterId = intent.getIntExtra("chapterId", 1)

        val site = site
        val comicId = comicId
        val chapterId = chapterId

        val searchRespService = ServiceCreator.create(ComicSearchService::class.java)
        if (site != null && comicId != null) {
            val that = this
            searchRespService.getComicContentResp(comicId, chapterId).enqueue(object : Callback<ComicContentResp> {
                override fun onResponse(call: Call<ComicContentResp>, response: Response<ComicContentResp>) {
                    val comicResp = response.body()
                    if (comicResp != null) {

//                        Glide.with(that).load(comicResp.data.get(3)).into(test_)
//                        tttt.text = comicResp.data.get(3)

//                        val layoutManager = GridLayoutManager(that, 1)
//                        readView.layoutManager = layoutManager
                        val adapter =
                            PictureAdapter(
                                that,
                                comicResp.image_urls
                            )
                        readView.adapter = adapter

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
    }

    override fun onStop() {
        val mangasDao = MangasDao(this)
        val site_ = site
        val comicId_ = comicId
        val chapterId_ = chapterId
        if (site_ != null && comicId_ != null) {
            if (mangasDao.isHasManga(Manga(site_, comicId_, "", "", 1, 1))) {
                mangasDao.updateManga(Manga(site_, comicId_, "", "", chapterId_, comicPageNum.text.split('/').get(0).toInt()))
            }

            Toast.makeText(this, "阅读记录已更新", Toast.LENGTH_SHORT).show()
        }
        super.onStop()
    }



}

