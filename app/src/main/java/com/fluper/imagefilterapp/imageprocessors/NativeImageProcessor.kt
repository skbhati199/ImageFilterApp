package com.fluper.imagefilterapp.imageprocessors

/**
 * @author Varun on 30/06/15.
 */
object NativeImageProcessor {
    
    external fun applyRGBCurve(
        pixels: IntArray?,
        rgb: IntArray?,
        width: Int,
        height: Int
    ): IntArray?

    
    external fun applyChannelCurves(
        pixels: IntArray?,
        r: IntArray?,
        g: IntArray?,
        b: IntArray?,
        width: Int,
        height: Int
    ): IntArray?

    
    external fun doBrightness(pixels: IntArray?, value: Int, width: Int, height: Int): IntArray?
    
    external fun doContrast(pixels: IntArray?, value: Float, width: Int, height: Int): IntArray?
    
    external fun doColorOverlay(
        pixels: IntArray?, depth: Int,
        red: Float, green: Float, blue: Float,
        width: Int, height: Int
    ): IntArray?

    
    external fun doSaturation(pixels: IntArray?, level: Float, width: Int, height: Int): IntArray?



}