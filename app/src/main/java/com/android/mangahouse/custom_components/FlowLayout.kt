package com.android.mangahouse.custom_components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.android.mangahouse.R
import com.android.mangahouse.sql.RecordsDao

class FlowLayout(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    private val maxLength = 18
    private var list: List<String> = ArrayList()
    private val recordsDao: RecordsDao = RecordsDao(context)

    init {
        orientation = VERTICAL
    }

    fun setList(list: List<String>) {
        this.list = list

        removeAllViews()

        var rowLayout = LinearLayout(context)
        addView(rowLayout)

        var len = 0
        for (str in list) {
            len += str.length
            if (len > maxLength) {
                rowLayout = LinearLayout(context)
                addView(rowLayout)
                len = str.length
            }
            val view = LayoutInflater.from(context).inflate(R.layout.record_item, null)
            val textView = view.findViewById<TextView>(R.id.recordText)
            textView.text = str
            rowLayout.addView(view)

        }

    }

}