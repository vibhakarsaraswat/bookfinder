package com.example.vibs.bookfinder;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Loads a list of BookInfo by using an AsyncTask to perform the
 * network request to the given URL.
 */
public class BookInfoLoader extends AsyncTaskLoader<List<BookInfo>> {

    /** Tag for the log messages */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    // Query URL
    private String mUrl;

    /**
     * Constructs a new {@link BookInfoLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */

    public BookInfoLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<BookInfo> loadInBackground() {
        // Don't perform the request if there are no URL.
        if (mUrl == null) {
            return null;
        }

        // Perform the network reuqest, parse the response, and extract a list of earthquakes.
        List<BookInfo> result = QueryUtils.fetchBookInfoData(mUrl);
        return result;
    }
}
