package com.example.android.booklistingapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Lariveg on 5/06/2017.
 */

public class BookLoader  extends AsyncTaskLoader<List<Book>> {

    /** Tag for log messages */
    private final String LOG_TAG = BookLoader.class.getName();

    /** Query URL */
    private String mUrl;
    private String mSearchQuery;

    public BookLoader(Context context, String url, String searchQuery) {
        super(context);
        mUrl = url;
        mSearchQuery = searchQuery;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Book> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of books.
        List<Book> books = Utils.fetchBookData(mUrl + mSearchQuery);
        return books;
    }

}
