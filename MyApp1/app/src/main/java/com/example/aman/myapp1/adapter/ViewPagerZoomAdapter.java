package com.example.aman.myapp1.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.aman.myapp1.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by aman on 30/3/16.
 */
public class ViewPagerZoomAdapter extends PagerAdapter implements View.OnClickListener {

    Context context;
    ViewPager viewPager;
    LayoutInflater layoutInflater;
    ArrayList<String> urls;


    public ViewPagerZoomAdapter(Context context, ArrayList<String> urls ,ViewPager viewPager) {

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

       /* Intent intent = new Intent(context, ViewPagerZoom.class);
        intent.putStringArrayListExtra("json", getImageUrls(sc_jsonArray));
        intent.putExtra("position",pagerPosition);
        context.startActivity(intent);*/
        //Toast.makeText(context,"viewPagerPosition"+pagerPosition,Toast.LENGTH_SHORT).show();


    }

}