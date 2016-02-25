package com.example.aman.myapp1.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aman.myapp1.R;
import com.example.aman.myapp1.adapter.HomeListAdapter;
import com.example.aman.myapp1.adapter.ListAdapter;
import com.example.aman.myapp1.model.Detail;
import com.example.aman.myapp1.model.HomeElement;
import com.example.aman.myapp1.util.SwipeDetector;

import java.util.ArrayList;
import java.util.List;


public class Feature4 extends ActionBarActivity {

    private ListView listView;
//    private ListAdapter adapter;
//    private Detail details[];
//    ArrayAdapter<String> adapter1;
    private List<HomeElement> elements;
    private  HomeListAdapter adapter;
    int p;

    int total_list_items,currentFirstVisibleItem;
    TextView toast_tv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature4);

       /* de.add("delhi");
        de.add("bangalore");
        de.add("mumbai");
        de.add("kolkata");
        de.add("chennai");*/

        init();

        toast_tv = (TextView) findViewById(R.id.toast_tv);
        listView = (ListView) findViewById(R.id.list_item1);

       /* adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, de);
        listView.setAdapter(adapter1);*/

        adapter = new HomeListAdapter(Feature4.this, R.layout.homelistview, elements);
        listView.setAdapter(adapter);


      /*  listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {


                Log.i("Feature4","scrollState-"+scrollState);

                if(scrollState == 1 || scrollState ==2){
                    Log.i("Feature4", "visible");
                    toast_tv.setVisibility(View.VISIBLE);
                    toast_tv.setText(currentFirstVisibleItem+" of "+total_list_items);
                }else if(scrollState == 0){
                    Log.i("Feature4", "gone");
                    toast_tv.setVisibility(View.GONE);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                currentFirstVisibleItem = firstVisibleItem;
                total_list_items = totalItemCount;

            }
        });*/

       /* final ListView lv = (ListView) findViewById(R.id.list_item);
        final SwipeDetector swipeDetector = new SwipeDetector();
        lv.setOnTouchListener(swipeDetector);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (swipeDetector.swipeDetected()) {

                    if (swipeDetector.getAction() == SwipeDetector.Action.RL) {
                        de.remove(position);
                        Log.i("remove", "Swipe Right to Left" + position);
                        adapter1.notifyDataSetChanged();
                    } else {
                        Toast.makeText(Feature4.this, "right", Toast.LENGTH_SHORT).show();
                    }

                } else {

                }
            }
        });*/


      /*  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ImageView iv = (ImageView) view.findViewById(R.id.homelist_image);

                Intent i = new Intent(Feature4.this, Movies_Detail.class);

                String transitionName = getString(R.string.movie_image_tran);

               *//* ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(Feature4.this, iv, transitionName);
                startActivity(i, transitionActivityOptions.toBundle());*//*

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(Feature4.this, iv, transitionName);
                ActivityCompat.startActivity(Feature4.this, i,
                        options.toBundle());

            }
        });*/
    }
    public void init(){
        elements = new ArrayList<HomeElement>();
        elements.add(new HomeElement(R.drawable.m1, "Dawn of the Planet of the Ape",1453121987));
        elements.add(new HomeElement(R.drawable.m1, "Dawn of the Planet of the Ape",1453113987));
        elements.add(new HomeElement(R.drawable.m2,"District 9",1453208387));
        elements.add(new HomeElement(R.drawable.m3,"Transformers: Age of Extinction",1452949187));
        elements.add(new HomeElement(R.drawable.m4,"X-Men: Days of Future Past",1454158787));
        elements.add(new HomeElement(R.drawable.m5, "The Machinist",1454158787));
        elements.add(new HomeElement(R.drawable.m6, "The Last Samurai",1453041661));
        elements.add(new HomeElement(R.drawable.m7, "The Amazing Spider-Man 2",1453726787));
        elements.add(new HomeElement(R.drawable.m8,"Tangled",1453467587));
        elements.add(new HomeElement(R.drawable.m1, "Dawn of the Planet of the Ape",1454158787));
        elements.add(new HomeElement(R.drawable.m1, "Dawn of the Planet of the Ape",1453294787));
        elements.add(new HomeElement(R.drawable.m2,"District 9",1453467587));
        elements.add(new HomeElement(R.drawable.m3,"Transformers: Age of Extinction",1453208387));
        elements.add(new HomeElement(R.drawable.m4,"X-Men: Days of Future Past",1454158787));
        elements.add(new HomeElement(R.drawable.m5, "The Machinist",1454158787));
        elements.add(new HomeElement(R.drawable.m6, "The Last Samurai",1453726787));
        elements.add(new HomeElement(R.drawable.m7, "The Amazing Spider-Man 2",1453726787));
        elements.add(new HomeElement(R.drawable.m8,"Tangled",1453121987));
    }


}
