package com.compose.baseapp.tool

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import coil.load
import com.compose.baseapp.MyApplication
import com.huantansheng.easyphotos.EasyPhotos
import com.huantansheng.easyphotos.callback.SelectCallback
import com.huantansheng.easyphotos.engine.ImageEngine
import com.huantansheng.easyphotos.models.album.entity.Photo
import com.yalantis.ucrop.UCrop
import java.io.File
import java.util.ArrayList

object PhotoPickerUtils {
    @JvmName("getImageEngine1")
    fun getImageEngine(): ImageEngine {
        return if (imageEngine == null) {
            imageEngine = object : ImageEngine {
                override fun loadPhoto(
                    context: Context,
                    uri: Uri,
                    imageView: ImageView
                ) {
                    imageView.load(uri)
                }

                override fun loadGifAsBitmap(
                    context: Context,
                    gifUri: Uri,
                    imageView: ImageView
                ) {
                }

                override fun loadGif(
                    context: Context,
                    gifUri: Uri,
                    imageView: ImageView
                ) {
                }

                override fun getCacheBitmap(
                    context: Context,
                    uri: Uri,
                    width: Int,
                    height: Int
                ): Bitmap? {
                    return null
                }

            }
            imageEngine as ImageEngine
        } else imageEngine!!
    }

    private var imageEngine: ImageEngine? = null
    fun choseImage(
        choseResult: ((
            photos: ArrayList<Photo>?,
            isOriginal: Boolean
        ) -> Unit)? = null
    ) {
        EasyPhotos
            .createAlbum(
                MyApplication.curActivity,
                true,
                false,
                PhotoPickerUtils.getImageEngine()
            ).setFileProviderAuthority("${MyApplication.INSTANT.packageName}.fileprovide")
            .setPuzzleMenu(false)
            .setCleanMenu(false)
            .start(object : SelectCallback() {
                override fun onResult(
                    photos: ArrayList<Photo>?,
                    isOriginal: Boolean
                ) {
                    choseResult?.let { it(photos, isOriginal) }
                }

                override fun onCancel() {

                }

            })
    }

    //裁剪
    fun crop(sourceUri: Uri) {
        var destinationUri =
            Uri.fromFile(File(MyApplication.curActivity.cacheDir, "tempCropImage.jpg"))
        UCrop.of(sourceUri, destinationUri)
            .withAspectRatio(1f, 1f)
            .start(MyApplication.curActivity)
    }
}