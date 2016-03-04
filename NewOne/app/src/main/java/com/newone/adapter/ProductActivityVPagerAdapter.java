package com.newone.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.newone.fragment.ProductExploreViewPagerFragment;
import com.newone.fragment.ProductViewPagerFragment;
import com.newone.model.MainResultsRoot;

/**
 * Created by aman on 1/12/15.
 */
public class ProductActivityVPagerAdapter extends FragmentPagerAdapter {

    ViewPager viewPager;
    MainResultsRoot mainResultsRoot;
    Context context;

    public ProductActivityVPagerAdapter(Context context, FragmentManager fragmentManager, MainResultsRoot mainResultsRoot, ViewPager viewPager){
        super(fragmentManager);
        this.context = context;
        this.mainResultsRoot = mainResultsRoot;
        this.viewPager = viewPager;
    }

    @Override
    public Fragment getItem(int position) {
        if(position ==0){
            ProductExploreViewPagerFragment productExploreViewPagerFragment = new ProductExploreViewPagerFragment();
            productExploreViewPagerFragment.setMainResultsRoot(mainResultsRoot);
            productExploreViewPagerFragment.setViewPager(viewPager);
            return productExploreViewPagerFragment;
        }else{
        ProductViewPagerFragment productViewPagerFragment = new ProductViewPagerFragment();
            productViewPagerFragment.setMainResultsRoot(mainResultsRoot);
            productViewPagerFragment.setType(mainResultsRoot.getProductType().get(position));
        return productViewPagerFragment;
       }
    }

    @Override
    public int getCount() {
        return mainResultsRoot.getProductType().size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mainResultsRoot.getProductType().get(position);
    }
}
