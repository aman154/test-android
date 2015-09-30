package com.example.aman.myapp1.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.aman.myapp1.R;
import com.example.aman.myapp1.adapter.ListAdapter;
import com.example.aman.myapp1.model.Detail;

import java.util.ArrayList;

public class TestActivity1 extends ActionBarActivity {

    ArrayList<Detail> details;
    ListView listView;
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_activity1);

        setArray();

        listView = (ListView) findViewById(R.id.test_list);
        adapter = new ListAdapter(this,details);
        listView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_activity1, menu);
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

    public void setArray(){

        details = new ArrayList<>();
        details.add(new Detail("Rs. 100 off","cashback","Get flat 100 cashback ","valid till 30 sept 15"));
        details.add(new Detail("Rs. 150 off","referral","Get flat 150 cashback ","valid till 30 sept 15"));
        details.add(new Detail("Rs. 200 off","cashback","Get flat 200 cashback ","valid till 30 sept 15"));
    }
}
