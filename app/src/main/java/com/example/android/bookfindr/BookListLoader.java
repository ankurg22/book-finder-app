package com.example.android.bookfindr;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Ankur Gupta on 5/8/17.
 * guptaankur.gupta0@gmail.com
 */

public class BookListLoader extends AsyncTaskLoader<List<Book>> {

    private String mUrl;

    public BookListLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        return QueryUtils.fetchBookData(mUrl);
    }
}
