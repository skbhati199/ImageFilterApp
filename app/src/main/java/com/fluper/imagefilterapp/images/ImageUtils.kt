package com.fluper.imagefilterapp.images

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.fluper.imagefilterapp.R
import com.fluper.imagefilterapp.databinding.DialogueImagerChooserBinding
import com.fluper.imagefilterapp.utils.*
import java.io.*


object ImageUtils {
    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    fun convertDpToPixel(dp: Float, context: Context): Float {
        return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    fun convertPixelsToDp(px: Float, context: Context): Float {
        return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }


    fun selectImageDialog(context: Context?) {
        val option = arrayOf("Take Photo", "Choose From Library")
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Choose Action")
        builder.setItems(option) { dialogInterface, i ->
            if (i == 0) {
                openCamera(context)
            } else if (i == 1) {
                openGallery(context)
            } else if (i == 2) {
                openVideo(context)
            }
        }
        builder.show()
    }

    fun selectImageMsgDialog(context: Context?) {

        val bindingDialogView = DialogueImagerChooserBinding.inflate(LayoutInflater.from(context))

        val mBuilder = AlertDialog.Builder(context)
            .setView(bindingDialogView.root)
            .create()
        mBuilder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mBuilder.setCancelable(true)
        mBuilder.show()
        bindingDialogView.btnCamera.setOnClickListener {
            mBuilder.dismiss()

            openCamera(context)
        }
        bindingDialogView.btnGallery.setOnClickListener {
            mBuilder.dismiss()

            openGallery(context)
        }


    }

    fun selectImageVideoDialog(context: Context?) {
        val option = arrayOf("Take Photo", "Choose From Library", "Select Video From Gallery")
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Choose Action")
        builder.setItems(option) { dialogInterface, i ->
            if (i == 0) {
                openCamera(context)
            } else if (i == 1) {
                openGallery(context)
            } else if (i == 2) {
                openVideo(context)
            }
        }
        builder.show()
    }

    private fun openCamera(context: Context?) {
        val cameraintent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        (context as AppCompatActivity).startActivityForResult(cameraintent, IntentConstant.CAMERA)
    }

    private fun openGallery(context: Context?) {
        val galleryintent = Intent(Intent.ACTION_GET_CONTENT)
        galleryintent.type = "image/*"
        (context as AppCompatActivity).startActivityForResult(galleryintent, IntentConstant.GALLERY)
    }

    private fun openVideo(context: Context?) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "video/*"
        (context as AppCompatActivity).startActivityForResult(intent, IntentConstant.VIDEO)
    }

    fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent,
        context: Context,
        mListener: ImageListener
    ) {
        if (requestCode == IntentConstant.GALLERY && resultCode == Activity.RESULT_OK) {
            val imageuri = data?.data
            val file = compressImageFile(context, imageuri!!)
            mListener.getImageData(imageuri, null, file, IntentConstant.MEDIA_TYPE_IMAGE)

        } else if (requestCode == IntentConstant.CAMERA && resultCode == Activity.RESULT_OK) {
            val bitmap: Bitmap = data!!.extras!!.get("data") as Bitmap
            val imageuri = (context as AppCompatActivity).legacyOrQ(bitmap)
            val file = compressImageFile(context, imageuri)
            mListener.getImageData(imageuri, bitmap, file, IntentConstant.MEDIA_TYPE_IMAGE)
        } else if (requestCode == IntentConstant.VIDEO && resultCode == Activity.RESULT_OK) {
            val videoUri = data?.data

            val file = File(
                RealPathUtil.getRealPath(
                    context!!,
                    videoUri!!
                )!!
            )
            var length = file.length()
            if (file.path.isNotEmpty()) {
                length = length.div(1024).div(1024)
                Log.e("MyVideo", length.toString() + " MB")
                if (length.toInt() > 15) {
                    (context as AppCompatActivity).showToast(
                        context.resources.getString(
                            R.string.error_video_limit
                        )
                    )
                } else {
                    val bm = videoUri?.let { getVideoThumbnails(context, it) }!![0]
                    mListener.getImageData(
                        (context as AppCompatActivity).legacyOrQ(bm),
                        bm,
                        file,
                        IntentConstant.MEDIA_TYPE_VIDEO
                    )
                }
            } else {
                (context as AppCompatActivity).showToast("Video Error")
            }
        }
    }
}



const val IMAGE_MAX_SIZE = 1024
fun compressImageFile(context: Context?, pathUri: Uri): File {
    var b: Bitmap? = null

    var realPath: String? =
        getRealPathFromURI(context!!, pathUri)
    var f: File = File(realPath)

//Decode image size
    var o: BitmapFactory.Options = BitmapFactory.Options()
    o.inJustDecodeBounds = true

    var fis: FileInputStream
    try {
        fis = FileInputStream(f)
        BitmapFactory.decodeStream(fis, null, o)
        fis.close()
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }


    var scale = 1
    if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
        scale = Math.pow(
            2.0,
            Math.ceil(
                Math.log(
                    IMAGE_MAX_SIZE / Math.max(
                        o.outHeight,
                        o.outWidth
                    ).toDouble()
                ) / Math.log(0.5)
            )
        ).toInt()
    }

//Decode with inSampleSize
    var o2: BitmapFactory.Options = BitmapFactory.Options()
    o2.inSampleSize = scale
    try {
        fis = FileInputStream(f)
        b = BitmapFactory.decodeStream(fis, null, o2)
        fis.close()
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }

    var destFile = File(getImageFilePath())
    try {
        var out: FileOutputStream = FileOutputStream(destFile)
        b?.compress(Bitmap.CompressFormat.PNG, 90, out)
        out.flush()
        out.close()

    } catch (e: Exception) {
        e.printStackTrace()
    }
    return destFile
}



interface ImageListener {
    fun getImageData(uri: Uri?, bm: Bitmap?, file: File?, type: Int)
}