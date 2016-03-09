package com.example.aman.myapp1.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.aman.myapp1.R;
import com.example.aman.myapp1.adapter.PlaceSearchListAdapter;
import com.example.aman.myapp1.model.PlaceResult;
import com.example.aman.myapp1.model.PlaceRoot;
import com.example.aman.myapp1.util.PlacesService;

import java.util.ArrayList;
import java.util.List;

public class PlaceSearchListActivity extends ActionBarActivity implements AbsListView.OnScrollListener {

    private final String TAG = "PlaceSearchList";
    private ImageView map_view;
    private LinearLayout search_ll;
    private final String placeTypes = "restaurant|atm|cafe|hospital|hair_care|police";

    private ListView list;
    private ArrayList<PlaceResult> placeResults;
    PlaceRoot placeRoot = new PlaceRoot();
    private double lat, lng;
    PlaceSearchListAdapter adapter;
    private View footerView;
    private ProgressBar progressBar3;
    private String pageToken;
    private String status;

    int currentVisibleItemCount, currentScrollState, currentFirstVisibleItem, total_list_items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_search_list);

        list = (ListView) findViewById(R.id.place_search_list);

        progressBar3 = (ProgressBar) findViewById(R.id.progressBar3);


        footerView = ((LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.base_list_item_loading_footer, null, false);

        lat = 12.8868343;
        lng = 77.5960655;

        adapter = new PlaceSearchListAdapter(getApplicationContext(), getPlaceResults());


        new FindPlaces().execute();
        list.setOnScrollListener(this);

    }


        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_place_search_list, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
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

        private boolean is_ready_to_paginate () {
            return ((adapter != null) && (adapter.getFeed_state() == PlaceSearchListAdapter.FEED_STATE_IDLE));
        }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        this.currentScrollState = scrollState;
        int last_item_index = currentFirstVisibleItem + currentVisibleItemCount;
        if((last_item_index >= total_list_items) && is_ready_to_paginate()){
            Log.e(TAG, "handle_list_bottom - ");
            if(pageToken != null) {
                new FindPlaces1().execute();
            }

        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        this.currentFirstVisibleItem = firstVisibleItem;
        this.currentVisibleItemCount = visibleItemCount;
        this.total_list_items = totalItemCount;
    }

    public ArrayList<PlaceResult> getPlaceResults() {
        return placeResults;
    }

    public void setPlaceResults(ArrayList<PlaceResult> placeResults) {
        this.placeResults = placeResults;
    }


    private class FindPlaces extends AsyncTask {

        public FindPlaces(){
            progressBar3.setVisibility(View.VISIBLE);
        }
            @Override
            protected Object doInBackground(Object[] params) {
                PlacesService placesService = new PlacesService();
                pageToken = placeRoot.getPageToken();

                if (lat != 0 && lng != 0) {
                    placeRoot = placesService.getPlaces(lat, lng, placeTypes, pageToken);
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Object o) {
                progressBar3.setVisibility(View.GONE);
                if (placeRoot != null){
                    setPlaceResults(placeRoot.getPlaceResult());
                    pageToken = placeRoot.getPageToken();
                    status = placeRoot.getStatus();
            }
                if(status != null && status.equalsIgnoreCase("ok")) {
                    if (adapter == null) {
                        adapter = new PlaceSearchListAdapter(getApplicationContext(), getPlaceResults());
                        list.setAdapter(adapter);
                    } else {
                        adapter.setPlaceResults(getPlaceResults());
                        list.setAdapter(adapter);
                    }
                }else {
                    Toast.makeText(PlaceSearchListActivity.this,status,Toast.LENGTH_LONG).show();
                }

            }
        }

    private class FindPlaces1 extends AsyncTask {

        public FindPlaces1(){
            list.addFooterView(footerView);
        }
        @Override
        protected Object doInBackground(Object[] params) {
            adapter.setFeed_state(PlaceSearchListAdapter.FEED_STATE_PAGINATION);
            PlacesService placesService = new PlacesService();
            pageToken = placeRoot.getPageToken();

            if (lat != 0 && lng != 0) {
                placeRoot = placesService.getPlaces(lat, lng, placeTypes, pageToken);
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            if(adapter.getFeed_state() == PlaceSearchListAdapter.FEED_STATE_PAGINATION){
                list.removeFooterView(footerView);
            }
                pageToken = placeRoot.getPageToken();
                status = placeRoot.getStatus();
                Log.i(TAG,"page token - "+pageToken);
            if(status != null && status.equalsIgnoreCase("ok")) {
                boolean isaddResult = addPlaceResult(placeResults, placeRoot.getPlaceResult());
                if (adapter != null && isaddResult) {
                    adapter.setFeed_state(PlaceSearchListAdapter.FEED_STATE_IDLE);
                    adapter.setPlaceResults(getPlaceResults());
                }
            }else{
                Toast.makeText(PlaceSearchListActivity.this,status,Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean addPlaceResult(ArrayList<PlaceResult> oldPlaceResult, ArrayList<PlaceResult> newPlaceResult){
        if(newPlaceResult != null){
            try {
                oldPlaceResult.addAll(newPlaceResult);
            } catch (Exception e) {
                Log.e(TAG, "Exception - " + e.toString(), e);
            }

            return true;
        }
        return false;

    }
    }
