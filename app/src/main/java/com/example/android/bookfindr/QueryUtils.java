package com.example.android.bookfindr;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ankurg22 on 4/8/17.
 */

public class QueryUtils {
    private static final String TAG = QueryUtils.class.getSimpleName();

    private static final String KEY_TOTAL_ITEMS = "totalItems";
    private static final String KEY_ITEMS = "items";
    private static final String KEY_VOLUME_INFO = "volumeInfo";
    private static final String KEY_TITLE = "title";
    private static final String KEY_AUTHORS = "authors";
    private static final String KEY_PUBLISHER = "publisher";
    private static final String KEY_PUBLISHED_DATE = "publishedDate";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_PAGE_COUNT = "pageCount";
    private static final String KEY_PRINT_TYPE = "printType";
    private static final String KEY_CATEGORIES = "categories";
    private static final String KEY_AVERAGE_RATING = "averageRating";
    private static final String KEY_IMAGE_LINKS = "imageLinks";
    private static final String KEY_THUMBNAIL = "thumbnail";
    private static final String KEY_SALE_INFO = "saleInfo";
    private static final String KEY_SALEABILITY = "saleability";
    private static final String KEY_IS_EBOOK = "isEbook";
    private static final String KEY_LIST_PRICE = "listPrice";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_CURRENCY_CODE = "currencyCode";
    private static final String KEY_ACCESS_INFO = "accessInfo";
    private static final String KEY_EPUB = "epub";
    private static final String KEY_PDF = "pdf";
    private static final String KEY_IS_AVAILABLE = "isAvailable";


    private QueryUtils() {

    }

    /**
     * To query Google Books API
     *
     * @param requestUrl A string url
     * @return List of data type Book
     */
    public static List<Book> fetchBookData(String requestUrl) {
        //Create URL object
        URL url = createUrl(requestUrl);

        //Perform HTTP request to the URL
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(TAG, "Problem while HTTP request", e);
        }

        List<Book> books = extractBooks(jsonResponse);

        return books;
    }

    /**
     * Method to convert String url to URL object
     *
     * @param stringUrl String
     * @return URL object
     */
    public static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Problem while building URL", e);
        }
        return url;
    }

    /**
     * Make an HTTP request
     *
     * @param url URL object to make HTTP request on
     * @return String JSON response
     * @throws IOException Closing inputStream can generate this exception
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "Problem retrieving JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Covert InputStream to String
     *
     * @param inputStream
     * @return String which contains whole JSON response from server
     * @throws IOException
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Book> extractBooks(String bookJSON) {
        if (TextUtils.isEmpty(bookJSON)) {
            return null;
        }

        List<Book> books = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(bookJSON);
            int numOfItems = baseJsonResponse.getInt(KEY_TOTAL_ITEMS);
            if (numOfItems <= 0) return null;
            JSONArray itemsArray = baseJsonResponse.optJSONArray(KEY_ITEMS);

            for (int i = 0; i < itemsArray.length(); i++) {
                String title = null;
                String author = null;
                String publisher = null;
                String publishedDate = null;
                String description = null;
                int pageCount = 0;
                String printType = null;
                String category = null;
                double rating = 0;
                String thumbnail = null;
                boolean isEbook = false;
                double amount = 0;
                String currency = null;
                boolean isPdf = false;
                boolean isEpub = false;

                JSONObject itemObject = itemsArray.getJSONObject(i);

                //Extract required objects from item
                JSONObject volumeInfo = itemObject.optJSONObject(KEY_VOLUME_INFO);
                JSONObject saleInfo = itemObject.optJSONObject(KEY_SALE_INFO);
                JSONObject accessInfo = itemObject.optJSONObject(KEY_ACCESS_INFO);

                //Extract details from volumeInfo
                if (volumeInfo != null) {
                    title = volumeInfo.optString(KEY_TITLE);
                    JSONArray authorJson = volumeInfo.optJSONArray(KEY_AUTHORS);
                    if (authorJson != null) author = authorJson.optString(0, "");
                    publisher = volumeInfo.optString(KEY_PUBLISHER);
                    publishedDate = volumeInfo.optString(KEY_PUBLISHED_DATE);
                    description = volumeInfo.optString(KEY_DESCRIPTION);
                    pageCount = volumeInfo.optInt(KEY_PAGE_COUNT);
                    printType = volumeInfo.optString(KEY_PRINT_TYPE);
                    JSONArray categoryJson = volumeInfo.optJSONArray(KEY_CATEGORIES);
                    if (categoryJson != null) category = categoryJson.optString(0, "");
                    rating = volumeInfo.optDouble(KEY_AVERAGE_RATING, 0);
                    JSONObject thumbnailJson = volumeInfo.optJSONObject(KEY_IMAGE_LINKS);
                    if (thumbnailJson != null) thumbnail = thumbnailJson.optString(KEY_THUMBNAIL);
                }

                //Extract Sale Info
                if (saleInfo != null) {
                    isEbook = saleInfo.optBoolean(KEY_IS_EBOOK);
                    //Certain objects are available only available if eBook is avilable. Hence the optJSONObject
                    JSONObject listPrice = saleInfo.optJSONObject(KEY_LIST_PRICE);
                    if (listPrice != null) {
                        amount = listPrice.optInt(KEY_AMOUNT, 0);
                        currency = listPrice.optString(KEY_CURRENCY_CODE, "");
                    }
                }

                //Extract Access Info
                if (accessInfo != null) {
                    JSONObject epubJson = accessInfo.optJSONObject(KEY_EPUB);
                    if (epubJson != null) isEpub = epubJson.optBoolean(KEY_IS_AVAILABLE);
                    JSONObject pdfJson = accessInfo.optJSONObject(KEY_PDF);
                    if (pdfJson != null) isPdf = pdfJson.optBoolean(KEY_IS_AVAILABLE);
                }

                Book book = new Book(
                        title, author, publisher, publishedDate,
                        description, pageCount, printType, category,
                        rating, thumbnail, amount, currency,
                        isEbook, isEpub, isPdf
                );
                books.add(book);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Problem parsing JSON", e);
        }
        return books;
    }
}
