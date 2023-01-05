package com.android.mangahouse.activity

import android.R.layout
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.android.mangahouse.R
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_select.*
import kotlinx.android.synthetic.main.nav_header.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.OutputStream


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), View.OnClickListener {
    val takePhoto = 1
    val fromAlbum = 2
//    val afterCrop = 3

    val profilePic = "photo.jpg"

    lateinit var imageUri: Uri
    lateinit var outputImage: File
    lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        Log.d("uuuu", "${this.resources.displayMetrics.density}, ${resources.displayMetrics.widthPixels}")

        //首页导航页面切换
        val navController = findNavController(R.id.navHostFragment)
        bottomNavView.setupWithNavController(navController)


//        getdatabt.setOnClickListener {
//            val searchRespService = ServiceCreator.create(ComicSearchService::class.java)
//
//            searchRespService.getComicResp("title", "校园").enqueue(object : Callback<ComicResp> {
//
//                override fun onResponse(call: Call<ComicResp>, response: Response<ComicResp>) {
//                    val app = response.body()
//                    if (app != null) {
//                        Log.d("TestTest", "id is ${app.msg}, name is ${app.code}, version is ${app.count}")
//                        for (list in app.data) {
//                            Log.d("TestTest", "${list.author}-${list.cover}-${list.title}")
//                        }
//                    }
//
//                }
//
//
//                override fun onFailure(call: Call<ComicResp>, t: Throwable) {
//                    Log.d("TestTest", "id is ")
//                    t.printStackTrace()
//                }
//
//            })
//        }

//        Glide.with(this)
//            .load("https://image.mangabz.com/1/511/129538/3_6051.jpg?cid=129538&key=3dfee6cee5de9ec4fabe28e4f4789a65&uk=")
//            .into(testImage)

//        getdatabt.setOnClickListener {
//            startActivity(Intent(this, TestActivity::class.java))
//        }

        //侧边栏配置
        val navHeaderIcon = navView.getHeaderView(0).findViewById<ImageView>(R.id.iconImage)
        outputImage = File(externalCacheDir, profilePic)
        imageUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FileProvider.getUriForFile(this, "com.android.mangahouse.photo", outputImage)
        } else {
            Uri.fromFile(outputImage)
        }

        if (outputImage.exists()) {
            val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri))
            navHeaderIcon.setImageBitmap(bitmap)
        }
        else {
            Glide.with(this).load(R.drawable.nav_icon).into(navHeaderIcon)
        }


        navHeaderIcon.setOnClickListener {
            val layout = layoutInflater.inflate(R.layout.dialog_select, null)
            dialog = AlertDialog.Builder(this)
                .setView(layout)
                .create()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.setCanceledOnTouchOutside(true)
            dialog.show()

            val takePhotoBtn = layout.findViewById<TextView>(R.id.takePhotoView)
            val fromAlbumBtn = layout.findViewById<TextView>(R.id.fromAlbumView)
            val cancelBtn = layout.findViewById<TextView>(R.id.cancel)

            takePhotoBtn.setOnClickListener(this)
            fromAlbumBtn.setOnClickListener(this)
            cancelBtn.setOnClickListener(this)
        }

        navView.setNavigationItemSelectedListener { item ->
            when (item.title) {
                "设置" -> {
                    item.isCheckable = false
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.takePhotoView -> {
//                if (outputImage.exists()) {
//                    outputImage.delete()
//                }
//                outputImage.createNewFile()
//
//                imageUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    FileProvider.getUriForFile(this, "com.android.mangahouse.photo", outputImage)
//                } else {
//                    Uri.fromFile(outputImage)
//                }

                val intent = Intent("android.media.action.IMAGE_CAPTURE")
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                startActivityForResult(intent, takePhoto)

                dialog.dismiss()
            }

            R.id.fromAlbumView -> {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)

                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "image/*"
                startActivityForResult(intent, fromAlbum)

                dialog.dismiss()
            }

            R.id.cancel -> {
                dialog.dismiss()
            }
        }
    }
//
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val navHeaderIcon = navView.getHeaderView(0).findViewById<ImageView>(R.id.iconImage)

        when (requestCode) {
            takePhoto -> {
                if (resultCode == Activity.RESULT_OK) {
//                    cropPhoto(imageUri)
                    val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri))
                    val rotateBitmap = rotateIfRequired(bitmap)
                    rotateBitmap.compress(Bitmap.CompressFormat.JPEG, 100, FileOutputStream(outputImage))
                    navHeaderIcon.setImageBitmap(rotateBitmap)
                }
            }

            fromAlbum -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    data.data?.let {uri ->
                        saveFile(outputImage, uri)
                        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri))
                        navHeaderIcon.setImageBitmap(bitmap)
                    }
//                    cropPhoto(data.data)
                }
            }

        }
    }
//

//    private fun cropPhoto(uri: Uri?) {
//
//        val intent = Intent("com.android.camera.action.CROP")
//        intent.setDataAndType(uri, "image/*")
//        intent.putExtra("crop", "true");
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        intent.putExtra("outputX", 150);
//        intent.putExtra("outputY", 150);
//        intent.putExtra("return-data", true);
//
//        startActivityForResult(intent, afterCrop);
//    }

    private fun saveFile(f: File?, uri: Uri) {
        val fd = contentResolver.openFileDescriptor(uri, "r")
        fd?.fileDescriptor?.let {
            val fi = FileInputStream(it)
            f?.outputStream()?.let {
                fi.copyTo(it)
                it.close()
            }
            fi.close()
        }
        fd?.close()
    }

    private fun rotateIfRequired(bitmap: Bitmap): Bitmap {
        val exif = ExifInterface(outputImage.path)

        val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmap(bitmap, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitmap(bitmap, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitmap(bitmap, 270)
            else -> bitmap
        }
    }

    private fun rotateBitmap(bitmap: Bitmap, degree: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())

        val rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        bitmap.recycle()
        return rotateBitmap
    }


}
