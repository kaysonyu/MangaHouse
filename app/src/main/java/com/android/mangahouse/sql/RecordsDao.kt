package com.android.mangahouse.sql

import android.content.ContentValues
import android.content.Context
import android.database.Cursor


class RecordsDao(context: Context?) {
    private val recordHelper: MySQLiteOpenHelper =
        MySQLiteOpenHelper(context)
    private val TABLE = "records"

    fun addRecord(record: String) {
        if (!isHasRecord(record)) {
            val db = recordHelper.writableDatabase
            val values = ContentValues()
            values.put("name", record)
            db.insert(TABLE, null, values)
        }
    }

    fun deleteRecord(record: String) {
        val db = recordHelper.writableDatabase
        db.delete(TABLE, "name=?", arrayOf(record))
    }

    fun isHasRecord(record: String): Boolean {
        val db = recordHelper.readableDatabase
        val cursor: Cursor = db.query(TABLE, null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                if (record == cursor.getString(cursor.getColumnIndex("name"))) {
                    cursor.close()
                    return true
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        return false
    }

//    fun querySimlar(record: String): List<String> {
//        val sqlStr = "select * from records where name like '%$record%' order by name "
//        val recordsList: MutableList<String> = ArrayList()
//        val cursor: Cursor = recordHelper.readableDatabase.rawQuery(sqlStr, null)
//        if (cursor.moveToFirst()) {
//            do {
//                val name: String = cursor.getString(cursor.getColumnIndex("name"))
//                recordsList.add(name)
//            } while (cursor.moveToNext())
//        }
//        cursor.close()
//        return recordsList
//    }

    fun getAll(): List<String> {
        val recordsList: MutableList<String> = ArrayList()
        val db = recordHelper.readableDatabase
        val cursor: Cursor = db.query(TABLE, null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val name: String = cursor.getString(cursor.getColumnIndex("name"))
                recordsList.add(name)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return recordsList
    }

    fun deleteAll() {
        val db = recordHelper.writableDatabase
        db.execSQL("delete from records")
    }

}