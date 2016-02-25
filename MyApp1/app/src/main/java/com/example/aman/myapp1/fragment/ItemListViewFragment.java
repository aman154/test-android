package com.example.aman.myapp1.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ListView;

import com.example.aman.myapp1.R;
import com.example.aman.myapp1.adapter.ItemListAdapter;
import com.example.aman.myapp1.app.AppUtil;
import com.example.aman.myapp1.model.ItemDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;


public class ItemListViewFragment extends Fragment implements AbsListView.OnScrollListener {


    private static final String jsonFileName = "items_details.json";
    Context context;
    private ListView fragmentList;
    GridView fragmentGird;
    private View rootView;
    private ArrayList<ItemDetails> details;
    int viewType = 0;
    private ItemListAdapter adapter;
    private int listPosition = 0;
    private int listPos = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_item_list_view, container, false);

        context = this.getActivity();

        try{
        viewType = getArguments().getInt("viewType");
            listPos = getArguments().getInt("listPosition");
        }catch (Exception e){e.printStackTrace();}

        Log.i("ItemListViewFragment", "viewType" + viewType);
        Log.i("ItemListViewFragment", "listPos" + listPos);

        getDetailJson();

        if(viewType ==0 || viewType == 1){

            if(fragmentGird != null)
                fragmentGird.setVisibility(View.GONE);

        fragmentList = (ListView) rootView.findViewById(R.id.fragment_list);
        fragmentList.setVisibility(View.VISIBLE);
        adapter = new ItemListAdapter(context,details,viewType);
        fragmentList.setAdapter(adapter);
            fragmentList.setSelection(listPos);
            fragmentList.setOnScrollListener(this);



        } else if(viewType == 2){

            if (fragmentList != null)
                fragmentList.setVisibility(View.GONE);
        fragmentGird = (GridView) rootView.findViewById(R.id.fragment_gridview);
        fragmentGird.setVisibility(View.VISIBLE);
            fragmentGird.setNumColumns(3);
        adapter = new ItemListAdapter(context,details,viewType);
        fragmentGird.setAdapter(adapter);
            fragmentGird.setSelection(listPos);
            fragmentGird.setOnScrollListener(this);

       }

        return rootView;
    }


    public void getDetailJson(){

        details = new ArrayList<>();

        try {

            JSONObject jsonObject = new JSONObject(loadJSONFromAsset());
            Log.i("ItemListViewFragment", "jsonObject" + jsonObject.toString());

            JSONArray array = jsonObject.getJSONArray("details");
            Log.i("ItemListViewFragment", "array" + array.toString());

            for (int i=0;i<array.length();i++){

                ItemDetails detail = new ItemDetails();

                JSONObject iObject = array.getJSONObject(i);

                String iPic  = iObject.getString("ipic");

                String iDetail = iObject.getString("idetail");

                int mrpPrice  = iObject.getInt("mrprice");

                int dlPrice = iObject.getInt("dlprice");

                double rating = iObject.getDouble("rating");

                detail.setiPic(iPic);
                detail.setiDetail(iDetail);
                detail.setMrPrice(mrpPrice);
                detail.setDlPrice(dlPrice);
                detail.setRating(rating);

                details.add(detail);
            }


        }catch(JSONException e){
            e.printStackTrace();
        }

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = this.getActivity().getAssets().open("items_details.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");

            Log.i("loadJSONFromAsset","jsonString-"+json);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        Log.i("ItemListViewFragment", "view" + view.toString());
        Log.i("ItemListViewFragment", "scrollState" + scrollState);
        Log.i("ItemListViewFragment", "view-LastVisiblePosition" + view.getLastVisiblePosition());
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Log.i("ItemListViewFragment", "firstVisibleItem" + firstVisibleItem);
        Log.i("ItemListViewFragment", "totalItemCount" + totalItemCount);
        AppUtil.setListPosition(firstVisibleItem);
        Log.i("ItemListViewFragment", "firstVisibleItem-getListPosition" + getListPosition());
    }

    public int getListPosition() {
        return listPosition;
    }

    public void setListPosition(int listPosition) {
        this.listPosition = listPosition;
    }
}
