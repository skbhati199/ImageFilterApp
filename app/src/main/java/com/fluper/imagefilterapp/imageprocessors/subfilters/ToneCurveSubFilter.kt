package com.fluper.imagefilterapp.imageprocessors.subfilters

import android.graphics.Bitmap
import com.fluper.imagefilterapp.gemory.BezierSpline.curveGenerator
import com.fluper.imagefilterapp.gemory.Point
import com.fluper.imagefilterapp.imageprocessors.ImageProcessor
import com.fluper.imagefilterapp.imageprocessors.SubFilter

/**
 * @author varun
 * Subfilter to tweak rgb channels of an image
 */
class ToneCurveSubFilter(
    rgbKnots: Array<Point>?,
    redKnots: Array<Point>?,
    greenKnots: Array<Point>?,
    blueKnots: Array<Point>?
) : SubFilter {
    // These are knots which contains the plot points
    lateinit var rgbKnots: Array<Point>
    lateinit var greenKnots: Array<Point>
    private var redKnots: Array<Point>
    private var blueKnots: Array<Point>
    private var rgb: IntArray? = null
    private var r: IntArray? = null
    private var g: IntArray? = null
    private var b: IntArray? = null
    override fun process(inputImage: Bitmap): Bitmap {
        rgbKnots = sortPointsOnXAxis(rgbKnots)
        redKnots = sortPointsOnXAxis(redKnots)
        greenKnots = sortPointsOnXAxis(greenKnots)
        blueKnots = sortPointsOnXAxis(blueKnots)
        if (rgb == null) {
            rgb = curveGenerator(rgbKnots)
        }
        if (r == null) {
            r = curveGenerator(redKnots)
        }
        if (g == null) {
            g = curveGenerator(greenKnots)
        }
        if (b == null) {
            b = curveGenerator(blueKnots)
        }
        return ImageProcessor.applyCurves(rgb, r, g, b, inputImage)
    }

    fun sortPointsOnXAxis(points: Array<Point>): Array<Point> {
        for (s in 1 until points.size - 1) {
            for (k in 0..points.size - 2) {
                if (points[k]!!.x > points[k + 1]!!.x) {
                    var temp = 0f
                    temp = points[k]!!.x
                    points[k]!!.x = points[k + 1]!!.x //swapping values
                    points[k + 1]!!.x = temp
                }
            }
        }
        return points
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

    /**
     * Initialise ToneCurveSubfilter (NOTE : Don't pass null knots, pass straight line instead)
     * Knots are the points in 2D taken by tweaking photoshop channels(plane ranging from 0-255)
     *
     * @param rgbKnots   rgb Knots
     * @param redKnots   Knots in Red Channel
     * @param greenKnots Knots in green Channel
     * @param blueKnots  Knots in Blue channel
     */
    init {
        val straightKnots : Array<Point> = Array(2, {Point(0f,0f)} )
        straightKnots[0] = Point(0f, 0f)
        straightKnots[1] = Point(255.0f, 255.0f)
        if (rgbKnots == null) {
            this.rgbKnots = straightKnots
        } else {
            this.rgbKnots = rgbKnots
        }
        if (redKnots == null) {
            this.redKnots = straightKnots
        } else {
            this.redKnots = redKnots
        }
        if (greenKnots == null) {
            this.greenKnots = straightKnots
        } else {
            this.greenKnots = greenKnots
        }
        if (blueKnots == null) {
            this.blueKnots = straightKnots
        } else {
            this.blueKnots = blueKnots
        }
    }
}