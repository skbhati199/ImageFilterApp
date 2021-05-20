package com.fluper.imagefilterapp;


import com.fluper.imagefilterapp.imageprocessors.Filter;

/**
 * @author Varun on 01/07/15.
 */
public interface ThumbnailCallback {

    void onThumbnailClick(Filter filter);
}
