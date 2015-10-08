package com.example.aman.myapp1.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.aman.myapp1.R;
import com.example.aman.myapp1.util.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by aman on 1/8/15.
 */
public class HomeTabListAdapter extends BaseAdapter implements ViewPager.OnPageChangeListener {


    private final int TYPE_MAX_COUNT = 2;
    private final int BIG_LAYOUT = 0;
    private final int SMALL_LAYOUT = 1;

    private int viewPagerCount,previousState,currentState;

    private Context context;
    private JSONArray hp_array;
    JSONObject hp_jsonObject;
    String ss_string_json = null;
    private LayoutInflater layoutInflater;

    ViewHolderB b_holder = null;
    ViewHolderS s_holder = null;

    HomePageBListAdapter bListAdapter;
    HomePageSListAdapter sListAdapter;

    private static final String HOME_PAGE_KEY = "hp";
    private static final String HOME_PAGE_ST_KEY ="st";
    private static final String HOME_PAGE_SS_KEY = "ss";
    private static final String HOME_PAGE_SC_KEY = "sc";
    private static final String HOME_PAGE_VTT_KEY = "vtt";
    private static final String HOME_PAGE_SS_BIG = "b";
    private static final String HOME_PAGE_SS_SMALL ="s";



    public HomeTabListAdapter(Context context, JSONArray hp_array){

        this.context = context;
        this.hp_array = hp_array;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getItemViewType(int position) {

        try {
            hp_jsonObject = hp_array.getJSONObject(position);
            ss_string_json = hp_jsonObject.getString(HOME_PAGE_SS_KEY);
           }catch (JSONException e) {e.printStackTrace();}

       if (ss_string_json.equals(HOME_PAGE_SS_BIG))
            return BIG_LAYOUT;
       else
      // if(ss_string_json.equals(HOME_PAGE_SS_SMALL))
           return SMALL_LAYOUT;

    }

    @Override
    public int getViewTypeCount() {
        return TYPE_MAX_COUNT;
    }

    @Override
    public int getCount() {
        return hp_array.length();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        JSONArray sc_jsonArray = null;
        String st_string_json = null;
        String vtt_string_json = null;

        try {
            hp_jsonObject = hp_array.getJSONObject(position);
            if (hp_jsonObject.has(HOME_PAGE_ST_KEY)){
            st_string_json = hp_jsonObject.getString(HOME_PAGE_ST_KEY);}
            ss_string_json = hp_jsonObject.getString(HOME_PAGE_SS_KEY);
            vtt_string_json = hp_jsonObject.getString(HOME_PAGE_VTT_KEY);
            sc_jsonArray = hp_jsonObject.getJSONArray(HOME_PAGE_SC_KEY);
        }catch (Exception e){
            e.printStackTrace();
        }


        int type = getItemViewType(position);
        Log.i("HomeTabListAdapter","layoutType"+type);

        switch(type) {

            case BIG_LAYOUT:

            View b_view = convertView;
            if (b_view == null) {
                b_holder = new ViewHolderB();

                    b_view = layoutInflater.inflate(R.layout.home_tab_big_list, null);
                    b_holder.big_st_tv_ll = (LinearLayout) b_view.findViewById(R.id.big_st_tv_ll);
                    b_holder.big_st_tv = (TextView) b_view.findViewById(R.id.big_st_tv);
                    b_holder.big_vtt_tv = (TextView) b_view.findViewById(R.id.big_vtt_tv);
                    b_holder.big_images_view = (ViewPager) b_view.findViewById(R.id.big_images_view);
                    b_holder.big_cir_indicator = (CirclePageIndicator) b_view.findViewById(R.id.big_cir_indicator);
                        b_view.setTag(b_holder);

            } else {

                b_holder = (ViewHolderB) b_view.getTag();

            }

                if(hp_jsonObject != null && hp_array != null && sc_jsonArray != null && ss_string_json.equals(HOME_PAGE_SS_BIG) ){

                if(st_string_json != null && !st_string_json.isEmpty()){
                   b_holder.big_st_tv.setText(st_string_json);
                    b_holder.big_st_tv_ll.setVisibility(View.VISIBLE);
                    b_holder.big_st_tv.setVisibility(View.VISIBLE);
                }else{
                    b_holder.big_st_tv_ll.setVisibility(View.GONE);
                    b_holder.big_st_tv.setVisibility(View.GONE);

                }

                bListAdapter = new HomePageBListAdapter(context,sc_jsonArray,b_holder.big_images_view);

                b_holder.big_images_view.setAdapter(bListAdapter);

                    viewPagerCount = bListAdapter.getCount();

                b_holder.big_cir_indicator.setViewPager(b_holder.big_images_view);

                b_holder.big_vtt_tv.setText(vtt_string_json);

                    b_holder.big_images_view.setOnPageChangeListener(this);

                }

                return b_view;


           case SMALL_LAYOUT:

               View s_view  = convertView;

               if(s_view == null) {
                   s_holder = new ViewHolderS();

                   s_view = layoutInflater.inflate(R.layout.home_tab_small_list, null);
                   s_holder.small_st_tv = (TextView) s_view.findViewById(R.id.small_st_tv);
                   s_holder.small_vtt_tv = (TextView) s_view.findViewById(R.id.small_vtt_tv);
                   s_holder.small_images_view = (RecyclerView) s_view.findViewById(R.id.small_images_view);
                   s_view.setTag(s_holder);
               }
               else{

                   s_holder = (ViewHolderS) s_view.getTag();
               }


               if(hp_jsonObject != null && hp_array != null && sc_jsonArray != null && ss_string_json.equals(HOME_PAGE_SS_SMALL) ){

               s_holder.small_st_tv.setText(st_string_json);
               if(vtt_string_json != null){
                   s_holder.small_vtt_tv.setText(vtt_string_json);
                   s_holder.small_vtt_tv.setVisibility(View.VISIBLE);
               }

               s_holder.small_images_view.setHasFixedSize(true);
               s_holder.small_images_view.setScrollingTouchSlop(RecyclerView.HORIZONTAL);
               LinearLayoutManager mLayoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
               s_holder.small_images_view.setLayoutManager(mLayoutManager);
               sListAdapter = new HomePageSListAdapter(context,sc_jsonArray,s_holder.small_images_view);
               s_holder.small_images_view.setAdapter(sListAdapter);

               }

               return s_view;
        }

        return convertView;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        b_holder.big_cir_indicator.setCurrentItem(position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

        int currentPage = b_holder.big_images_view.getCurrentItem();

        if (currentPage == viewPagerCount-1 || currentPage == 0) {
            previousState = currentState;
            currentState = state;
            if (previousState == 1 && currentState == 0) {
                b_holder.big_images_view.setCurrentItem(currentPage == 0 ? viewPagerCount-1 : 0,false);
            }
        }

    }

    public class ViewHolderB{
      LinearLayout big_st_tv_ll;
      TextView big_st_tv,big_vtt_tv;
      ViewPager big_images_view;
      CirclePageIndicator big_cir_indicator;

   }

  public class ViewHolderS{

     RecyclerView small_images_view;
     TextView small_st_tv,small_vtt_tv;
  }
}
