package com.fluper.imagefilterapp.imageprocessors

import android.graphics.Bitmap
import com.fluper.imagefilterapp.imageprocessors.NativeImageProcessor.applyChannelCurves
import com.fluper.imagefilterapp.imageprocessors.NativeImageProcessor.applyRGBCurve
import com.fluper.imagefilterapp.imageprocessors.NativeImageProcessor.doBrightness
import com.fluper.imagefilterapp.imageprocessors.NativeImageProcessor.doColorOverlay
import com.fluper.imagefilterapp.imageprocessors.NativeImageProcessor.doContrast
import com.fluper.imagefilterapp.imageprocessors.NativeImageProcessor.doSaturation

/**
 * @author Varun on 29/06/15.
 */
object ImageProcessor {
    fun applyCurves(
        rgb: IntArray?,
        red: IntArray?,
        green: IntArray?,
        blue: IntArray?,
        inputImage: Bitmap
    ): Bitmap {
        // create output bitmap

        // get image size
        val width = inputImage.width
        val height = inputImage.height
        var pixels: IntArray? = IntArray(width * height)
        inputImage.getPixels(pixels, 0, width, 0, 0, width, height)
        if (rgb != null) {
            pixels = applyRGBCurve(pixels, rgb, width, height)
        }
        if (!(red == null && green == null && blue == null)) {
            pixels =
                applyChannelCurves(pixels, red, green, blue, width, height)
        }
        try {
            inputImage.setPixels(pixels, 0, width, 0, 0, width, height)
        } catch (ise: IllegalStateException) {
        }
        return inputImage
    }

    fun doBrightness(value: Int, inputImage: Bitmap): Bitmap {
        val width = inputImage.width
        val height = inputImage.height
        val pixels = IntArray(width * height)
        inputImage.getPixels(pixels, 0, width, 0, 0, width, height)
        doBrightness(pixels, value, width, height)
        inputImage.setPixels(pixels, 0, width, 0, 0, width, height)
        return inputImage
    }

    fun doContrast(value: Float, inputImage: Bitmap): Bitmap {
        val width = inputImage.width
        val height = inputImage.height
        val pixels = IntArray(width * height)
        inputImage.getPixels(pixels, 0, width, 0, 0, width, height)
        doContrast(pixels, value, width, height)
        inputImage.setPixels(pixels, 0, width, 0, 0, width, height)
        return inputImage
    }

    fun doColorOverlay(
        depth: Int,
        red: Float,
        green: Float,
        blue: Float,
        inputImage: Bitmap
    ): Bitmap {
        val width = inputImage.width
        val height = inputImage.height
        val pixels = IntArray(width * height)
        inputImage.getPixels(pixels, 0, width, 0, 0, width, height)
        doColorOverlay(pixels, depth, red, green, blue, width, height)
        inputImage.setPixels(pixels, 0, width, 0, 0, width, height)
        return inputImage
    }

    fun doSaturation(inputImage: Bitmap, level: Float): Bitmap {
        val width = inputImage.width
        val height = inputImage.height
        val pixels = IntArray(width * height)
        inputImage.getPixels(pixels, 0, width, 0, 0, width, height)
        doSaturation(pixels, level, width, height)
        inputImage.setPixels(pixels, 0, width, 0, 0, width, height)
        return inputImage
    }
}