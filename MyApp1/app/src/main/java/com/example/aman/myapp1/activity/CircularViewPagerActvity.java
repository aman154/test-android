package com.example.aman.myapp1.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.aman.myapp1.R;
import com.example.aman.myapp1.custom_view.LoopPagerAdapterWrapper;
import com.example.aman.myapp1.custom_view.LoopViewPager;
import com.example.aman.myapp1.util.CirclePageIndicator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CircularViewPagerActvity extends ActionBarActivity {
    ArrayList<String> urls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circular_view_pager_actvity);

        addDefaultData();

        CirclePageIndicator circlePageIndicator = (CirclePageIndicator) findViewById(R.id.loop_view_pager_cir_indicator);

        LoopViewPager viewPager = (LoopViewPager) findViewById(R.id.loop_view_pager);
        viewPager.setBoundaryCaching(true);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this,urls,viewPager);

        viewPager.setAdapter(viewPagerAdapter);

        circlePageIndicator.setViewPager(viewPager);

    }

    private void addDefaultData() {
        urls.add("http://img5a.flixcart.com/www/promos/new/20150721_163520_730x300_home-decor-range.jpg");
        urls.add("http://img5a.flixcart.com/www/promos/new/20150727_182549_730x300_slot-3-730-300.jpg");
        urls.add("http://img5a.flixcart.com/www/promos/new/20150727_195329_730x300_image-730-300-copy-4.jpg");
    }

    private class ViewPagerAdapter extends PagerAdapter implements View.OnClickListener {

        Context context;
        ViewPager viewPager;
        LayoutInflater layoutInflater;
        ArrayList<String> urls;


        public ViewPagerAdapter(Context context, ArrayList<String> urls ,ViewPager viewPager) {

            this.context = context;
            this.viewPager = viewPager;
            this.urls = urls;
        }

        public int getCount() {
            if(urls != null && urls.size() > 0) {
                return urls.size();
            }else
                return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView imgDisplay;
            String url = null;

            layoutInflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewLayout = layoutInflater.inflate(R.layout.home_blist_item, container,
                    false);
            imgDisplay = (ImageView) viewLayout.findViewById(R.id.home_blist_image);

            url = urls.get(position);

            if (url != null && url.length() > 0) {
                Picasso.with(context)
                        .load(url)
                        .into(imgDisplay);
            }

            imgDisplay.setOnClickListener(this);

            container.addView(viewLayout);

            return viewLayout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((LinearLayout) object);

        }

        @Override
        public void onClick(View view) {
            int pagerPosition = viewPager.getCurrentItem();
        }

    }

}
