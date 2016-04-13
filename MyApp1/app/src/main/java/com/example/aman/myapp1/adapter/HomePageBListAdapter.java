package com.example.aman.myapp1.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.aman.myapp1.R;
import com.example.aman.myapp1.activity.Feature4;
import com.example.aman.myapp1.activity.ViewPagerZoom;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by aman on 20/7/15.
 */
public class HomePageBListAdapter extends PagerAdapter implements View.OnClickListener {

    Context context;
    ViewPager viewPager;
    JSONObject hp_jsonObject;
    String page_type;
    LayoutInflater layoutInflater;
    private JSONArray sc_jsonArray;
    private JSONObject sc_jsonObject;
    private static final String HOME_PAGE_SC_MPIC_KEY = "mpic";
    ImageView imgDisplay;
    Activity activity;



    public HomePageBListAdapter(Context context,Activity activity,JSONArray sc_jsonArray,ViewPager viewPager) {

        this.context = context;
        this.sc_jsonArray = sc_jsonArray;
        this.viewPager = viewPager;
        this.activity = activity;

    }

    public int getCount() {
        return sc_jsonArray.length();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        String url = null;

        layoutInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = layoutInflater.inflate(R.layout.home_blist_item, container,
                false);

        imgDisplay = (ImageView) viewLayout.findViewById(R.id.home_blist_image);


        try{
            sc_jsonObject = sc_jsonArray.getJSONObject(position);
            url = sc_jsonObject.getString(HOME_PAGE_SC_MPIC_KEY);

        if (url != null && url.length() > 0) {
            Picasso.with(context)
                    .load(url)
                    .into(imgDisplay);
        }

        viewLayout.setOnClickListener(this);

        }catch (JSONException e){
                e.printStackTrace();}

        container.addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((LinearLayout) object);

    }

    public float getPageWidth(int position) {
        return 0.94f;
    }


    @Override
    public void onClick(View view) {
        int pagerPosition = viewPager.getCurrentItem();

        Intent intent = new Intent(context, ViewPagerZoom.class);
        intent.putStringArrayListExtra("json", getImageUrls(sc_jsonArray));
        intent.putExtra("position",pagerPosition);
        String transitionName = context.getString(R.string.movie_image_tran);
        ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(activity,imgDisplay, transitionName);
        context.startActivity(intent, transitionActivityOptions.toBundle());
        //context.startActivity(intent);
        //Toast.makeText(context,"viewPagerPosition"+pagerPosition,Toast.LENGTH_SHORT).show();


    }

    private ArrayList<String> getImageUrls(JSONArray jsonArray){
        ArrayList<String> urls = new ArrayList<String>();
        JSONObject jsonObject;
        if(jsonArray != null){
            for (int i=0; i<jsonArray.length(); i++){
                String url;
                try {
                    jsonObject = jsonArray.getJSONObject(i);
                    url = jsonObject.getString(HOME_PAGE_SC_MPIC_KEY);
                    urls.add(url);
                }catch (Exception e){}
            }
        }
        return urls;
    }

}