package com.example.android.bookfindr;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.android.bookfindr.databinding.ActivityBookListBinding;

import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {
    private static String REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private ActivityBookListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_list);

        Intent intent = getIntent();
        String query = intent.getStringExtra("QUERY");
        REQUEST_URL += query;
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(0, null, this);
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        return new BookListLoader(this, REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        BookListAdapter adapter = new BookListAdapter((ArrayList<Book>) books);
        binding.listBooks.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {

    }

    /**
     * Since Data Binding library was not able to load drawables so I Google'd and found solution
     * https://stackoverflow.com/a/35809319/6494628
     * This code is required only once in whole App.
     */
    @BindingAdapter("bind:image_url")
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).crossFade(1000).into(imageView);
    }
}
