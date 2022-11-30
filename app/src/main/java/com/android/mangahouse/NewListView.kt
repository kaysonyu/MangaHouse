package com.android.mangahouse

import android.content.Context
import android.util.AttributeSet
import android.widget.ListView

class NewListView (context: Context?, attrs: AttributeSet?) : ListView(context, attrs) {
    protected override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val heightSpec =
            MeasureSpec.makeMeasureSpec(Int.MAX_VALUE shr 2, MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, heightSpec)
    }
}
