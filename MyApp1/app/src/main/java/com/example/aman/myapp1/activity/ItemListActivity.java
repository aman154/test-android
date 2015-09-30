package com.example.aman.myapp1.activity;

import android.app.Dialog;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.aman.myapp1.R;
import com.example.aman.myapp1.app.AppUtil;
import com.example.aman.myapp1.fragment.ItemListViewFragment;

public class ItemListActivity extends FragmentActivity implements View.OnClickListener {

    LinearLayout filterLL,sortLL;
    ImageView imageList,imageTab,imageGrid;
    int listPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        sortLL = (LinearLayout) findViewById(R.id.item_sort_ll);
        sortLL.setOnClickListener(this);

        imageList = (ImageView) findViewById(R.id.items_list_view);
        imageList.setOnClickListener(this);

        imageTab = (ImageView) findViewById(R.id.items_detail_view);
        imageTab.setOnClickListener(this);

        imageGrid = (ImageView) findViewById(R.id.items_grid_view);
        imageGrid.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        listPosition = AppUtil.getListPosition();
        Log.i("ItemListActivity","listPosition"+AppUtil.getListPosition());

        if(id == imageList.getId()){

            ItemListViewFragment fragment = new ItemListViewFragment();
            Bundle b = new Bundle();
            b.putInt("viewType",0);
            b.putInt("listPosition",listPosition);
            fragment.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.items_list_fragment,fragment).commit();
            imageTab.setVisibility(View.VISIBLE);
            imageList.setVisibility(View.GONE);
            imageGrid.setVisibility(View.GONE);

        }else if(id == imageTab.getId()){

            ItemListViewFragment fragment = new ItemListViewFragment();
            Bundle b = new Bundle();
            b.putInt("viewType",1);
            b.putInt("listPosition",listPosition);
            fragment.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.items_list_fragment,fragment).commit();
            imageGrid.setVisibility(View.VISIBLE);
            imageTab.setVisibility(View.GONE);


        }else if(id == imageGrid.getId()){

            ItemListViewFragment fragment = new ItemListViewFragment();
            Bundle b = new Bundle();
            b.putInt("viewType",2);
            b.putInt("listPosition",listPosition);
            fragment.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.items_list_fragment,fragment).commit();
            imageGrid.setVisibility(View.GONE);
            imageList.setVisibility(View.VISIBLE);
            imageTab.setVisibility(View.GONE);
        }

        else if(id == sortLL.getId()){

            Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.items_sort_dialog_view);
            dialog.setCancelable(true);
            dialog.show();

        }

    }
}
