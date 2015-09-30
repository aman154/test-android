package com.example.aman.myapp1.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.aman.myapp1.R;
import com.example.aman.myapp1.adapter.SearchListAdapter;
import com.example.aman.myapp1.fragment.SearchFragment;
import com.example.aman.myapp1.model.PlaceResult;
import com.example.aman.myapp1.util.PlacesService;
import com.example.aman.myapp1.util.UserLocation;

import java.util.ArrayList;

public class SearchListActivity extends FragmentActivity implements View.OnClickListener {


    private final String TAG = "SearchListActivity";
    private ImageView map_view;
    private String placeType;
    private LinearLayout search_ll;

    private ListView list;
    private ArrayList<PlaceResult> placeResults;
    private double lat,lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
       /* placeType = bundle.getString("searchText");
        lat = bundle.getDouble("lat");
        lng = bundle.getDouble("lng");

        Log.i(TAG, "lat-" + lat + "," + "lng" + lng + "," + "placeType" + placeType);*/

        placeResults = (ArrayList<PlaceResult>) bundle.getSerializable("resultArray");

        }

        search_ll = (LinearLayout) findViewById(R.id.search_ll);
        search_ll.setOnClickListener(this);

        map_view = (ImageView) findViewById(R.id.map_view);
        map_view.setOnClickListener(this);

        list = (ListView) findViewById(R.id.list);

       // new FindPlaces().execute();

        if(placeResults != null){
            SearchListAdapter adapter = new SearchListAdapter(getApplicationContext(),placeResults);
            list.setAdapter(adapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    PlaceResult result = placeResults.get(position);

                    Intent intent = new Intent(SearchListActivity.this,SearchResultDetail.class);
                    intent.putExtra("result",result);
                    startActivity(intent);

                }
            });
        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_list, menu);
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
    SearchFragment searchFragment;
    @Override
    public void onClick(View v) {

        int id = v.getId();
        if(id == search_ll.getId()){

            searchFragment = new SearchFragment();
            Bundle bundle = new Bundle();
            bundle.putString("pageType",TAG);
            searchFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.search_list_container, searchFragment,"searchFragment").commit();

        }else

        if(id == map_view.getId()){

            Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
            intent.putExtra("resultArray",placeResults);
            intent.putExtra("pageType","SearchListActivity");
            startActivity(intent);
            finish();
        }

    }

    public void handle_search_back_button() {
        Log.e(TAG,TAG + " searchFragment  - "  + searchFragment);
        if(searchFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(searchFragment).commit();
        }
    }

    private class FindPlaces extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            PlacesService placesService = new PlacesService();

            if(lat != 0 && lng !=0 && placeType !=null){
            placeResults = placesService.findPlaces(lat,lng,placeType);}

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {

            if(placeResults != null){
            SearchListAdapter adapter = new SearchListAdapter(getApplicationContext(),placeResults);
            list.setAdapter(adapter);}
        }
    }
}
