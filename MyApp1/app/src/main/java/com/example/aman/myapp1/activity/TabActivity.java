package com.example.aman.myapp1.activity;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.example.aman.myapp1.R;
import com.example.aman.myapp1.adapter.TabAdapter;


class TabActivity extends FragmentActivity {

    private ViewPager viewPager;
    private TabAdapter tabAdapter;
   // private Toolbar toolbar;
  //  private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
/*
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("App");*/

      //  tabLayout = (TabLayout) findViewById(R.id.tabs);
      //  tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
     //   tabLayout.setTabMode(TabLayout.MODE_FIXED);
      /*  tabLayout.addTab(tabLayout.newTab().setText("TopRated"));
        tabLayout.addTab(tabLayout.newTab().setText("Games"));
        tabLayout.addTab(tabLayout.newTab().setText("Movies"));*/

        viewPager = (ViewPager) findViewById(R.id.pager);
        //final ActionBar actionBar = getActionBar();
        tabAdapter = new TabAdapter(getSupportFragmentManager());

        viewPager.setAdapter(tabAdapter);
      //  tabLayout.setupWithViewPager(viewPager);


/*
    viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            // on changing the page
            // make respected tab selected
           // actionBar.setSelectedNavigationItem(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    });*/

    }


}
