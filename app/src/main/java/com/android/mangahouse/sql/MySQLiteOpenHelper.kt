package com.android.mangahouse.sql

import android.content.Context
import android.database.sqlite.SQLiteDatabase

import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class MySQLiteOpenHelper(context: Context?) : SQLiteOpenHelper(context,
    DB_NAME, null,
    DB_VERSION
) {
    override fun onCreate(db: SQLiteDatabase) {
        val sqlStrSite = "CREATE TABLE IF NOT EXISTS sites (site TEXT, source_name TEXT, PRIMARY KEY(site));"
        db.execSQL(sqlStrSite)

        val sqlStrRecord = "CREATE TABLE IF NOT EXISTS records (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT);"
        db.execSQL(sqlStrRecord)

        val sqlStrSub = "CREATE TABLE IF NOT EXISTS mangas (site TEXT, comicId TEXT, coverImg TEXT, name TEXT, chapterNum INTEGER, pageNum INTEGER, PRIMARY KEY(site,comicId) );"
        db.execSQL(sqlStrSub)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    companion object {
        private const val DB_NAME = "mangahouse.db"
        private const val DB_VERSION = 1
    }
}