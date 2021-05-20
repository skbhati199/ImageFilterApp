package com.fluper.imagefilterapp.utils

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Environment.*
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
import android.provider.MediaStore.MediaColumns.*
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


// To Show Toast
var toast: Toast? = null

fun AppCompatActivity.showToast(message: String) {
    if (toast != null) toast!!.cancel()
    toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
    toast?.show()
}

fun showToast(message: String, context: Context) {
    if (toast != null) toast!!.cancel()
    toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
    toast?.show()
}

//
//fun AppCompatActivity.getImageUri(inImage: Bitmap): Uri {
//    val bytes = ByteArrayOutputStream()
//    inImage.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
//
//    val path = MediaStore.Images.Media.insertImage(
//        this.contentResolver, inImage,
//        "Title", null
//    )
//    return Uri.parse(path)
//}


private val dateFormatter = SimpleDateFormat(
    "yyyy.MM.dd 'at' HH:mm:ss z", Locale.getDefault()
)
fun  AppCompatActivity.legacyOrQ(it:Bitmap) : Uri {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        return this.saveImageInQ(it)
    } else {
        return this.legacySave(it)
    }
}

 fun AppCompatActivity.legacySave(bitmap: Bitmap): Uri {
    val appContext = applicationContext
    val filename = "${title}_of_${dateFormatter.format(Date())}.png"
    val directory = getExternalStoragePublicDirectory(DIRECTORY_PICTURES)
    val file = File(directory, filename)
    val outStream = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream)
    outStream.flush()
    outStream.close()
    MediaScannerConnection.scanFile(
        appContext, arrayOf(file.absolutePath),
        null, null
    )
    return FileProvider.getUriForFile(
        appContext, "${appContext.packageName}.provider",
        file
    )
}
 fun AppCompatActivity.saveImageInQ(bitmap: Bitmap): Uri {
    val filename = "${title}_of_${dateFormatter.format(Date())}.png"
    val fos: OutputStream?
    val contentValues = ContentValues().apply {
        put(DISPLAY_NAME, filename)
        put(MIME_TYPE, "image/png")
        put(RELATIVE_PATH, DIRECTORY_DCIM)
        put(IS_PENDING, 1)
    }

    //use application context to get contentResolver
    val contentResolver = applicationContext.contentResolver
    val uri = contentResolver.insert(EXTERNAL_CONTENT_URI, contentValues)
    uri?.let { contentResolver.openOutputStream(it) }.also { fos = it }
    fos?.use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }
    fos?.flush()
    fos?.close()

    contentValues.clear()
    contentValues.put(IS_PENDING, 0)
    uri?.let {
        contentResolver.update(it, contentValues, null, null)
    }
    return uri!!
}


fun getVideoThumbnails(context: Context, videoUri: Uri): java.util.ArrayList<Bitmap> {

    val thumbnailList = java.util.ArrayList<Bitmap>()

    try {
        val mediaMetadataRetriever = MediaMetadataRetriever()
        mediaMetadataRetriever.setDataSource(context, videoUri)

        // Retrieve media data
        val videoLengthInMs = (Integer.parseInt(
            mediaMetadataRetriever.extractMetadata(
                MediaMetadataRetriever.METADATA_KEY_DURATION
            )
        ) * 1000).toLong()

        // Set thumbnail properties (Thumbs are squares)
        val thumbWidth = 512
        val thumbHeight = 512

        val numThumbs = 10

        val interval = videoLengthInMs / numThumbs

        for (i in 0 until numThumbs) {
            var bitmap = mediaMetadataRetriever.getFrameAtTime(
                i * interval,
                MediaMetadataRetriever.OPTION_CLOSEST_SYNC
            )
            try {
                bitmap = Bitmap.createScaledBitmap(bitmap!!, thumbWidth, thumbHeight, false)
            } catch (e: Exception) {
                e.printStackTrace()
                return thumbnailList
            }
            thumbnailList.add(bitmap)
        }

        mediaMetadataRetriever.release()
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return thumbnailList
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


fun getRealPathFromURI(context: Context, contentUri: Uri?): String? {
    var contentUri = contentUri
    var cursor: Cursor?
    var filePath: String? = ""
    when (contentUri) {
        null -> return filePath
        else -> {
            val file = File(contentUri.path)
            when {
                file.exists() -> filePath = file.path
            }
            when {
                !TextUtils.isEmpty(filePath) -> return filePath
                else -> {
                    val proj = arrayOf(MediaStore.Images.Media.DATA)
                    when {
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> {
                            try {
                                val wholeID = DocumentsContract.getDocumentId(contentUri)
                                val id: String
                                when {
                                    wholeID.contains(":") -> id =
                                        wholeID.split(":".toRegex()).dropLastWhile { it.isEmpty() }
                                            .toTypedArray()[1]
                                    else -> id = wholeID
                                }
                                cursor = context.contentResolver.query(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    proj,
                                    MediaStore.Images.Media._ID + "='" + id + "'",
                                    null,
                                    null
                                )
                                when {
                                    cursor != null -> {
                                        val columnIndex = cursor.getColumnIndex(proj[0])
                                        when {
                                            cursor.moveToFirst() -> filePath =
                                                cursor.getString(columnIndex)
                                        }
                                        when {
                                            !TextUtils.isEmpty(filePath) -> contentUri =
                                                Uri.parse(filePath)
                                        }
                                    }
                                }
                            } catch (e: IllegalArgumentException) {
                                e.printStackTrace()
                            }
                        }
                    }
                    when {
                        !TextUtils.isEmpty(filePath) -> return filePath
                        else -> {
                            try {
                                cursor = context.contentResolver.query(
                                    contentUri!!,
                                    proj,
                                    null,
                                    null,
                                    null
                                )
                                when {
                                    cursor == null -> return contentUri.path
                                    cursor.moveToFirst() -> filePath =
                                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                                }

                                when {
                                    !cursor!!.isClosed -> cursor.close()
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                                filePath = contentUri!!.path
                            }

                            when (filePath) {
                                null -> filePath = ""
                            }
                            return filePath
                        }
                    }

                }
            }

        }
    }

}


fun getImageFilePath(): String {
    val file =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/ImageFilter")
    if (!file.exists()) {
        file.mkdirs()
    }
    return file.absolutePath + "/IMG_" + System.currentTimeMillis() + ".jpg"
}
