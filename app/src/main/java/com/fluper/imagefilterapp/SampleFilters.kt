package com.fluper.imagefilterapp

import com.fluper.imagefilterapp.gemory.Point
import com.fluper.imagefilterapp.imageprocessors.Filter
import com.fluper.imagefilterapp.imageprocessors.subfilters.BrightnessSubFilter
import com.fluper.imagefilterapp.imageprocessors.subfilters.ContrastSubFilter
import com.fluper.imagefilterapp.imageprocessors.subfilters.ToneCurveSubFilter

/**
 * @author Varun on 01/07/15.
 */
object SampleFilters {
    val starLitFilter: Filter
        get() {
            val rgbKnots: Array<Point> = Array(8, { Point(0f, 0f) })
            rgbKnots[0] = Point(0f, 0f)
            rgbKnots[1] = Point(34f, 6f)
            rgbKnots[2] = Point(69f, 23f)
            rgbKnots[3] = Point(100f, 58f)
            rgbKnots[4] = Point(150f, 154f)
            rgbKnots[5] = Point(176f, 196f)
            rgbKnots[6] = Point(207f, 233f)
            rgbKnots[7] = Point(255f, 255f)
            val filter = Filter()
            filter.addSubFilter(ToneCurveSubFilter(rgbKnots, null, null, null))
            return filter
        }
    val blueMessFilter: Filter
        get() {
            val redKnots: Array<Point> = Array(8,{ Point(0f,0f) })
            redKnots[0] = Point(0f, 0f)
            redKnots[1] = Point(86f, 34f)
            redKnots[2] = Point(117f, 41f)
            redKnots[3] = Point(146f, 80f)
            redKnots[4] = Point(170f, 151f)
            redKnots[5] = Point(200f, 214f)
            redKnots[6] = Point(225f, 242f)
            redKnots[7] = Point(255f, 255f)
            val filter = Filter()
            filter.addSubFilter(ToneCurveSubFilter(null, redKnots, null, null))
            filter.addSubFilter(BrightnessSubFilter(30))
            filter.addSubFilter(ContrastSubFilter(1f))
            return filter
        }
    val aweStruckVibeFilter: Filter
        get() {
            val rgbKnots: Array<Point> = Array(5,{Point(0f,0f)})
            rgbKnots[0] = Point(0f, 0f)
            rgbKnots[1] = Point(80f, 43f)
            rgbKnots[2] = Point(149f, 102f)
            rgbKnots[3] = Point(201f, 173f)
            rgbKnots[4] = Point(255f, 255f)
            val redKnots: Array<Point> = Array(5,{Point(0f,0f)})
            redKnots[0] = Point(0f, 0f)
            redKnots[1] = Point(125f, 147f)
            redKnots[2] = Point(177f, 199f)
            redKnots[3] = Point(213f, 228f)
            redKnots[4] = Point(255f, 255f)
            val greenKnots: Array<Point> = Array(6,{Point(0f,0f)})
            greenKnots[0] = Point(0f, 0f)
            greenKnots[1] = Point(57f, 76f)
            greenKnots[2] = Point(103f, 130f)
            greenKnots[3] = Point(167f, 192f)
            greenKnots[4] = Point(211f, 229f)
            greenKnots[5] = Point(255f, 255f)
            val blueKnots: Array<Point> = Array(7,{Point(0f,0f)})
            blueKnots[0] = Point(0f, 0f)
            blueKnots[1] = Point(38f, 62f)
            blueKnots[2] = Point(75f, 112f)
            blueKnots[3] = Point(116f, 158f)
            blueKnots[4] = Point(171f, 204f)
            blueKnots[5] = Point(212f, 233f)
            blueKnots[6] = Point(255f, 255f)
            val filter = Filter()
            filter.addSubFilter(ToneCurveSubFilter(rgbKnots, redKnots, greenKnots, blueKnots))
            return filter
        }

    // Check whether output is null or not.
    val limeStutterFilter: Filter
        get() {
            val blueKnots: Array<Point> = Array(3,{Point(0f,0f)})
            blueKnots[0] = Point(0f, 0f)
            blueKnots[1] = Point(165f, 114f)
            blueKnots[2] = Point(255f, 255f)
            // Check whether output is null or not.
            val filter = Filter()
            filter.addSubFilter(ToneCurveSubFilter(null, null, null, blueKnots))
            return filter
        }
    val nightWhisperFilter: Filter
        get() {
            val rgbKnots: Array<Point> = Array(3,{Point(0f,0f)})
            rgbKnots[0] = Point(0f, 0f)
            rgbKnots[1] = Point(174f, 109f)
            rgbKnots[2] = Point(255f, 255f)
            val redKnots: Array<Point> = Array(4,{Point(0f,0f)})
            redKnots[0] = Point(0f, 0f)
            redKnots[1] = Point(70f, 114f)
            redKnots[2] = Point(157f, 145f)
            redKnots[3] = Point(255f, 255f)
            val greenKnots: Array<Point> = Array(3,{Point(0f,0f)})
            greenKnots[0] = Point(0f, 0f)
            greenKnots[1] = Point(109f, 138f)
            greenKnots[2] = Point(255f, 255f)
            val blueKnots: Array<Point> = Array(3,{Point(0f,0f)})
            blueKnots[0] = Point(0f, 0f)
            blueKnots[1] = Point(113f, 152f)
            blueKnots[2] = Point(255f, 255f)
            val filter = Filter()
            filter.addSubFilter(ToneCurveSubFilter(rgbKnots, redKnots, greenKnots, blueKnots))
            return filter
        }
}