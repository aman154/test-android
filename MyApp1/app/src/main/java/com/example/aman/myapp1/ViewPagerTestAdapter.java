package com.example.aman.myapp1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by aman on 25/7/16.
 */
public class ViewPagerTestAdapter extends FragmentPagerAdapter {

    public ViewPagerTestAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ViewPagerFragmentTest.newInstance(""+position);
    }

    @Override
    public int getCount() {
        return 5;
    }

    public CharSequence getPageTitle(int position) {
       return "tab "+position;
    }
}
