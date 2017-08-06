package com.example.android.bookfindr;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.android.bookfindr.databinding.ActivityBookListBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ankur Gupta on 4/8/17.
 * guptaankur.gupta0@gmail.com
 */

public class BookListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {
    private String REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private ActivityBookListBinding binding;
    private BookListAdapter adapter;

    /**
     * Since Data Binding library was not able to load images so I Google'd and found solution
     * https://stackoverflow.com/a/35809319/6494628
     * This code is required only once in whole App.
     */
    @BindingAdapter("bind:image_url")
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).crossFade(1000).into(imageView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_list);

        //Special UI changes for Android 5+ devices
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
        }

        //When search fab is clicked finish this activity because MainActivity is already in backstack
        binding.fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Make and set empty adapter on ListView.
        adapter = new BookListAdapter(this, new ArrayList<Book>());
        binding.listBooks.setAdapter(adapter);
        binding.listBooks.setEmptyView(binding.textEmpty);

        //Search only if network is available else show proper message
        if (isNetworkWorking()) {
            Intent intent = getIntent();
            String query = intent.getStringExtra("QUERY");
            REQUEST_URL += query;
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.restartLoader(1, null, this);
        } else {
            binding.progress.setVisibility(View.GONE);
            binding.textEmpty.setText(getString(R.string.no_internet));
        }
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        return new BookListLoader(this, REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        //Remove ProgressBar when list is ready
        binding.progress.setVisibility(View.GONE);

        //Set empty text
        binding.textEmpty.setText(getString(R.string.empty_books));

        //Clear adapter first
        adapter.clear();

        //Add list to adapter if it is not empty and null
        if (books != null && !books.isEmpty()) {
            adapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        adapter.clear();
    }

    /**
     * Method to check if network is working
     *
     * @return boolean value
     */
    private boolean isNetworkWorking() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }
}
