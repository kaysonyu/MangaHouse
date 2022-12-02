package com.android.mangahouse.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.android.mangahouse.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.bottomNavView)
        val navController = findNavController(R.id.navHostFragment)
        navView.setupWithNavController(navController)

//        getdatabt.setOnClickListener {
//            val searchRespService = ServiceCreator.create(ComicSearchService::class.java)
//
//            searchRespService.getComicResp("title", "校园").enqueue(object : Callback<ComicResp> {
//
//                override fun onResponse(call: Call<ComicResp>, response: Response<ComicResp>) {
//                    val app = response.body()
//                    if (app != null) {
//                        Log.d("TestTest", "id is ${app.msg}, name is ${app.code}, version is ${app.count}")
//                        for (list in app.data) {
//                            Log.d("TestTest", "${list.author}-${list.cover}-${list.title}")
//                        }
//                    }
//
//                }
//
//
//                override fun onFailure(call: Call<ComicResp>, t: Throwable) {
//                    Log.d("TestTest", "id is ")
//                    t.printStackTrace()
//                }
//
//            })
//        }

//        Glide.with(this)
//            .load("https://image.mangabz.com/1/511/129538/3_6051.jpg?cid=129538&key=3dfee6cee5de9ec4fabe28e4f4789a65&uk=")
//            .into(testImage)

//        getdatabt.setOnClickListener {
//            startActivity(Intent(this, TestActivity::class.java))
//        }

    }


}
