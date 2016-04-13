package com.example.aman.myapp1.activity;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.aman.myapp1.R;
import com.example.aman.myapp1.adapter.HomePageBListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

public class ViewPagerActivity extends ActionBarActivity {

    private ViewPager viewPager;
    private JSONObject home_page,hp_jsonObject;
    private static final String HOME_PAGE_KEY = "hp";
    private static final String HOME_PAGE_SS_KEY = "ss";
    private static final String HOME_PAGE_SC_KEY = "sc";
    private static final String HOME_PAGE_SS_BIG = "b";
    private JSONArray hp_jsonArray,sc_jsonArray;
    private HomePageBListAdapter bListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        if (home_page == null) {
            try {
                Log.e("HomeTab", "loadJSONFromAsset() " + loadJSONFromAsset(ViewPagerActivity.this));
                home_page = new JSONObject(loadJSONFromAsset(ViewPagerActivity.this));
                Log.i("HomeTab", "jsonObject" + home_page.toString());
            } catch (Exception e) {
                // Log.e("HomeTab","Exception json -"+ e,e);
                e.printStackTrace();
            }
        }
        try {
            hp_jsonArray = home_page.getJSONArray(HOME_PAGE_KEY);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(int i=0; i<hp_jsonArray.length(); i++){
            String ss_string_json;
            try {
                hp_jsonObject = hp_jsonArray.getJSONObject(i);
                ss_string_json = hp_jsonObject.getString(HOME_PAGE_SS_KEY);
                if(ss_string_json.equals(HOME_PAGE_SS_BIG)) {
                    sc_jsonArray = hp_jsonObject.getJSONArray(HOME_PAGE_SC_KEY);
                }
            }catch (Exception e){
                e.printStackTrace();
            }


        }
        viewPager = (ViewPager) findViewById(R.id.view_pager_test);

        if(sc_jsonArray != null){
            bListAdapter = new HomePageBListAdapter(this,ViewPagerActivity.this,sc_jsonArray,viewPager);

            viewPager.setAdapter(bListAdapter);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_pager, menu);
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
}
