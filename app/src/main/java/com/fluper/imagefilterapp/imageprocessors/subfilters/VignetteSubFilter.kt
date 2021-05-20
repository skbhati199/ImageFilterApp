package com.fluper.imagefilterapp.imageprocessors.subfilters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import com.fluper.imagefilterapp.R
import com.fluper.imagefilterapp.imageprocessors.SubFilter

/**
 * @author varun
 * Subfilter to add Vignette effect on an image
 */
class VignetteSubFilter(private val context: Context, alpha: Int) : SubFilter {
    // value of alpha is between 0-255
    private var alpha = 0
    override fun process(inputImage: Bitmap): Bitmap {
        var vignette = BitmapFactory.decodeResource(context.resources, R.drawable.photo)
        vignette = Bitmap.createScaledBitmap(vignette!!, inputImage.width, inputImage.height, true)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.alpha = alpha
        val comboImage = Canvas(inputImage)
        comboImage.drawBitmap(vignette, 0f, 0f, paint)
        return inputImage
    }

    override fun getTag(): Any? {
        return tag
    }

    override fun setTag(tag: Any?) {
        Companion.tag = tag as String?
    }

    /**
     * Change alpha value to new value
     */
    fun setAlpha(alpha: Int) {
        this.alpha = alpha
    }

    /**
     * Changes alpha value by that number
     */
    fun changeAlpha(value: Int) {
        alpha += value
        if (alpha > 255) {
            alpha = 255
        } else if (alpha < 0) {
            alpha = 0
        }
    }

    companion object {
        private var tag: String? = ""
    }

    /**
     * Initialise Vignette subfilter
     *
     * @param alpha value of alpha ranges from 0-255 (Intensity of Vignette effect)
     */
    init {
        this.alpha = alpha
    }
}