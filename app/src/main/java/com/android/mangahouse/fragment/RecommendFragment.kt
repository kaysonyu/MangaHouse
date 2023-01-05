package com.android.mangahouse.fragment



import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.mangahouse.R
import com.android.mangahouse.`object`.Manga
import com.android.mangahouse.`object`.MangaTag
import com.android.mangahouse.adapter.MangaAdapter
import com.android.mangahouse.adapter.PictureAdapter
import com.android.mangahouse.adapter.TagAdapter
import com.android.mangahouse.request.*
import com.android.mangahouse.sql.MangasDao
import kotlinx.android.synthetic.main.activity_read.*
import kotlinx.android.synthetic.main.fragment_recommend.*
import kotlinx.android.synthetic.main.fragment_subscribe.*
import kotlinx.android.synthetic.main.tag_item.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread

class RecommendFragment : Fragment() {
    var mangaTagList = ArrayList<MangaTag>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recommend, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val mActivity = activity as AppCompatActivity
        val layoutManager = LinearLayoutManager(mActivity)
        recommendRecycleView.layoutManager = layoutManager
        val adapter = TagAdapter(mActivity, mangaTagList)
        recommendRecycleView.adapter = adapter
        refreshRecommend(adapter)

        recommendSwipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        recommendSwipeRefresh.setOnRefreshListener {
            thread {
                activity?.runOnUiThread {
                    mangaTagList.clear()
                    refreshRecommend(adapter)
                    recommendSwipeRefresh.isRefreshing = false
                }
            }
        }

        super.onActivityCreated(savedInstanceState)
    }

    private fun refreshRecommend(adapter: TagAdapter) {
        val site = "dmzj"
        val category = "题材"
        val tagNotUse = "全部"
        val searchRespService = ServiceCreator.create(ComicSearchService::class.java)
        searchRespService.getAllTags(site).enqueue(object : Callback<ComicTagResp> {
            override fun onResponse(call: Call<ComicTagResp>, response: Response<ComicTagResp>) {
                val comicTagResp = response.body()
                if (comicTagResp != null) {
                    for (tagCategory in comicTagResp.tags) {
                        if (tagCategory.category == category) {
                            for (it in tagCategory.tags) {
                                if (it.name == tagNotUse) continue
                                searchRespService.getComicByTags(site, it.tag).enqueue(object : Callback<ComicResp> {
                                    override fun onResponse(call: Call<ComicResp>, response: Response<ComicResp>) {
                                        val comicResp = response.body()
                                        if (comicResp != null && comicResp.list.isNotEmpty()) {
                                            val random = Random()
                                            val selected = comicResp.list.toMutableList().apply { shuffle(random) }.take(3)
                                            mangaTagList.add(MangaTag(it.name, selected))
                                            adapter.notifyDataSetChanged()
                                        }
                                    }
                                    override fun onFailure(call: Call<ComicResp>, t: Throwable) {
                                        t.printStackTrace()
                                    }
                                })
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ComicTagResp>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }


}
