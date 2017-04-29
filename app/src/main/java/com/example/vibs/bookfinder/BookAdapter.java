package com.example.vibs.bookfinder;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class BookAdapter extends ArrayAdapter<BookInfo> {
    /** Tag for the log messages */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Constructs a new {@link booksAdapter}.
     *
     * @param context of the app
     * @param booksFoundInSearch is the list of books, which is the data source of the adapter
     */
    public BookAdapter(Context context, List<BookInfo> booksFoundInSearch) {
        super(context, 0, booksFoundInSearch);
    }


    /*
    Creating 'BookAdapterViewHolder' class to Hold the Views that are being used in 'getView' method below.
    This is to save multiple 'findViewById' calls made by these Views and instead to call this
    only once.
    */

    public class BookAdapterViewHolder {
        TextView averageRatingView;
        TextView titleView;
        TextView authorView;
        TextView languageView;

        /**
         * defining constructor i.e. BookAdapterViewHolder, to intialize the View declared above,
         * so that we don't have to call 'findViewById' again and again.
         * Note: In order to Initiaze these views, We need to an object of type View,
         * which needs to be provided as a parameter while defining the Constructor i.e. 'viewBookAdapter'
         */
        BookAdapterViewHolder(View viewBookAdapter) {
            averageRatingView = (TextView) viewBookAdapter.findViewById(R.id.averageRating);
            titleView = (TextView) viewBookAdapter.findViewById(R.id.title);
            authorView = (TextView) viewBookAdapter.findViewById(R.id.author);
            languageView = (TextView) viewBookAdapter.findViewById(R.id.language);
        }
    }

    /**
     * Below created 'getView' returns a list item view that displays information about
     * a Book at the given position in the list of booksFoundInSearch.
     */
    public View getView(int position, View converView, ViewGroup parent) {

        // Creating and intializing an instance of BookAdapterViewHolder constructor.
        BookAdapterViewHolder bavHolder = null;

        // Below we Check if there is an existing list item view (called convertView)
        // that we can reuse, otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = converView;

        /**
         * Value for 'listItemView' will be 'null' if getting created for the first time and therefore
         * it needs to be inflated by using Layout Inflator before using for further operations.
         */
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.book_list_item, parent, false);

            // ViewHolder i.e. bavHolder also needs to be intialized by passing in the 'View' param.
            bavHolder = new BookAdapterViewHolder(listItemView);

            /**
             * Storing the ViewHolder i.e. bavHolder, so that we don't have to recreate
             * the ViewHolder again while using an already Inflated View.
             */
            listItemView.setTag(bavHolder);
            Log.e(LOG_TAG, "Creating a New ListItemView Row");

        } else {
            // getting the value of ViewHolder i.e. bavHolder, if an Inflated View already exists.
            bavHolder = (BookAdapterViewHolder) listItemView.getTag();
            Log.e(LOG_TAG, "Recycling ListItemView Row");
        }

        // Find the Book at the given position in the list of booksFoundInSearch
        BookInfo currentBook = getItem(position);


        String formattedAverageRating;
        if (currentBook.getmAverageRating() == 0.0) {
            bavHolder.averageRatingView.setText("n/a");
        } else {
            // Format the AverageRating to show 1 decimal place (in case we ever get more than 2 decimal rating)
            formattedAverageRating = formatAvgRating(currentBook.getmAverageRating());
            // Display the Average Rating of the current booksFoundInSearch in that TextView
            bavHolder.averageRatingView.setText(formattedAverageRating);
        }

        // Set the proper background color on the Average Rating circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable ratingCircle = (GradientDrawable) bavHolder.averageRatingView.getBackground();
        // Get the appropriate background color based on the current Book average rating.
        int ratingColor = getRatingColor(currentBook.getmAverageRating());
        // Set the color on the Average Rating circle
        ratingCircle.setColor(ratingColor);

        // Display the author of the current booksFoundInSearch in that TextView
        bavHolder.titleView.setText(currentBook.getmTitle());

        // Display the author of the current booksFoundInSearch in that TextView
        bavHolder.authorView.setText(currentBook.getmAuthor());

        // Display the author of the current booksFoundInSearch in that TextView
        bavHolder.languageView.setText(currentBook.getmLanguage());

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }

    /**
     * Return the color for the rating circle based on the average rating of the Book.
     *
     * @param rating of the book
     */
    private int getRatingColor(double rating) {
        int ratingColorResourceId;
        int ratingFloor = (int) Math.floor(rating);
        switch (ratingFloor) {
            case 0:
                ratingColorResourceId = R.color.ratingNA;
                break;
            case 1:
                ratingColorResourceId = R.color.rating1;
                break;
            case 2:
                ratingColorResourceId = R.color.rating2;
                break;
            case 3:
                ratingColorResourceId = R.color.rating3;
                break;
            case 4:
                ratingColorResourceId = R.color.rating4;
                break;
            case 5:
                ratingColorResourceId = R.color.rating5;
                break;
            default:
                ratingColorResourceId = R.color.ratingDefault;
                break;
        }
        return ContextCompat.getColor(getContext(), ratingColorResourceId);
    }

    /**
     * Return the formatted AvargeRating string showing 1 decimal place (i.e. "3.2")
     * from a decimal Rating value.
     */
    private String formatAvgRating(double avgrating) {
        DecimalFormat ratingFormat = new DecimalFormat("0.0");
        return ratingFormat.format(avgrating);
    }

}
