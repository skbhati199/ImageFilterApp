package com.fluper.imagefilterapp

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fluper.imagefilterapp.databinding.ActivityMainBinding
import com.fluper.imagefilterapp.imageprocessors.Filter
import com.fluper.imagefilterapp.images.ImageListener
import com.fluper.imagefilterapp.images.ImageUtils
import com.fluper.imagefilterapp.images.IntentConstant
import com.fluper.imagefilterapp.utils.RealPathUtil.getRealPath
import com.fluper.imagefilterapp.utils.showToast
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import java.io.File

class MainActivity : AppCompatActivity(), ThumbnailCallback, ImageListener {

    private lateinit var binding: ActivityMainBinding

    private var activity: Activity? = null
    private var thumbListView: RecyclerView? = null
    private var placeHolderImageView: ImageView? = null

    var uri:Uri = Uri.EMPTY

    init {
        System.loadLibrary("native-lib")
    }

    companion object {
//        System.loadLibrary("native-lib")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activity = this
//        binding.tvPrint.text = stringFromJNI()
        initUIWidgets()
        binding.btnChoose.setOnClickListener {
            setImage()
        }

    }


    private fun setImage() {
        val permissions =
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        Permissions.check(this, permissions, null, null,
            object : PermissionHandler() {
                override fun onGranted() {
                    ImageUtils.selectImageMsgDialog(this@MainActivity)
                }
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IntentConstant.GALLERY, IntentConstant.CAMERA -> {
                data?.let { ImageUtils.onActivityResult(requestCode, resultCode, it, this, this) }
            }
        }
    }


    override fun getImageData(uri: Uri?, bm: Bitmap?, file: File?, type: Int) {
        try {
            if (uri != null) {
                this.uri= uri
            }
            placeHolderImageView?.setImageURI(uri)
            initHorizontalList()
        }catch (e:Exception){
            showToast("Please try again, something went wrong")
            e.printStackTrace()
        }
    }

    private fun initUIWidgets() {
        thumbListView = findViewById<RecyclerView>(R.id.thumbnails)
        placeHolderImageView = findViewById<ImageView>(R.id.place_holder_imageview)
        placeHolderImageView?.setImageBitmap(
            Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(
                    this.applicationContext.resources,
                    R.drawable.photo
                ), 640, 640, false
            )
        )
        initHorizontalList()
    }

    private fun initHorizontalList() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        layoutManager.scrollToPosition(0)
        thumbListView!!.layoutManager = layoutManager
        thumbListView!!.setHasFixedSize(true)
        bindDataToAdapter()
    }

    private fun bindDataToAdapter() {
        val context: Context = this.application
        val handler = Handler()
        val r = Runnable {
            val bitmap = if (uri == Uri.EMPTY) {
                BitmapFactory.decodeResource(
                    context.resources,
                    R.drawable.photo
                )
            } else {
                ThumbnailsManager.clearThumbs()
                MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri)
            }
            val thumbImage = Bitmap.createScaledBitmap(
                bitmap, 640, 640, false
            )
            val t1 = ThumbnailItem()
            val t2 = ThumbnailItem()
            val t3 = ThumbnailItem()
            val t4 = ThumbnailItem()
            val t5 = ThumbnailItem()
            val t6 = ThumbnailItem()
            t1.image = thumbImage
            t2.image = thumbImage
            t3.image = thumbImage
            t4.image = thumbImage
            t5.image = thumbImage
            t6.image = thumbImage
            ThumbnailsManager.clearThumbs()
            ThumbnailsManager.addThumb(t1) // Original Image
            t2.filter = SampleFilters.starLitFilter
            ThumbnailsManager.addThumb(t2)
            t3.filter = SampleFilters.blueMessFilter
            ThumbnailsManager.addThumb(t3)
            t4.filter = SampleFilters.aweStruckVibeFilter
            ThumbnailsManager.addThumb(t4)
            t5.filter = SampleFilters.limeStutterFilter
            ThumbnailsManager.addThumb(t5)
            t6.filter = SampleFilters.nightWhisperFilter
            ThumbnailsManager.addThumb(t6)
            val thumbs = ThumbnailsManager.processThumbs(context)
            val adapter = ThumbnailsAdapter(thumbs, activity as ThumbnailCallback?)
            thumbListView!!.adapter = adapter
            adapter.notifyDataSetChanged()
        }
        handler.postDelayed(r,100)
    }

    override fun onThumbnailClick(filter: Filter) {
        if (uri == Uri.EMPTY) {
            placeHolderImageView!!.setImageBitmap(
                filter.processFilter(
                    Bitmap.createScaledBitmap(
                        BitmapFactory.decodeResource(
                            this.applicationContext.resources,
                            R.drawable.photo
                        ),
                        640,
                        640,
                        false
                    )
                )
            )
        } else {
            placeHolderImageView!!.setImageBitmap(filter.processFilter(Bitmap.createScaledBitmap(
                BitmapFactory.decodeFile(getRealPath(this,uri)),
                640,
                640,
                false
            )))
        }
    }


    external fun stringFromJNI(): String


}