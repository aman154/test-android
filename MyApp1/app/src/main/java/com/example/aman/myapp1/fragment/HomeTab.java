package com.example.aman.myapp1.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.example.aman.myapp1.R;
import com.example.aman.myapp1.adapter.HomeTabListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

/**
 * Created by aman on 3/7/15.
 */

public class HomeTab extends Fragment {

    private Context mActivity = this.getActivity();
    private ListView home_tab_list;
    private HomeTabListAdapter adapter;
    private View rootView;
    private JSONObject home_page;

    private static final String HOME_PAGE_KEY = "hp";

    JSONArray hp_jsonArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mActivity = this.getActivity();
        if (rootView != null) {

            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
            return rootView;

        } else
            rootView = inflater.inflate(R.layout.home_tab_list_view, container, false);


        // Log.e("HomeTab", "getJson() " +getJson());
        if (home_page == null) {
            try {
                Log.e("HomeTab", "loadJSONFromAsset() " + loadJSONFromAsset(this.getActivity()));
                home_page = new JSONObject(loadJSONFromAsset(this.getActivity()));
                Log.i("HomeTab", "jsonObject" + home_page.toString());
            } catch (Exception e) {
                // Log.e("HomeTab","Exception json -"+ e,e);
                e.printStackTrace();
            }

        }
        try {
            hp_jsonArray = home_page.getJSONArray(HOME_PAGE_KEY);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (hp_jsonArray.length() != 0) {
            home_tab_list = (ListView) rootView.findViewById(R.id.home_tab_list);

            adapter = new HomeTabListAdapter(mActivity, hp_jsonArray);

            home_tab_list.setAdapter(adapter);
        }

        return rootView;
    }


    public static String loadJSONFromAsset(Context context) {
        String json;
        try {

            InputStream is = context.getAssets().open("hp.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");

        } catch (Exception ex) {

            ex.printStackTrace();
            return null;
        }
        return json;

    }

}
