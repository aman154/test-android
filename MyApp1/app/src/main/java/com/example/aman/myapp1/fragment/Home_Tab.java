package com.example.aman.myapp1.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.aman.myapp1.R;
import com.example.aman.myapp1.adapter.HomeTabListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;


public class Home_Tab extends Fragment {

    private Context mActivity = this.getActivity();
    private ListView home_tab_list;
    private HomeTabListAdapter adapter;
    private View rootView;
    private JSONObject home_page;
    LinearLayoutManager mLayoutManager;
    private LinearLayout containerLL;
    private TextView st_text,vtt_text,s_ll_viewall_text,b_ll_viewall_text;

    private static final String HOME_PAGE_KEY = "hp";
    private static final String HOME_PAGE_ST_KEY ="st";
    private static final String HOME_PAGE_SS_KEY = "ss";
    private static final String HOME_PAGE_SC_KEY = "sc";
    private static final String HOME_PAGE_VTT_KEY = "vtt";
    private static final String HOME_PAGE_SS_BIG = "b";
    private static final String HOME_PAGE_SS_SMALL ="s";

    JSONArray hp_jsonArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mActivity = this.getActivity();
        if (rootView != null) {

            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
            return rootView;

        }else rootView = inflater.inflate(R.layout.home_tab, container, false);


       // Log.e("HomeTab", "getJson() " +getJson());
      if(home_page == null){
            try{
                 Log.e("HomeTab", "loadJSONFromAsset() " +loadJSONFromAsset(this.getActivity()));
                 home_page = new JSONObject(loadJSONFromAsset(this.getActivity()));
                 Log.i("HomeTab", "jsonObject" + home_page.toString());
            }catch (Exception e){
               // Log.e("HomeTab","Exception json -"+ e,e);
                e.printStackTrace();
            }

        }
        try {
            hp_jsonArray = home_page.getJSONArray(HOME_PAGE_KEY);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if(hp_jsonArray.length() != 0)
        {
        home_tab_list = (ListView) rootView.findViewById(R.id.home_tab_list);

        adapter = new HomeTabListAdapter(mActivity,hp_jsonArray);

        home_tab_list.setAdapter(adapter);
        }

       /* containerLL = (LinearLayout) rootView.findViewById(R.id.container_ll);


        if (hp_jsonArray.length()!=0) {
            for (int i = 0; i < hp_jsonArray.length(); i++) {

                JSONObject hp_jsonObject;
                JSONArray sc_jsonArray;
                final String st_string_json;
                String ss_string_json;
                String vtt_string_json;

                try {
                    hp_jsonObject = hp_jsonArray.getJSONObject(i);
                    st_string_json = hp_jsonObject.getString(HOME_PAGE_ST_KEY);
                    ss_string_json = hp_jsonObject.getString(HOME_PAGE_SS_KEY);
                    vtt_string_json = hp_jsonObject.getString(HOME_PAGE_VTT_KEY);
                    sc_jsonArray = hp_jsonObject.getJSONArray(HOME_PAGE_SC_KEY);

                    if (ss_string_json.equals(HOME_PAGE_SS_BIG)) {


                        LinearLayout b_ll = new LinearLayout(mActivity);
                        b_ll.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT));
                        b_ll.setBackgroundColor(getResources().getColor(R.color.line_grey));
                        b_ll.setOrientation(LinearLayout.VERTICAL);

                        if (!st_string_json.isEmpty()) {

                            RelativeLayout pageDetail_rl = new RelativeLayout(mActivity);
                            pageDetail_rl.setLayoutParams(new RelativeLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT));

                            TextView  st_btext = getStText(mActivity, st_string_json);
                            pageDetail_rl.addView(st_btext);
                            b_ll.addView(pageDetail_rl);
                        }

                        LinearLayout bImageLL = new LinearLayout(mActivity);
                        bImageLL.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.homepage_trendingImgLL_height)));
                        bImageLL.setBackgroundColor(getResources().getColor(R.color.line_grey));
                        bImageLL.setPadding(20, 10, 0, 10);

                        HomePageBListAdapter homePageBListAdapter = new HomePageBListAdapter(mActivity, sc_jsonArray);
                        ViewPager bImagePager = new ViewPager(mActivity);
                        bImagePager.setAdapter(homePageBListAdapter);

                        bImagePager.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        });

                        bImageLL.addView(bImagePager);
                        b_ll.addView(bImageLL);


                        RelativeLayout indicator_rl = new RelativeLayout(mActivity);
                        indicator_rl.setLayoutParams(new RelativeLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.homepage_indecator_rl_height)));
                        indicator_rl.setBackgroundColor(getResources().getColor(R.color.line_grey));

                        CirclePageIndicator indicator = new CirclePageIndicator(mActivity);
                        indicator.setFillColor(getResources().getColor(R.color.profile_tab_green));
                        indicator.setRadius(8.0f);
                        indicator.setPageColor(getResources().getColor(R.color.bg_1));
                        indicator.setPadding(25, 20, 0, 0);
                        indicator.setLayoutParams(new RelativeLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT));
                        indicator.setViewPager(bImagePager);
                        indicator_rl.addView(indicator, 0);

                        b_ll_viewall_text = getVtt_text(mActivity, vtt_string_json);
                        //click event on view all large linear layout list view
                        b_ll_viewall_text.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                               // handle_b_ll_viewall_text_click(view, st_string_json, );
                            }
                        });
                        indicator_rl.addView(b_ll_viewall_text);
                        b_ll.addView(indicator_rl);

                        containerLL.addView(b_ll);
                    }

                if (ss_string_json.equals(HOME_PAGE_SS_SMALL)) {

                    LinearLayout s_ll = new LinearLayout(mActivity);
                    s_ll.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT));
                    s_ll.setBackgroundColor(getResources().getColor(R.color.white));
                    s_ll.setOrientation(LinearLayout.VERTICAL);
                    s_ll.setPadding(0, 20, 0, 0);

                    RelativeLayout pageDetail_rl = new RelativeLayout(mActivity);
                    pageDetail_rl.setLayoutParams(new RelativeLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT));

                    TextView st_stext = getStText(mActivity, st_string_json);
                    pageDetail_rl.addView(st_stext);

                    s_ll_viewall_text = getVtt_text(mActivity, vtt_string_json);
                   //click event on view all small linear layout list view
                    s_ll_viewall_text.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            handle_s_ll_viewall_text_click(view, st_string_json);
                        }
                    });
                    pageDetail_rl.addView(s_ll_viewall_text);
                    s_ll.addView(pageDetail_rl);

                    RecyclerView recyclerView = new RecyclerView(mActivity);
                    recyclerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.homepage_pagerLL_height)));
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setScrollingTouchSlop(RecyclerView.HORIZONTAL);

                    mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(mLayoutManager);
                    HomePageSListAdapter sListAdapter = new HomePageSListAdapter(mActivity, sc_jsonArray);
                    recyclerView.setAdapter(sListAdapter);

                    s_ll.addView(recyclerView);
                    containerLL.addView(s_ll);

                }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }*/


        return rootView;
    }


