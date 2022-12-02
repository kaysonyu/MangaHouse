package com.android.mangahouse.sql

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.android.mangahouse.`object`.Manga


class MangasDao(context: Context?) {
    private val mangaHelper: MySQLiteOpenHelper = MySQLiteOpenHelper(context)
    private val TABLE = "mangas"

    fun addManga(manga: Manga) {
        val db = mangaHelper.writableDatabase
        val values = ContentValues()
        values.apply {
            put("site", manga.site)
            put("comicId", manga.comicId)
            put("coverImg", manga.coverImg)
            put("name", manga.name)
            put("chapterNum", manga.chapterNum)
            put("pageNum", manga.pageNum)
        }
        db.insert(TABLE, null, values)
    }

    fun deleteManga(manga: Manga) {
        val db = mangaHelper.writableDatabase
        db.delete(TABLE, "site=? and comicId=?", arrayOf(manga.site, manga.comicId))
    }

    fun isHasManga(manga: Manga): Boolean {
        val db = mangaHelper.readableDatabase
        val cursor: Cursor = db.query(TABLE, null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                if (manga.site == cursor.getString(cursor.getColumnIndex("site")) &&
                    manga.comicId == cursor.getString(cursor.getColumnIndex("comicId"))) {
                    cursor.close()
                    return true
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        return false
    }

    fun getAll(): List<Manga> {
        val mangasList: MutableList<Manga> = ArrayList()
        val db = mangaHelper.readableDatabase
        val cursor: Cursor = db.query(TABLE, null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val manga = Manga(cursor.getString(cursor.getColumnIndex("site")),
                    cursor.getString(cursor.getColumnIndex("comicId")),
                    cursor.getString(cursor.getColumnIndex("coverImg")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getInt(cursor.getColumnIndex("chapterNum")),
                    cursor.getInt(cursor.getColumnIndex("pageNum")))

                mangasList.add(manga)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return mangasList
    }

    fun deleteAll() {
        val db = mangaHelper.writableDatabase
        db.execSQL("delete from mangas")
    }

}