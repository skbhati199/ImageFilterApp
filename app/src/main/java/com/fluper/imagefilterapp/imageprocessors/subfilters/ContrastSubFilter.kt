package com.fluper.imagefilterapp.imageprocessors.subfilters

import android.graphics.Bitmap
import com.fluper.imagefilterapp.imageprocessors.ImageProcessor
import com.fluper.imagefilterapp.imageprocessors.SubFilter

/**
 * @author varun
 * Class to add Contrast Subfilter
 */
class ContrastSubFilter(contrast: Float) : SubFilter {
    /**
     * Get current Contrast level
     */
    /**
     * Sets the contrast value by the value passed in as parameter
     */
    // The value is in fraction, value 1 has no effect
    var contrast = 0f
    override fun process(inputImage: Bitmap): Bitmap {
        return ImageProcessor.doContrast(contrast, inputImage)
    }

    override fun getTag(): String? {
        return tag
    }

    override fun setTag(tag: Any?) {
        Companion.tag = tag as String?
    }

    /**
     * Changes contrast value by the value passed in as a parameter
     */
    fun changeContrast(value: Float) {
        contrast += value
    }

    companion object {
        private var tag: String? = ""
    }

    /**
     * Initialise contrast subfilter
     *
     * @param contrast The contrast value ranges in fraction, value 1 has no effect
     */
    init {
        this.contrast = contrast
    }
}