package com.fluper.imagefilterapp.imageprocessors

import android.graphics.Bitmap

interface SubFilter {
    fun process(inputImage: Bitmap) : Bitmap

    fun getTag(): Any?

    fun setTag(tag: Any?)
}