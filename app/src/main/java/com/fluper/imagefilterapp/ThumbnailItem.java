package com.fluper.imagefilterapp;

import android.graphics.Bitmap;

import com.fluper.imagefilterapp.imageprocessors.Filter;


/**
 * @author Varun on 01/07/15.
 */
public class ThumbnailItem {
    public Bitmap image;
    public Filter filter;

    public ThumbnailItem() {
        image = null;
        filter = new Filter();
    }
}
