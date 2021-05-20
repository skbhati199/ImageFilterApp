package com.fluper.imagefilterapp.imageprocessors.subfilters

import android.graphics.Bitmap
import com.fluper.imagefilterapp.imageprocessors.ImageProcessor
import com.fluper.imagefilterapp.imageprocessors.SubFilter

/**
 * @author varun on 28/07/15.
 */
class SaturationSubFilter(
    /**
     * Get the current saturation level
     */
    // The Level value is float, where level = 1 has no effect on the image
    var saturation: Float
) : SubFilter {
    override fun process(inputImage: Bitmap): Bitmap {
        return ImageProcessor.doSaturation(inputImage, saturation)
    }

    override fun getTag(): Any? {
        return tag
    }

    override fun setTag(tag: Any?) {
        Companion.tag = tag as String?
    }

    fun setLevel(level: Float) {
        saturation = level
    }

    companion object {
        private var tag: String? = ""
    }
}