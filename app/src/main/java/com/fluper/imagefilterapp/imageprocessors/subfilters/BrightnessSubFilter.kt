package com.fluper.imagefilterapp.imageprocessors.subfilters

import android.graphics.Bitmap
import com.fluper.imagefilterapp.imageprocessors.ImageProcessor
import com.fluper.imagefilterapp.imageprocessors.SubFilter

/**
 * @author varun
 * subfilter used to tweak brightness of the Bitmap
 */
class BrightnessSubFilter(brightness: Int) : SubFilter {
    /**
     * Get current Brightness level
     */
    // Value is in integer
    var brightness = 0
    override fun process(inputImage: Bitmap): Bitmap {
        return ImageProcessor.doBrightness(brightness, inputImage)
    }

    override fun getTag(): String? {
        return tag
    }

    override fun setTag(tag: Any?) {
        Companion.tag = tag as String?
    }

    /**
     * Changes the brightness by the value passed as parameter
     */
    fun changeBrightness(value: Int) {
        brightness += value
    }

    companion object {
        private var tag: String? = ""
    }

    /**
     * Takes Brightness of the image
     *
     * @param brightness Integer brightness value {value 0 has no effect}
     */
    init {
        this.brightness = brightness
    }
}