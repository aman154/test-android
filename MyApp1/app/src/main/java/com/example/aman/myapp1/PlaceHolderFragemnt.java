package com.example.aman.myapp1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by aman on 26/7/16.
 */
public class PlaceHolderFragemnt extends Fragment {

    public PlaceHolderFragemnt() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ViewPagerFragmentTest.
     */
    // TODO: Rename and change types and number of parameters
    public static PlaceHolderFragemnt newInstance(String param1) {
        PlaceHolderFragemnt fragment = new PlaceHolderFragemnt();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.place_holder_fragment, container, false);
    }
}
