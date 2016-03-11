package com.example.aman.myapp1.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.aman.myapp1.R;
import com.example.aman.myapp1.adapter.PlaceSearchListAdapter;
import com.example.aman.myapp1.model.PlaceResult;
import com.example.aman.myapp1.model.PlaceRoot;
import com.example.aman.myapp1.util.PlacesService;

import java.util.ArrayList;

public class PlaceSearchListActivity extends ActionBarActivity implements AbsListView.OnScrollListener {

    private static final String TAG = "PlaceSearchList";
    private static final String placeTypes = "restaurant|atm|cafe|hospital|hair_care|police";
    private static final double tempLat = 12.8868343, tempLng = 77.5960655;
    private ListView list;
    private ArrayList<PlaceResult> placeResults;
    private PlaceRoot placeRoot = new PlaceRoot();
    private PlaceSearchListAdapter adapter;
    private View footerView;
    private ProgressBar progressBar;
    private String pageToken;
    private String status;
    private int currentVisibleItemCount, currentScrollState, currentFirstVisibleItem, total_list_items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_search_list);

        list = (ListView) findViewById(R.id.place_search_list);
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        footerView = ((LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.base_list_item_loading_footer, null, false);

        adapter = new PlaceSearchListAdapter(getApplicationContext(), getPlaceResults());
        list.setAdapter(adapter);
        list.setOnScrollListener(this);

        if(savedInstanceState != null){
            placeResults = savedInstanceState.getParcelableArrayList("list_items");
            adapter.setPlaceResults(placeResults);
        }else {
            new FindPlacesAsync(0).execute();
        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PlaceResult result = placeResults.get(position);

                Intent intent = new Intent(PlaceSearchListActivity.this,SearchResultDetail.class);
                intent.putExtra("result",result);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("list_items",placeResults);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_place_search_list, menu);
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

    private boolean is_ready_to_paginate() {
        return ((adapter != null) && (adapter.getFeed_state() == PlaceSearchListAdapter.FEED_STATE_IDLE));
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.currentScrollState = scrollState;
        int last_item_index = currentFirstVisibleItem + currentVisibleItemCount;
        if ((last_item_index >= total_list_items) && is_ready_to_paginate()) {
            Log.e(TAG, "handle_list_bottom - ");
            if (pageToken != null) {
                new FindPlacesAsync(1).execute();
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

    private boolean addPlaceResult(ArrayList<PlaceResult> oldPlaceResult, ArrayList<PlaceResult> newPlaceResult) {
        if (newPlaceResult != null) {
            try {
                oldPlaceResult.addAll(newPlaceResult);
            } catch (Exception e) {
                Log.e(TAG, "Exception - " + e.toString(), e);
            }

            return true;
        }
        return false;
    }

    private class FindPlacesAsync extends AsyncTask<Void, Void, Boolean> {
        private int taskType = 0;
        private PlacesService placesService;

        public FindPlacesAsync(int taskType) {
            this.taskType = taskType;
            placesService = new PlacesService();
            if (taskType == 0) {
                progressBar.setVisibility(View.VISIBLE);
            } else if (taskType == 1) {
                list.addFooterView(footerView);
            }
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            pageToken = placeRoot.getPageToken();
            if (taskType == 1) {
                adapter.setFeed_state(PlaceSearchListAdapter.FEED_STATE_PAGINATION);
            }
            if (tempLat != 0 && tempLng != 0) {
                placeRoot = placesService.getPlaces(tempLat, tempLng, placeTypes, pageToken);
                return true;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean b) {
            pageToken = placeRoot.getPageToken();
            status = placeRoot.getStatus();
            if (status != null && status.equalsIgnoreCase("ok")) {
                if (taskType == 0) {
                    progressBar.setVisibility(View.GONE);
                    setPlaceResults(placeRoot.getPlaceResult());
                    if (adapter == null) {
                        adapter = new PlaceSearchListAdapter(getApplicationContext(), getPlaceResults());
                    } else {
                        adapter.setPlaceResults(getPlaceResults());
                        list.setAdapter(adapter);
                    }
                } else if (taskType == 1 && adapter.getFeed_state() == PlaceSearchListAdapter.FEED_STATE_PAGINATION) {
                    list.removeFooterView(footerView);
                    boolean isNewResultsAdded = addPlaceResult(placeResults, placeRoot.getPlaceResult());
                    if (adapter != null && isNewResultsAdded) {
                        adapter.setFeed_state(PlaceSearchListAdapter.FEED_STATE_IDLE);
                        adapter.setPlaceResults(getPlaceResults());
                    }
                }
            } else {
                Toast.makeText(PlaceSearchListActivity.this, status, Toast.LENGTH_LONG).show();
            }
        }
    }
}
