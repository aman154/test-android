package com.example.aman.myapp1.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.aman.myapp1.R;
import com.example.aman.myapp1.adapter.HomeListAdapter;
import com.example.aman.myapp1.model.HomeElement;

import java.util.ArrayList;
import java.util.List;


public class Home extends android.support.v4.app.Fragment {


    List<HomeElement> elements;
    ImageView proImage,backImage;
    TextView nameText;
    ListView homeList;
    HomeListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        init();

        proImage = (ImageView) rootView.findViewById(R.id.home_pro_image);
        backImage = (ImageView) rootView.findViewById(R.id.home_back_image);
        nameText = (TextView) rootView.findViewById(R.id.home_name);
        homeList = (ListView) rootView.findViewById(R.id.home_list);

        adapter = new HomeListAdapter(this.getActivity(), R.layout.homelistview, elements);
        homeList.setAdapter(adapter);

        return rootView;
    }

    public void init(){
        elements = new ArrayList<>();
        elements.add(new HomeElement(R.drawable.ic_home_black_24dp,"Home"));
        elements.add(new HomeElement(R.drawable.ic_person_black_24dp,"Friends"));
        elements.add(new HomeElement(R.drawable.ic_find_in_page_black_24dp,"Search"));
        elements.add(new HomeElement(R.drawable.ic_notifications_black_24dp,"Notification"));
        elements.add(new HomeElement(R.drawable.ic_group_black_24dp,"Groups"));
        elements.add(new HomeElement(R.drawable.ic_settings_black_24dp,"Setting"));
    }

}
