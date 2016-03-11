package com.example.aman.myapp1.activity;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aman.myapp1.R;
import com.example.aman.myapp1.fragment.SearchFragment;
import com.example.aman.myapp1.model.PlaceResult;
import com.example.aman.myapp1.util.PlacesService;
import com.example.aman.myapp1.util.UserLocation;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchActivity extends FragmentActivity implements OnMapReadyCallback,View.OnClickListener {

    private final String TAG = "SearchActivity";
    private Double lat;
    private Double lng;
    private LinearLayout search_ll;
    private ImageView list_view;

    private final String placeTypes = "restaurant|atm|cafe|hospital|hair_care|police";
    private String placeType;

    private ArrayList<PlaceResult> placeResults;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

/*
        Bundle bundle = getIntent().getExtras();

        if(bundle != null){

            if(bundle.getString("pageType").equals("SearchFragment")){ placeType = bundle.getString("searchText");}
         }*/

        list_view = (ImageView) findViewById(R.id.list_view);
        search_ll = (LinearLayout) findViewById(R.id.search_ll);

        search_ll.setOnClickListener(this);
        list_view.setOnClickListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.search_map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap map) {

        map.setMyLocationEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setAllGesturesEnabled(true);
        UserLocation location = new UserLocation(this);

        lat = location.getLatitude();
        lng = location.getLongitude();

       /* lat = 12.8868343;
        lng = 77.5960655;*/


        if(lat != 0 && lng != 0) {
            LatLng latLng = new LatLng(lat, lng);

            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLng, 15);
            map.animateCamera(yourLocation);
            MarkerOptions markerUser = new MarkerOptions().position(latLng).title("you are here");
            markerUser.icon(BitmapDescriptorFactory.fromResource(R.drawable.my_pin));
            map.addMarker(markerUser);


            Bundle bundle = getIntent().getExtras();

            if (bundle != null) {

                if (bundle.getString("pageType").equals("SearchFragment")) {

                    placeType = bundle.getString("searchText");
                    new FindPlaces(map).execute();
                }
                else if(bundle.getString("pageType").equals("SearchListActivity")){

                    placeResults = (ArrayList<PlaceResult>) bundle.getSerializable("resultArray");

                    showMapForResults(map);
                }

            } else {
                new FindPlaces(map).execute();
            }

       }
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                if(marker.getTitle().equalsIgnoreCase("You are here")){
                    marker.hideInfoWindow();
                    marker.getId();

                }
                else {
                        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SearchActivity.this);
                        LayoutInflater inflater = SearchActivity.this.getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.map_info_detail, null);
                        dialogBuilder.setView(dialogView);
                        final AlertDialog alertDialog = dialogBuilder.create();

                    RelativeLayout alert_dialog_rl = (RelativeLayout) dialogView.findViewById(R.id.alert_dialog_rl);
                    TextView result_tittle_tv=(TextView)dialogView.findViewById(R.id.result_tittle_tv);
                    TextView result_detail_tv=(TextView)dialogView.findViewById(R.id.result_detail_tv);
                    ImageView map_detail_icon=(ImageView)dialogView.findViewById(R.id.map_detail_icon);
                    ImageView dialog_dismiss_tv=(ImageView)dialogView.findViewById(R.id.dialog_dismiss_tv);

                   final PlaceResult results = placeResults.get(Integer.parseInt(marker.getSnippet()));

                    result_tittle_tv.setText(results.getName());
                    result_detail_tv.setText("Address-"+results.getAddress());

                    String urlS = results.getIcon();

                    if (urlS != null && urlS.length() > 0) {
                        Picasso.with(SearchActivity.this)
                                .load(urlS)
                                .placeholder(R.drawable.title_about_default)
                                .error(R.drawable.title_about_alt)
                                .into(map_detail_icon);

                    }

                    dialog_dismiss_tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });

                    alert_dialog_rl.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(SearchActivity.this,SearchResultDetail.class);
                            //intent.putExtra("result",results);
                            startActivity(intent);
                        }
                    });

                    alertDialog.show();
                    return true;
                }
                return true;
            }
        });
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
            getSupportFragmentManager().beginTransaction().add(R.id.search_activity_container, searchFragment).commit();

        }else

        if(id == list_view.getId()){

            Intent intent = new Intent(getApplicationContext(),SearchListActivity.class);
            intent.putExtra("resultArray",placeResults);
            /*intent.putExtra("lat",lat);
            intent.putExtra("lng",lng);






            if(placeType != null){intent.putExtra("searchText",placeType);}else{
            intent.putExtra("searchText",placeTypes);}*/
            startActivity(intent);

        }

    }
    public void handle_search_back_button() {
        Log.e(TAG,TAG + " searchFragment  - "  + searchFragment);
        if(searchFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(searchFragment).commit();
        }
    }

  private class FindPlaces extends AsyncTask {

      GoogleMap map;

      public  FindPlaces(GoogleMap map){
          this.map = map;
      }
      @Override
      protected Objects doInBackground(Object[] params) {

          PlacesService placesService = new PlacesService();
          Log.i(TAG,"lat-"+lat+","+"lng"+lng+","+placeTypes);

          if(placeType != null){

          placeResults = placesService.findPlaces(lat,lng,placeType);

          }else {

              placeResults = placesService.findPlaces(lat,lng,placeTypes); }

          return null;
      }

      @Override
      protected void onPreExecute() {
          super.onPreExecute();
      }

      @Override
      protected void onPostExecute(Object o) {

          showMapForResults(map);
      }
  }

    private void showMapForResults(GoogleMap map) {
        if(placeResults != null) {
            Log.i(TAG, placeResults.toString());

            for (int i = 0; i < placeResults.size(); i++)
            {
                PlaceResult results = placeResults.get(i);
                Log.i(TAG,"result =="+results);
                if (results != null) {

                    final MarkerOptions markerOption = new MarkerOptions().position(new LatLng(results.getLat(), results.getLng())).title(results.getName()).snippet(String.valueOf(i));

                    Picasso.with(getBaseContext()).load(results.getIcon()).into(new Target() {

                        @Override
                        public void onPrepareLoad(Drawable arg0) {
                        }

                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom arg1) {

                            markerOption.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
                        }

                        @Override
                        public void onBitmapFailed(Drawable arg0) {
                        }
                    });

                    map.addMarker(markerOption);

                   /* Marker marker = map.addMarker(markerOption);

                    marker.showInfoWindow();*/

                }
            }
        }
    }

   /* @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
    }*/
}
