package com.example.vibs.bookfinder;

/**
 * An {@link BookInfo} object contains information related to a single Book.
 */
public class BookInfo {

    /** AvergareRating of the Book */
    private final double mAverageRating;

    /** Title of the Book */
    private final String mTitle;

    /** Author of the Book */
    private final String mAuthor;

    /** Language of the Book */
    private final String mLanguage;

    /** Link for further Info about the Book */
    private final String mInfoLink;

    /**
     * Constructs a new {@link BookInfo} object.
     *
     * @param averageRating is the AverageRating of the Book
     * @param title is the Title of the Boook
     * @param author is the Author of the Book
     * @param language is the Language of the Book
     * @param infoLink is Link for further Info about the Book
     */

    public BookInfo(double averageRating, String title, String author, String language, String infoLink) {
        mAverageRating = averageRating;
        mTitle = title;
        mAuthor = author;
        mLanguage = language;
        mInfoLink = infoLink;
    }

    /** Returns the AvergareRating of the Book*/
    public double getmAverageRating() { return mAverageRating; }

    /** Returns the Title of the Book*/
    public String getmTitle() { return mTitle; }

    /** Returns the Author of the Book*/
    public String getmAuthor() { return mAuthor; }

    /** Returns the Language of the Book*/
    public String getmLanguage() { return mLanguage; }

    /** Returns the InfoLink of the Book*/
    public String getmInfoLink() { return mInfoLink; }

}
