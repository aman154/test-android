package com.example.aman.myapp1.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aman.myapp1.R;

public class Movies_Detail extends ActionBarActivity {

    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies__detail);

        imageView = (ImageView) findViewById(R.id.movie_image);
        textView = (TextView) findViewById(R.id.movie_name);

    }

}
