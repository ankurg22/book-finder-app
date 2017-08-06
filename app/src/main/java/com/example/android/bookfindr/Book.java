package com.example.android.bookfindr;

import java.io.Serializable;

/**
 * Created by Ankur Gupta on 4/8/17.
 * guptaankur.gupta0@gmail.com
 */

public class Book implements Serializable {
    private String mTitle;
    private String mAuthor;
    private String mPublisher;
    private String mPublishedDate;
    private String mDescription;
    private int mPageCount;
    private String mPrintType;
    private String mCategory;
    private double mRating;
    private String mThumbnail;
    private double mPrice;
    private String mCurrency;
    private boolean isEbook;
    private boolean isEpub;
    private boolean isPdf;

    public Book(String title, String author, String publisher, String publishedDate, String description, int pageCount, String printType, String category, double rating, String thumbnail, double price, String currency, boolean isEbook, boolean isEpub, boolean isPdf) {
        mTitle = title;
        mAuthor = author;
        mPublisher = publisher;
        mPublishedDate = publishedDate;
        mDescription = description;
        mPageCount = pageCount;
        mPrintType = printType;
        mCategory = category;
        mRating = rating;
        mThumbnail = thumbnail;
        mPrice = price;
        mCurrency = currency;
        this.isEbook = isEbook;
        this.isEpub = isEpub;
        this.isPdf = isPdf;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getPublisher() {
        return mPublisher;
    }

    public void setPublisher(String publisher) {
        mPublisher = publisher;
    }

    public String getPublishedDate() {
        return mPublishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        mPublishedDate = publishedDate;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public int getPageCount() {
        return mPageCount;
    }

    public void setPageCount(int pageCount) {
        mPageCount = pageCount;
    }

    public String getPrintType() {
        return mPrintType;
    }

    public void setPrintType(String printType) {
        mPrintType = printType;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public double getRating() {
        return mRating;
    }

    public void setRating(double rating) {
        mRating = rating;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(String thumbnail) {
        mThumbnail = thumbnail;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        mPrice = price;
    }

    public String getCurrency() {
        return mCurrency;
    }

    public void setCurrency(String currency) {
        mCurrency = currency;
    }

    public boolean isEbook() {
        return isEbook;
    }

    public void setEbook(boolean ebook) {
        isEbook = ebook;
    }

    public boolean isEpub() {
        return isEpub;
    }

    public void setEpub(boolean epub) {
        isEpub = epub;
    }

    public boolean isPdf() {
        return isPdf;
    }

    public void setPdf(boolean pdf) {
        isPdf = pdf;
    }
}
