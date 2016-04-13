package com.example.aman.myapp1.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.aman.myapp1.R;
import com.example.aman.myapp1.adapter.HomePageBListAdapter;
import com.example.aman.myapp1.adapter.ViewPagerZoomAdapter;

import org.json.JSONObject;

import java.util.ArrayList;

public class ViewPagerZoom extends ActionBarActivity {

    private ViewPager viewPager;
    private int position;
    private ArrayList<String> urls;
    private ViewPagerZoomAdapter viewPagerZoomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_zoom);

        Intent intent = getIntent();
        urls = intent.getStringArrayListExtra("json");
        position = intent.getIntExtra("position",0);

        viewPager = (ViewPager) findViewById(R.id.view_pager_zoom);
        if(urls != null){
            viewPagerZoomAdapter = new ViewPagerZoomAdapter(this,urls,viewPager);
            viewPager.setAdapter(viewPagerZoomAdapter);
            viewPager.setCurrentItem(position);
        }


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        findViewById(R.id.remove_view_pager_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_pager_zoom, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
