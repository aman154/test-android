package com.example.aman.myapp1.adapter;


import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.aman.myapp1.fragment.Home;
import com.example.aman.myapp1.fragment.MoviesFragment;
import com.example.aman.myapp1.fragment.TopRatedFragment;

/**
 * Created by aman on 30/m6/m15.
 */
public class TabAdapter extends FragmentPagerAdapter {

    public TabAdapter(FragmentManager fm){
        super(fm);
    }

    public android.support.v4.app.Fragment getItem(int index){

        switch (index){
            case 0:
              return new TopRatedFragment();

            case 1:
              return new Home();


            case 2:
              return new MoviesFragment();

        }
        return null;
    }

    public int getCount(){
        return 3;
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "TopRated";
            case 1:
                return "Home";
            case 2:
                default:
                return "Movies";
        }
    }
}
