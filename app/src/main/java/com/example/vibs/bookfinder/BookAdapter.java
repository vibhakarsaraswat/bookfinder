package com.example.vibs.bookfinder;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class BookAdapter extends ArrayAdapter<BookInfo> {
    /**
     * Constructs a new {@link booksAdapter}.
     *
     * @param context of the app
     * @param booksFoundInSearch is the list of books, which is the data source of the adapter
     */
    public BookAdapter(Context context, List<BookInfo> booksFoundInSearch) {
        super(context, 0, booksFoundInSearch);
    }

    /**
     * Below created 'getView' returns a list item view that displays information about
     * a Book at the given position in the list of booksFoundInSearch.
     */
    public View getView(int position, View converView, ViewGroup parent) {
        // Below we Check if there is an existing list item view (called convertView)
        // that we can reuse, otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = converView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);
        }

        // Find the Book at the given position in the list of booksFoundInSearch
        BookInfo currentBook = getItem(position);

        // Find the TextView with View Id averageRating
        TextView averageRatingView = (TextView) listItemView.findViewById(R.id.averageRating);
        String formattedAverageRating;
        if (currentBook.getmAverageRating() == 0.0) {
            averageRatingView.setText("n/a");
        } else {
            // Format the AverageRating to show 1 decimal place (in case we ever get more than 2 decimal rating)
            formattedAverageRating = formatAvgRating(currentBook.getmAverageRating());
            // Display the Average Rating of the current booksFoundInSearch in that TextView
            averageRatingView.setText(formattedAverageRating);
        }

        // Set the proper background color on the Average Rating circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable ratingCircle = (GradientDrawable) averageRatingView.getBackground();
        // Get the appropriate background color based on the current Book average rating.
        int ratingColor = getRatingColor(currentBook.getmAverageRating());
        // Set the color on the Average Rating circle
        ratingCircle.setColor(ratingColor);

        // Find the TextView with View Id title
        TextView titleView = (TextView) listItemView.findViewById(R.id.title);
        // Display the author of the current booksFoundInSearch in that TextView
        titleView.setText(currentBook.getmTitle());

        // Find the TextView with View Id author
        TextView authorView = (TextView) listItemView.findViewById(R.id.author);
        // Display the author of the current booksFoundInSearch in that TextView
        authorView.setText(currentBook.getmAuthor());

        // Find the TextView with View Id language
        TextView languageView = (TextView) listItemView.findViewById(R.id.language);
        // Display the author of the current booksFoundInSearch in that TextView
        languageView.setText(currentBook.getmLanguage());

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
