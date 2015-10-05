package com.example.aman.zwan.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aman.zwan.R;
import com.example.aman.zwan.activities.CustomerProfile;
import com.example.aman.zwan.adapter.MExpandableListAdapter;
import com.example.aman.zwan.models.DrawerElement;
import com.example.aman.zwan.models.DrawerSubElement;
import com.example.aman.zwan.util.AppConstants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;


public class DrawerFragment extends Fragment {

    private static final String TAG = "DrawerFragment";
    public
    Context context;
    View rootView;
    ExpandableListView drawerExpdListView;
    MExpandableListAdapter drawerAdapter;
    ArrayList<DrawerElement> arrayElements;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_drawer, container, false);

        context = this.getActivity();

        loadDrawerElements(context);

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        ViewGroup custHeader = (ViewGroup) layoutInflater.inflate(R.layout.drawer_cust_header_view, drawerExpdListView,false);


        drawerExpdListView = (ExpandableListView) rootView.findViewById(R.id.drawer_expd_list);
        drawerExpdListView.addHeaderView(custHeader, null, false);


        drawerAdapter = new MExpandableListAdapter(context,arrayElements,drawerExpdListView);
        drawerExpdListView.setAdapter(drawerAdapter);


        drawerExpdListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {

                Log.i(TAG, "group_position" + i + "and" + l);
                Toast.makeText(context, "group_position-" + i, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        drawerExpdListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                Log.i(TAG, "group_position" + i + "child_position-" + i1 + "and" + l);
                Toast.makeText(context, "child_position-" + i1, Toast.LENGTH_SHORT).show();
                return true;
            }
        });


        ImageView cust_edit_profile = (ImageView) custHeader.findViewById(R.id.cust_edit_profile_tv);

        cust_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CustomerProfile.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void loadDrawerElements(Context context){
        DrawerElement element;
        DrawerSubElement subElement;
        ArrayList<DrawerSubElement> drawerSubElementArrayList;
        arrayElements = new ArrayList<>();

        try{
        JSONObject object = new JSONObject(loadJSONFromAsset(context));

            JSONArray jsonArray = object.getJSONArray(AppConstants.HOME_DRAWER_KEY);

            for (int i=0; i<jsonArray.length(); i++){
               element = new DrawerElement();
                JSONObject elementsObject = jsonArray.getJSONObject(i);
                element.setTittle(elementsObject.getString(AppConstants.HOME_DRAWER_TITTLE_KEY));
                element.setIsBigBackground(elementsObject.getString(AppConstants.HOME_DRAWER_BACKGROUND_CHECK_KEY));
                String hasSubElements = elementsObject.getString(AppConstants.HOME_DRAWER_HAS_SUBELEMENTS_KEY);
                element.setIsSubElement(hasSubElements);
                if(hasSubElements.equals(AppConstants.YES_KEY)){

                    JSONArray subElementArray = elementsObject.getJSONArray(AppConstants.HOME_DRAWER_SUBELEMENTS_KEY);
                    drawerSubElementArrayList = new ArrayList<>();

                    for (int j= 0; j<subElementArray.length(); j++) {
                        subElement = new DrawerSubElement();
                        JSONObject subObject  = subElementArray.getJSONObject(j);
                        subElement.setTittle(subObject.getString(AppConstants.HOME_DRAWER_SUBELEMENTS_TITTLE_KEY));
                        subElement.setSomeTag(subObject.getString(AppConstants.HOME_DRAWER_SUBELEMENTS_TARGET_KEY));
                        drawerSubElementArrayList.add(subElement);
                    }

                    element.setSubElements(drawerSubElementArrayList);
                }
                arrayElements.add(element);

            }

        }catch(Exception e){
            e.printStackTrace();
        }


    }

    public static String loadJSONFromAsset(Context context) {
        String json;
        try {
            InputStream is = context.getAssets().open("hd.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (Exception ex) {
            Log.e(TAG, " Exception loadJSONFromAsset - " + ex, ex);
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
