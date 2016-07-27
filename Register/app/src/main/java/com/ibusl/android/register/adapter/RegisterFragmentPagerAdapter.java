package com.ibusl.android.register.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ibusl.android.register.fragment.RegisterKeypadFragment;
import com.ibusl.android.register.fragment.RegisterCategoriesFragment;

/**
 * Created by aman on 7/4/16.
 */
public class RegisterFragmentPagerAdapter extends FragmentPagerAdapter {


    public RegisterFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return RegisterKeypadFragment.newInstance();

            case 1:
                return RegisterCategoriesFragment.newInstance("Name", "RegisterCategoriesFragment");

        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0:
                return "Keypad";
            case 1:
                return "Items";
            default:
                return "";
        }
    }
}
