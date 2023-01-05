package com.android.mangahouse.fragment


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.android.mangahouse.`object`.Manga
import com.android.mangahouse.R
import com.android.mangahouse.activity.SearchActivity
import com.android.mangahouse.adapter.MangaAdapter
import com.android.mangahouse.sql.MangasDao
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_subscribe.*
import kotlin.concurrent.thread


class SubscribeFragment : Fragment() {

    val mangaList = ArrayList<Manga>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        val mActivity = activity as AppCompatActivity
        mActivity.setSupportActionBar(toolbar)

        val actionBar: ActionBar? = mActivity.supportActionBar
        actionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        val mangasDao = MangasDao(activity)
        mangasDao.getAll().forEach {
            mangaList.add(it)
        }


        val layoutManager = GridLayoutManager(context, 3)
        recycleView.layoutManager = layoutManager
        val adapter = MangaAdapter(mActivity, mangaList)
        recycleView.adapter = adapter

        swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        swipeRefresh.setOnRefreshListener {
            thread {
                activity?.runOnUiThread {
                    mangaList.clear()
                    mangasDao.getAll().forEach {
                        mangaList.add(it)
                    }
                    adapter.notifyDataSetChanged()
                    swipeRefresh.isRefreshing = false
                }
            }
        }


        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_subscribe, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.toolbar, menu)
        //super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                activity?.drawerLayout?.openDrawer(GravityCompat.START)
            }

            R.id.search -> {
                activity?.startActivity(Intent(activity, SearchActivity::class.java))
            }
        }
        return true
    }


}
