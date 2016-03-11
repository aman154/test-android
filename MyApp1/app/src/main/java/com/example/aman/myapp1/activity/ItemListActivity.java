package com.example.aman.myapp1.activity;

import android.app.Dialog;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.aman.myapp1.R;
import com.example.aman.myapp1.app.AppUtil;
import com.example.aman.myapp1.fragment.ItemListViewFragment;

public class ItemListActivity extends FragmentActivity implements View.OnClickListener {

    private LinearLayout filterLL,sortLL;
    private FrameLayout items_list_fragment;
    private ImageView imageList,imageTab,imageGrid;
    private int listPosition = 0,listType = 0;
    private ItemListViewFragment fragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        initViews();
        setViewsListener();

        if(savedInstanceState != null){
            listPosition = savedInstanceState.getInt("LIST_POSITION");
            listType = savedInstanceState.getInt("LIST_TYPE");
            startListFragment(listType, listPosition);
        }else {
            startListFragment(listType, listPosition);
        }
    }

    private void setViewsListener() {
        sortLL.setOnClickListener(this);
        imageList.setOnClickListener(this);
        imageTab.setOnClickListener(this);
        imageGrid.setOnClickListener(this);
    }

    private void initViews(){
        items_list_fragment = (FrameLayout) findViewById(R.id.items_list_fragment);
        sortLL = (LinearLayout) findViewById(R.id.item_sort_ll);
        imageList = (ImageView) findViewById(R.id.items_list_view);
        imageTab = (ImageView) findViewById(R.id.items_detail_view);
        imageGrid = (ImageView) findViewById(R.id.items_grid_view);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        listPosition = AppUtil.getListPosition();
        outState.putInt("LIST_POSITION",listPosition);
        outState.putInt("LIST_TYPE",listType);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        listPosition = AppUtil.getListPosition();
        Log.i("ItemListActivity","listPosition"+AppUtil.getListPosition());

        if(id == imageList.getId()){
            listType = 0;
            startListFragment(listType,listPosition);

        }else if(id == imageTab.getId()){
            listType = 1;
            startListFragment(listType, listPosition);


        }else if(id == imageGrid.getId()){
            listType = 2;
            startListFragment(listType, listPosition);
        }

        else if(id == sortLL.getId()){
            Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.items_sort_dialog_view);
            dialog.setCancelable(true);
            dialog.show();

        }

    }

    private void startListFragment(int listType, int listPosition) {
        if(listType == 0) {
            imageTab.setVisibility(View.VISIBLE);
            imageList.setVisibility(View.GONE);
            imageGrid.setVisibility(View.GONE);
        }else if(listType == 1){
            imageGrid.setVisibility(View.VISIBLE);
            imageTab.setVisibility(View.GONE);
        }else if(listType == 2){
            imageGrid.setVisibility(View.GONE);
            imageList.setVisibility(View.VISIBLE);
            imageTab.setVisibility(View.GONE);
        }
        fragment = new ItemListViewFragment();
        Bundle b = new Bundle();
        b.putInt("viewType", listType);
        b.putInt("listPosition", listPosition);
        fragment.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.items_list_fragment,fragment,"list_fragment").commit();

    }
}
