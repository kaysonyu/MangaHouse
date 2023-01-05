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
            put("siteName", manga.siteName)
            put("comicId", manga.comicId)
            put("coverImg", manga.coverImg)
            put("name", manga.name)
            put("chapterNum", manga.chapterNum)
            put("pageNum", manga.pageNum)
        }
        db.insert(TABLE, null, values)
    }

    fun updateManga(manga: Manga) {
        val db = mangaHelper.writableDatabase
        val values = ContentValues()
        values.apply {
            put("chapterNum", manga.chapterNum)
            put("pageNum", manga.pageNum)
        }
        db.update(TABLE, values, "site=? and comicId=?", arrayOf(manga.site, manga.comicId))
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

    fun getManga(manga: Manga): Manga {
        val db = mangaHelper.readableDatabase
        var mangaQuery: Manga = manga
        if (isHasManga(manga)) {
            val cursor: Cursor = db.query(TABLE, null, "site=? and comicId=?", arrayOf(manga.site, manga.comicId), null, null, null)
            if (cursor.moveToFirst()) {
                do {
                    mangaQuery = Manga(
                        cursor.getString(cursor.getColumnIndex("site")),
                        cursor.getString(cursor.getColumnIndex("siteName")),
                        cursor.getString(cursor.getColumnIndex("comicId")),
                        cursor.getString(cursor.getColumnIndex("coverImg")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getInt(cursor.getColumnIndex("chapterNum")),
                        cursor.getInt(cursor.getColumnIndex("pageNum"))
                    )
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        else {
            mangaQuery = Manga(manga.site, manga.siteName, manga.comicId, manga.coverImg, manga.name, 1, 1)
        }

        return mangaQuery
    }

    fun getAll(): List<Manga> {
        val mangasList: MutableList<Manga> = ArrayList()
        val db = mangaHelper.readableDatabase
        val cursor: Cursor = db.query(TABLE, null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val manga = Manga(cursor.getString(cursor.getColumnIndex("site")),
                    cursor.getString(cursor.getColumnIndex("siteName")),
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