    public TextView getStText(Context context,String text){

        st_text = new TextView(context);
        st_text.setLayoutParams(new RelativeLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT));
        st_text.setText(text);
        st_text.setTextSize(18);
        RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) st_text.getLayoutParams();
        params1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        st_text.setLayoutParams(params1);
        st_text.setPadding(20, 20, 0, 20);
        st_text.setTextColor(getResources().getColor(R.color.textcolor1));

        return st_text;
    }


    public TextView getVtt_text(Context context,String text){

        vtt_text = new TextView(context);
        vtt_text.setLayoutParams(new RelativeLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT));
        vtt_text.setText(text);
        vtt_text.setTextSize(9);
        vtt_text.setTypeface(null, Typeface.BOLD);
        vtt_text.setTextColor(getResources().getColor(R.color.bg_1));
        vtt_text.setGravity(RelativeLayout.CENTER_VERTICAL);
        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) vtt_text.getLayoutParams();
        params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        vtt_text.setLayoutParams(params2);
        vtt_text.setPadding(0, 15, 20, 20);

        return vtt_text;
    }



    public static String loadJSONFromAsset(Context context) {
        String json;
        try {

            InputStream is = context.getAssets().open("hp.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");

        } catch (Exception ex) {

            ex.printStackTrace();
            return null;
        }
        return json;

    }

    public void handle_s_ll_viewall_text_click(View view,String st){

        Toast.makeText(mActivity,"st_text-"+st,Toast.LENGTH_SHORT).show();
    }

}
