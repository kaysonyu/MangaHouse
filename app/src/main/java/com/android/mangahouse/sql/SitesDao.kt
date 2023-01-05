package com.android.mangahouse.sql

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.android.mangahouse.`object`.Site

class SitesDao(context: Context?) {
    private val mangaHelper: MySQLiteOpenHelper = MySQLiteOpenHelper(context)
    private val TABLE = "sites"

    fun addSite(site: Site) {
        val db = mangaHelper.writableDatabase
        val values = ContentValues()
        values.apply {
            put("site", site.site)
            put("source_name", site.source_name)
        }
        db.insert(TABLE, null, values)
    }

    fun isHasSite(site: Site): Boolean {
        val db = mangaHelper.readableDatabase
        val cursor: Cursor = db.query(TABLE, null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                if (site.site == cursor.getString(cursor.getColumnIndex("site"))) {
                    cursor.close()
                    return true
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        return false
    }

    fun getAll(): List<Site> {
        val sitesList: MutableList<Site> = ArrayList()
        val db = mangaHelper.readableDatabase
        val cursor: Cursor = db.query(TABLE, null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val site = Site(cursor.getString(cursor.getColumnIndex("site")),
                    cursor.getString(cursor.getColumnIndex("source_name")))
                sitesList.add(site)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return sitesList
    }

    fun deleteAll() {
        val db = mangaHelper.writableDatabase
        db.execSQL("delete from $TABLE")
    }

}
