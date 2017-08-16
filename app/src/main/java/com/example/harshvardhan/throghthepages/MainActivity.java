package com.example.harshvardhan.throghthepages;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
                implements LoaderCallbacks<List<book_attributes>>{


    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes";
    private static final int BOOK_LOADER_ID = 1;
    private BooksAdapter mAdapter;
    private String bookname = "";
    private EditText mBookinput;
    private LoaderManager loaderManager;
    private ConnectivityManager connMgr;
    private TextView mEmptyStateTextView;
    private boolean noBooks ;
    private Boolean firstTime = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView BooksListView = (ListView) findViewById(R.id.ListView_xml);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        BooksListView.setEmptyView(mEmptyStateTextView);

        mBookinput = (EditText) findViewById(R.id.Input);

        mAdapter = new BooksAdapter(this, new ArrayList<book_attributes>());
        BooksListView.setAdapter(mAdapter);

        connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);



        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText("no internet connection");
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        firstTime = null;
    }

    private boolean isFirstTime() {
        if(firstTime == null){
            firstTime = true;
        }
        else {
            firstTime = false;
        }
        return firstTime;
    }

    public void FindBook(View view){

        bookname = mBookinput.getText().toString();
        loaderManager.restartLoader(BOOK_LOADER_ID, null, this);
        mBookinput.setText("");


    }


    @Override
    public Loader<List<book_attributes>> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Uri.parse(BASE_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("q", bookname);
        uriBuilder.appendQueryParameter("maxResults", "5");

        return new BookLoader( this, uriBuilder.toString());
    }

    @Override
    protected void onPause() {
        super.onPause();

        firstTime = null;
    }

    @Override
    public void onLoadFinished(Loader<List<book_attributes>> loader, List<book_attributes> data) {

        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);



        mAdapter.clear();
        if (data != null && !data.isEmpty()){
            mAdapter.addAll(data);
        }
        else {
            if (!isFirstTime())
            Toast.makeText(getApplicationContext(),"No books available",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<book_attributes>> loader) {
        mAdapter.clear();
    }
}

