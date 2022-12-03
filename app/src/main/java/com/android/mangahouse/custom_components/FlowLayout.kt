package com.android.mangahouse.custom_components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.android.mangahouse.R

class FlowLayout(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    private val maxLength = 18
    private var list: List<String> = ArrayList()
//    private val screenWidth = px2dp(context, resources.displayMetrics.widthPixels)
    private lateinit var listener : OnItemClickListener

    public fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }


    init {
        orientation = VERTICAL
    }

    fun setList(list: List<String>) {
        this.list = list

//        val viewList = ArrayList<View>()
//        list.forEach {
//            val view = LayoutInflater.from(context).inflate(R.layout.record_item, null)
//            val textView = view.findViewById<TextView>(R.id.recordText)
//            textView.text = it
//            viewList.add(view)
//        }

        removeAllViews()

        var rowLayout = LinearLayout(context)
        addView(rowLayout)

        var len = 0
//        for (view in viewList) {
//            len += (px2dp(context, view.layoutParams.width) + 20)
//            if (len > screenWidth - 50) {
//                rowLayout = LinearLayout(context)
//                addView(rowLayout)
//
//                len = px2dp(context, view.layoutParams.width) + 20
//            }
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

            view.setOnClickListener {
                listener.onClick(str)
            }
        }

    }

//    private fun dp2px(context: Context, dpValue: Int): Int
//    {
//        val scale = context.resources.displayMetrics.density
//        return (dpValue * scale + 0.5f).toInt()
//    }
//
//    private fun px2dp(context: Context, pxValue: Int): Int {
//        val scale = context.resources.displayMetrics.density
//        return (pxValue / scale + 0.5f).toInt()
//    }

}