package com.android.mangahouse


import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_subscribe.*
import kotlin.concurrent.thread


class SubscribeFragment : Fragment() {

    val mangas = mutableListOf(Manga("寄生兽", R.drawable.p1), Manga("亚人", R.drawable.p2),
        Manga("进击的巨人", R.drawable.p3), Manga("死亡笔记", R.drawable.p4), Manga("炎拳", R.drawable.p5),
        Manga("剑风传奇", R.drawable.p6), Manga("致不灭的你", R.drawable.p7)
    )

    val mangaList = ArrayList<Manga>()

    private fun initManga() {
        mangaList.clear()
        repeat(50) {
            val index = (0 until mangas.size).random()
            mangaList.add(mangas[index])
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        val mActivity = activity as AppCompatActivity
        mActivity.setSupportActionBar(toolbar)

        val actionBar: ActionBar? = mActivity.supportActionBar
        actionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        initManga()
        val layoutManager = GridLayoutManager(context, 3)
        recycleView.layoutManager = layoutManager
        val adapter = MangaAdapter(mActivity, mangaList)
        recycleView.adapter = adapter

        swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        swipeRefresh.setOnRefreshListener {
            thread {
                Thread.sleep(2000)
                activity?.runOnUiThread {
                    initManga()
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
        // Inflate the layout for this fragment
//        setHasOptionsMenu(true)
//        val mActivity = activity as AppCompatActivity
//        mActivity.setSupportActionBar(toolbar)
//        toolbar.inflateMenu(R.menu.nav_menu)
//        toolbar.setOnMenuItemClickListener {
////                when(it.itemId) {
////                    android.R.id.home -> {
////                        drawerLayout.openDrawer(GravityCompat.START)
////                    }
////                }
//                true
//            }
//        toolbar.setTitle("111")

//        val actionBar: ActionBar? = mActivity.supportActionBar
//        actionBar?.let {
//            it.setDisplayHomeAsUpEnabled(true)
//            it.setHomeAsUpIndicator(R.drawable.ic_menu)
//        }

//        navView.setNavigationItemSelectedListener {
//            drawerLayout.closeDrawers()
//            true
//        }


//        initManga()
//        val layoutManager = GridLayoutManager(context, 3)
//        recycleView.layoutManager = layoutManager
//        val adapter = MangaAdapter(mActivity, mangaList)
//        recycleView.adapter = adapter
//
//        swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
//        swipeRefresh.setOnRefreshListener {
//            thread {
//                Thread.sleep(2000)
//                activity?.runOnUiThread {
//                    initManga()
//                    adapter.notifyDataSetChanged()
//                    swipeRefresh.isRefreshing = false
//                }
//            }
//        }

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
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
////            R.id.backup -> {
////                Toast.makeText(this, "Backup", Toast.LENGTH_SHORT).show()
////            }
////
////            R.id.delete -> {
////                Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show()
////            }
//
////            R.id.setting -> {
////                Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show()
////            }
//
//            android.R.id.home -> {
//                drawerLayout.openDrawer(GravityCompat.START)
//            }
//        }
//
//        return true
//    }


}
