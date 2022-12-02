package com.android.mangahouse.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.mangahouse.request.ComicContentResp
import com.android.mangahouse.request.ComicSearchService
import com.android.mangahouse.R
import com.android.mangahouse.request.ServiceCreator
import com.android.mangahouse.adapter.PictureAdapter
import kotlinx.android.synthetic.main.activity_read.*
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)

        val comicId = intent.getStringExtra("comicId")
        val chapterId = intent.getIntExtra("chapterId", 1)



        val searchRespService =
            ServiceCreator.create(
                ComicSearchService::class.java)
        if (comicId != null) {
            val that = this
            searchRespService.getComicContentResp(comicId, chapterId).enqueue(object : Callback<ComicContentResp> {
                override fun onResponse(call: Call<ComicContentResp>, response: Response<ComicContentResp>) {
                    val comicResp = response.body()
                    if (comicResp != null) {
                        for (list in comicResp.image_urls)
                            Log.d("yuyuyu", "--${list}--")

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
                    }
                }

                override fun onFailure(call: Call<ComicContentResp>, t: Throwable) {
                    t.printStackTrace()
                }

            })
        }
    }


}
