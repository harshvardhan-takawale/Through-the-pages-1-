package com.example.harshvardhan.throghthepages;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.List;

/**
 * Created by lenovo on 6/6/2017.
 */

public class BookLoader extends AsyncTaskLoader<List<book_attributes>> {

    private static final String LOG_TAG = BookLoader.class.getName();

    private String mUrl;

    public BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<book_attributes> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        List<book_attributes> allBooks = QueryUtils.fetchBooksdata(mUrl);
        return allBooks ;
    }
}
