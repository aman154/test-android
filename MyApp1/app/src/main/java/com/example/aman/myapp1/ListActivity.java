package com.example.aman.myapp1;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.aman.myapp1.adapter.ListAdapter;

import java.util.List;


public class ListActivity extends Activity {

    ListView list1,list2;
    ArrayAdapter adapter;
    List<String> typeList,radioList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        list1 = (ListView) findViewById(R.id.selection_list);
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,typeList);

        list2 = (ListView) findViewById(R.id.selected_list);
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,radioList);


    }

}
