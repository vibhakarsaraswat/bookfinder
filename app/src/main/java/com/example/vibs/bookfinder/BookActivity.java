package com.example.vibs.bookfinder;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity implements LoaderCallbacks<List<BookInfo>> {

    private static final String LOG_TAG = BookActivity.class.getName();

    private static final String GOOGLE_API_REQUEST_URL =
        "https://www.googleapis.com/books/v1/volumes?maxResults=10&q=";

    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int BookInfo_LOADER_ID = 1;

    /** Adapter for the list of booksFoundInSearch */
    private BookAdapter mAdapter;

    /** Variable to store value for 'searchKey'  */
    private String searchParam;

    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        // Fetching the value of Extra Argument passed in the intent.
        Intent intent = getIntent();
        if (getIntent().getExtras() != null) {
            searchParam = intent.getStringExtra("searchKey");
        }

        // Find the reference to the {@link ListView} in the layout and
        // assign it to a new ListView Object i.e. BooksListView
        // Note: At this moment ListView is EMPTY.
        ListView booksListView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        booksListView.setEmptyView(mEmptyStateTextView);

        // Create a new adapter that takes an empty list of booksFoundInSearch as input
        mAdapter = new BookAdapter(this, new ArrayList<BookInfo>());

        // Now set the mAdapter on the above created ListView i.e.  booksListView,
        // so that list can be populated in the UI.
        booksListView.setAdapter(mAdapter);

        // Set an "item click listener" on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected Book.
        booksListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current Book that was clicked on
                BookInfo currentBookInfo = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri bookInfoUri = Uri.parse(currentBookInfo.getmInfoLink());

                // Create a new intent to view the Book Details
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookInfoUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);

            }
        });

        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();

        /**
         * Initialize the loader. Pass in the int ID constant (BookInfo_LOADER_ID) defined above
         * and pass in null for the bundle.
         * Pass in 'this' activity for the LoaderCallbacks parameter
         * (which is valid because this activity implements the LoaderCallbacks interface).
         */
        loaderManager.initLoader(BookInfo_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<BookInfo>> onCreateLoader(int id, Bundle bundle) {
        return new BookInfoLoader(this, GOOGLE_API_REQUEST_URL + searchParam);
    }

    @Override
    public void onLoadFinished(Loader<List<BookInfo>> loader, List<BookInfo> data) {

        mEmptyStateTextView.setText(R.string.book_not_found);

        // Clear the adapter of previous BookInfo data
        mAdapter.clear();

        // If there is a valid list of {@link BookInfo}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<BookInfo>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}