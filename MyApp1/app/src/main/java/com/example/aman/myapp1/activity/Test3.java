package com.example.aman.myapp1.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.aman.myapp1.R;
import com.example.aman.myapp1.adapter.NewRecyclerAdapter;
import com.example.aman.myapp1.adapter.RecyclerAdapter;
import com.example.aman.myapp1.model.MoviesDetail;

import java.util.ArrayList;
import java.util.List;

public class Test3 extends ActionBarActivity {

    ArrayList<String> details;
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    NewRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test3);


        initList();

        recyclerView = (RecyclerView) findViewById(R.id.new_recycler_view);
        recyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new NewRecyclerAdapter(details);

        recyclerView.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test3, menu);
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


    public void initList(){

        details = new ArrayList<>();

        for (int i=1;i<50;i++){
        details.add(i+"");}



    }
}
