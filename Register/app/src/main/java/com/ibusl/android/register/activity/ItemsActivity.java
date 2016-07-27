package com.ibusl.android.register.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.ibusl.android.register.adapter.ItemsAdapter;
import com.ibusl.android.register.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ItemsActivity extends AppCompatActivity {

    @Bind(R.id.items_list_view)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        ButterKnife.bind(ItemsActivity.this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView.setBackgroundColor(0xffffffff);
        listView.setDivider(null);
        listView.setDividerHeight(0);
        listView.setVerticalScrollBarEnabled(false);
        listView.setAdapter(new ItemsAdapter(ItemsActivity.this));


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(ItemsActivity.class);
    }
}
