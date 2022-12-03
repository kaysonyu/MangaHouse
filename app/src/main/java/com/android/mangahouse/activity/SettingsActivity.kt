package com.android.mangahouse.activity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.android.mangahouse.R
import kotlinx.android.synthetic.main.activity_read.*
import kotlinx.android.synthetic.main.activity_settings.*


@Suppress("DEPRECATION")
class SettingsActivity : AppCompatActivity() {

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setSupportActionBar(settingToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listOf("横屏阅读", "竖屏阅读"))
//        spinner.adapter = adapter
        val readSetting = getSharedPreferences("settings", Context.MODE_PRIVATE)
        spinner.setSelection(readSetting.getInt("screenOrientation", 0))
        val that = this
        this.spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            @SuppressLint("CommitPrefEdits")
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (spinner.selectedItem == "横屏阅读") {
                   val editor = getSharedPreferences("settings", Context.MODE_PRIVATE).edit()
                    editor.putInt("screenOrientation", 0)
                    editor.apply()
                }
                else {
                    val editor = getSharedPreferences("settings", Context.MODE_PRIVATE).edit()
                    editor.putInt("screenOrientation", 1)
                    editor.apply()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

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
