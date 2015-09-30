package com.example.aman.myapp1.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.aman.myapp1.R;
import com.example.aman.myapp1.model.PlaceResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchResultDetail extends ActionBarActivity {

    PlaceResult result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_detail);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){

            result = (PlaceResult) bundle.getSerializable("result");

        }

        TextView result_tittle_tv=(TextView) findViewById(R.id.result_tittle_tv);
        TextView result_detail_tv=(TextView) findViewById(R.id.result_detail_tv);
        ImageView map_detail_icon=(ImageView) findViewById(R.id.map_detail_icon);
        RatingBar ratingBar=(RatingBar) findViewById(R.id.ratingBar);

        result_tittle_tv.setText(result.getName());
        result_detail_tv.setText("Address- "+result.getAddress());

        double rating = result.getRating();
        ratingBar.setRating((float) rating);


        String urlS = result.getIcon();
        String photoRef = result.getPhotoRef();

        String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=350&photoreference="+photoRef+"&sensor=true&key=AIzaSyDgpkdlHyCjPFUw-SV1BUCIJOgdAvnVUfI";

        if (url != null && url.length() > 0) {
            Picasso.with(SearchResultDetail.this)
                    .load(url)
                    .placeholder(R.drawable.title_about_default)
                    .error(R.drawable.title_about_alt)
                    .into(map_detail_icon);

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_result_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
