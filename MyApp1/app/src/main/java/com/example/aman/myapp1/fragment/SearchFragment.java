package com.example.aman.myapp1.fragment;

import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;


import com.example.aman.myapp1.R;
import com.example.aman.myapp1.activity.SearchActivity;
import com.example.aman.myapp1.activity.SearchListActivity;
import com.example.aman.myapp1.app.AppUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {

    private final String TAG = "SearchFragment";
    private static final String jsonFileName = "search.json";
    private View rootView;
    private EditText searchText;
    private ListView searchList;
    private List<String> citiesList;
    private ArrayAdapter adapter;
    private ImageView backButton;
    private Context context;
    InputMethodManager imm;
    private String pagetype;
    public android.support.v4.app.FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_search, container, false);
        context = this.getActivity();

        parseJson();
        searchText = (EditText) rootView.findViewById(R.id.search_text);
        searchList = (ListView) rootView.findViewById(R.id.list_search);
        backButton = (ImageView) rootView.findViewById(R.id.back_button);

        searchText.requestFocus();
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(searchText, InputMethodManager.SHOW_IMPLICIT);

        adapter = new ArrayAdapter(this.getActivity(),R.layout.search_item_list,R.id.list_search_text,citiesList);
      //  searchList.setAdapter(adapter);




        searchText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {

                adapter.getFilter().filter(cs);
                searchList.setAdapter(adapter);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });

        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                searchText.setText(adapter.getItem(position).toString());

                Intent intent = new Intent(context,SearchActivity.class);
                intent.putExtra("searchText",adapter.getItem(position).toString());
                intent.putExtra("pageType","SearchFragment");
                startActivity(intent);
                imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
                if(pagetype != null){

                    if(pagetype.equals("SearchActivity")){ ((SearchActivity)getActivity()).handle_search_back_button();
                    }else if(pagetype.equals("SearchListActivity")){
                        ((SearchListActivity)getActivity()).handle_search_back_button();
                    }
                }


            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pagetype = getArguments().getString("pageType");

                if(pagetype != null){

                    if(pagetype.equals("SearchActivity")){ ((SearchActivity)getActivity()).handle_search_back_button();
                    }else if(pagetype.equals("SearchListActivity")){
                        ((SearchListActivity)getActivity()).handle_search_back_button();
                    }
                }
            }

        });

        return rootView;
    }

    private void parseJson() {
        citiesList = new ArrayList<>();
        Log.i("SearchFragment", "citiesList" + citiesList.toString());

        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset());
            Log.i("SearchFragment", "jsonObject" + jsonObject.toString());

            JSONArray cities = jsonObject.getJSONArray("searchList");

            for(int i=0;i<cities.length();i++){

                JSONObject cityObject = cities.getJSONObject(i);
                Log.i("SearchFragment", "jsonCityObject" + cityObject.toString());
                String city = cityObject.getString("c");
                citiesList.add(city);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = this.getActivity().getAssets().open("search.json");

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
