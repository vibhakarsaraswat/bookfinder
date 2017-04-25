package com.example.vibs.bookfinder;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity {

    private static final String LOG_TAG = BookActivity.class.getName();

    private static final String GOOGLE_API_REQUEST_URL =
        "https://www.googleapis.com/books/v1/volumes?maxResults=10&q=";
        // "https://www.googleapis.com/books/v1/volumes?q=&maxResults=10" + searchParam ;

    /** Adapter for the list of booksFoundInSearch */
    private BookAdapter mAdapter;

    private String searchParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        Intent intent = getIntent();
        if (getIntent().getExtras() != null) {
            searchParam = intent.getStringExtra("searchKey");
        }

        // Create a new adapter that takes an empty list of booksFoundInSearch as input
        mAdapter = new BookAdapter(this, new ArrayList<BookInfo>());

        // Find the reference to the {@link ListView} in the layout and
        // assign it to a new ListView Object i.e. BooksListView
        ListView booksListView = (ListView) findViewById(R.id.list);

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

        if (searchParam != null) {
            // Start the AsyncTask to fetch the BookInfo data
            BookInfoAsyncTask task = new BookInfoAsyncTask();

            task.execute(GOOGLE_API_REQUEST_URL+searchParam);
        }

    }

    /**
     * {@link AsyncTask} to perform the network request on a background thread, and then
     * update the UI with the list of earthquakes in the response.
     *
     * AsyncTask has three generic parameters: the input type, a type used for progress updates, and
     * an output type. Our task will take a String URL, and return an Earthquake. We won't do
     * progress updates, so the second generic is just Void.
     *
     * We'll only override two of the methods of AsyncTask: doInBackground() and onPostExecute().
     * The doInBackground() method runs on a background thread, so it can run long-running code
     * (like network activity), without interfering with the responsiveness of the app.
     * Then onPostExecute() is passed the result of doInBackground() method, but runs on the
     * UI thread, so it can use the produced data to update the UI.
     */
    private class BookInfoAsyncTask extends AsyncTask<String, Void, List<BookInfo>> {

        /**
         * This method runs on a background thread and performs the network request.
         * We should not update the UI from a background thread, so we return a list of
         * {@link BookInfo}s as the result.
         */
        @Override
        protected List<BookInfo> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null
            if(urls.length < 1 || urls[0] == null) {
                Log.d(LOG_TAG, urls[0]);
                return null;
            }

            List<BookInfo> result = QueryUtils.fetchBookInfoData(urls[0]);
            return result;
        }

        /**
         * This method runs on the main UI thread after the background work has been
         * completed. This method receives as input, the return value from the doInBackground()
         * method. First we clear out the adapter, to get rid of BookInfo data from a previous
         * query to Googlr APIs. Then we update the adapter with the new list of books,
         * which will trigger the ListView to re-populate its list items.
         */
        @Override
        protected void onPostExecute(List<BookInfo> data) {
            mAdapter.clear();

            // If there is a valid list of {@link BookInfo}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if(data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(BookActivity.this);
                builder.setMessage("No book found!" + "\n" + "Please change Search text.")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                Intent iHome = new Intent(BookActivity.this, HomeActivity.class);
                                startActivity(iHome);
                            }
                        }) ;

                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }
}
