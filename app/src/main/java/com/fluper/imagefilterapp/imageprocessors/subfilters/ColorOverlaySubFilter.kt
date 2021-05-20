package com.fluper.imagefilterapp.imageprocessors.subfilters

import android.graphics.Bitmap
import com.fluper.imagefilterapp.imageprocessors.ImageProcessor
import com.fluper.imagefilterapp.imageprocessors.SubFilter

/**
 * @author varun
 * Subfilter used to overlay bitmap with the color defined
 */
class ColorOverlaySubFilter
/**
 * Initialize Color Overlay Subfilter
 *
 * @param depth Value ranging from 0-255 {Defining intensity of color overlay}
 * @param red   Red value between 0-1
 * @param green Green value between 0-1
 * @param blue  Blue value between 0-1
 */(// the color overlay depth is between 0-255
    private val colorOverlayDepth: Int, // these values are between 0-1
    private val colorOverlayRed: Float,
    private val colorOverlayGreen: Float,
    private val colorOverlayBlue: Float
) : SubFilter {
    override fun process(inputImage: Bitmap): Bitmap {
        return ImageProcessor.doColorOverlay(
            colorOverlayDepth, colorOverlayRed, colorOverlayGreen, colorOverlayBlue, inputImage
        )
    }

    override fun getTag(): String? {
        return tag
    }

    override fun setTag(tag: Any?) {
        Companion.tag = tag as String?
    }

    companion object {
        private var tag: String? = ""
    }
}