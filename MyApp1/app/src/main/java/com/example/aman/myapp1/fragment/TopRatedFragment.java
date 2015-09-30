package com.example.aman.myapp1.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.aman.myapp1.R;
import com.example.aman.myapp1.adapter.ExpandableAdapter;
import com.example.aman.myapp1.model.HomeElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class TopRatedFragment extends android.support.v4.app.Fragment {


    ExpandableListView expandableListView;
    ExpandableAdapter adapter;
    List<HomeElement> headerList;
    HashMap<HomeElement, List<String>> childList;
    List<String> index1,index2,index3,index4,index5,index6;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_top_rated, container, false);

        initLists();

        expandableListView = (ExpandableListView) rootView.findViewById(R.id.top_expd_list);

        adapter = new ExpandableAdapter(getActivity(),headerList,childList);
        expandableListView.setAdapter(adapter);

        return rootView;
    }

    private void initLists(){

        //headerlist elements
        headerList = new ArrayList<HomeElement>();
        headerList.add(new HomeElement(R.drawable.movies_top,"Movies"));
        headerList.add(new HomeElement(R.drawable.games_top,"Games"));
        headerList.add(new HomeElement(R.drawable.movies_top,"Movies"));
        headerList.add(new HomeElement(R.drawable.movies_top,"Movies"));
        headerList.add(new HomeElement(R.drawable.movies_top,"Movies"));
        headerList.add(new HomeElement(R.drawable.movies_top,"Movies"));

        index1 = new ArrayList<String>();
        index1.add("1-1");
        index1.add("1-2");
        index1.add("1-3");

        index2 = new ArrayList<String>();
        index2.add("2-1");
        index2.add("2-2");
        index2.add("2-3");

        index3 = new ArrayList<String>();
        index3.add("3-1");
        index3.add("3-2");
        index3.add("3-3");

        index4 = new ArrayList<String>();
        index4.add("4-1");
        index4.add("4-2");
        index4.add("4-3");

        index5 = new ArrayList<String>();
        index5.add("5-1");
        index5.add("5-2");
        index5.add("5-3");

        index6 = new ArrayList<String>();
        index6.add("6-1");
        index6.add("6-2");
        index6.add("6-3");

        //childlist elements
        childList = new HashMap<HomeElement, List<String>>();
        childList.put(headerList.get(0),index1);
        childList.put(headerList.get(1),index2);
        childList.put(headerList.get(2),index3);
        childList.put(headerList.get(3),index4);
        childList.put(headerList.get(4),index5);
        childList.put(headerList.get(5),index6);


    }


}
