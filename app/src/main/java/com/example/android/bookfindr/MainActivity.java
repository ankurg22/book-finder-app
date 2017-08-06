package com.example.android.bookfindr;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.android.bookfindr.databinding.ActivityMainBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        //Special UI for lollipop + devices
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
        }

        //Click listener on search button
        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get query from EditText and format it
                String query = binding.editSearch.getText().toString().toLowerCase().trim();

                //Only list activity if query is valid else show proper message.
                if (isQueryValid(query)) {
                    Intent intent = new Intent(getApplicationContext(), BookListActivity.class);
                    intent.putExtra("QUERY", query);
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeCustomAnimation(
                            getApplicationContext(), android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    startActivity(intent, optionsCompat.toBundle());
                } else {
                    binding.editSearch.setError(getString(R.string.query_error));
                }

            }
        });

    }

    /**
     * Took help from this answer to detect special characters
     * https://stackoverflow.com/a/1795436/6494628
     *
     * @param query
     * @return
     */
    private boolean isQueryValid(String query) {
        if (query.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile("[a-zA-z.]+([ '-][a-zA-Z.]+)*");
        Matcher matcher = pattern.matcher(query);
        return matcher.matches();
    }
}
