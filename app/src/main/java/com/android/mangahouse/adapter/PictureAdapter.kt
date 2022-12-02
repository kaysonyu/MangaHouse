package com.android.mangahouse.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.mangahouse.R
import com.bumptech.glide.Glide
import kotlin.math.sqrt


class PictureAdapter (val context: Context, val pictureList: List<String>): RecyclerView.Adapter<PictureAdapter.ViewHolder>() {
    inner class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
        val comicPicture: ImageView = view.findViewById(R.id.comicPicture)
        val comicPageNum: TextView = view.findViewById(R.id.comicPageNum)
    }

//    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(LayoutInflater.from(context).inflate(R.layout.picture_item, parent, false))
//        val img = viewHolder.comicPicture
//        img.setOnTouchListener(object : View.OnTouchListener{
//            val firstPoint = Point()
//            val secondPoint = Point()
//            val matrix = Matrix()
//            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//                if (event != null) {
//                    val action = event.actionMasked
//                    var x = event.x.toInt()
//                    var y = event.y.toInt()
//
//                    when {
//                        action==MotionEvent.ACTION_DOWN -> {
//                            img.scaleType = ImageView.ScaleType.MATRIX
//                        }
//                        action==MotionEvent.ACTION_POINTER_DOWN -> {
//                            firstPoint.set(x, y)
//                            x = event.getX(1).toInt()
//                            y = event.getY(1).toInt()
//                            secondPoint.set(x, y)
//                        }
//                        action==MotionEvent.ACTION_MOVE -> {
//                            if (event.pointerCount >= 2) {
//                                val x1 = event.getX(1).toInt()
//                                val y1 = event.getY(1).toInt()
//
//                                val nowDis = getDistance(x, y, x1, y1)
//                                val lastDis = getDistance(firstPoint.x, firstPoint.y, secondPoint.x, secondPoint.y)
//
//                                val scale = nowDis * 1.0f / lastDis
//
//                                matrix.set(img.imageMatrix)
//                                matrix.postScale(scale, scale, (x+x1)/2.0f, (y+y1)/2.0f)
//                                img.imageMatrix = matrix
//
//                                firstPoint.set(x, y)
//                                secondPoint.set(x1, y1)
//                            }
//                        }
//                        (action==MotionEvent.ACTION_CANCEL || action==MotionEvent.ACTION_UP) -> {
//                            val m = img.imageMatrix
//
//                            val rectF = RectF(0f, 0f, img.drawable.intrinsicWidth.toFloat(), img.drawable.intrinsicHeight.toFloat())
//                            m.mapRect(rectF)
//
//                            val postX = img.width / 2 - (rectF.right + rectF.left) / 2
//                            val postY = img.height / 2 - (rectF.bottom + rectF.top) / 2
//
//                            matrix.set(m);
//                            matrix.postTranslate(postX, postY);
//
//                            img.imageMatrix = matrix;
//                        }
//                    }
//
//                }
//                return false
//            }
//
//        })

        return viewHolder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val picture = pictureList[position]
        Glide.with(context)
            .load(picture)
            .into(holder.comicPicture)
        holder.comicPageNum.text = "${position+1}/${pictureList.size}"
    }

    override fun getItemCount() = pictureList.size

}
