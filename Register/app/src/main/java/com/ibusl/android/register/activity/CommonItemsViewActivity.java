package com.ibusl.android.register.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.ibusl.android.register.R;
import com.ibusl.android.register.adapter.CommonListViewAdapter;
import com.ibusl.android.register.model.Discount;
import com.ibusl.android.register.utilities.AndroidUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aman on 18/4/16.
 */
public class CommonItemsViewActivity extends AppCompatActivity {
    private static final String LOG_TAG = "ComItemsViewFragment";
    private int itemType = 0;
    //test list
    List<Object> items;

    @Bind(R.id.common_items_activity_toolbar)
    Toolbar toolbar;
    @Bind(R.id.common_items_back_iv)
    ImageView backButton;
    @Bind(R.id.common_items_list_view)
    ListView listView;
    @Bind(R.id.common_items_create_bt)
    Button createButton;
    @Bind(R.id.common_item_search_bar_et)
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_items_view);
        ButterKnife.bind(CommonItemsViewActivity.this);

        AndroidUtil.setDrawableColor(this,"#757575",R.drawable.ic_search_black_24dp);

        Intent bundle = getIntent();
        if(bundle != null) {
            itemType = bundle.getIntExtra("item_type",0);
        }
        setFragmentViews(itemType);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setFragmentViews(int itemType) {

        if(itemType == 0){
            createButton.setText("Create item");
            editText.setHint("Search All Items");
            createButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(CommonItemsViewActivity.this, CreateItem.class));
                }
            });
        }else if(itemType == 1){
            createButton.setText("Create Category");
            editText.setHint("Search Categories");
        }else if(itemType == 2){
            createButton.setText("Create Discount");
            editText.setHint("Search Discounts");
            createButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(CommonItemsViewActivity.this, CreateDiscount.class));
                }
            });

            //test data
            initData();
            CommonListViewAdapter adapter = new CommonListViewAdapter(CommonItemsViewActivity.this,items);
            listView.setVerticalScrollBarEnabled(false);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });

        }else if(itemType == 3){
            createButton.setText("Create Module");
            editText.setHint("Search Modules");
        }
    }

    private void initData() {
        Discount discount;
        items = new ArrayList<>();
        for (int i=0; i<10; i++){
            discount = new Discount();
            discount.setDiscountName("discount"+i);

            if(i > 5) {
                discount.setDiscountType("1");
            }else {
                discount.setDiscountType("0");
            }

            if(i == 3) {
                discount.setDiscountQuantity("");
            }else if(i == 7){
                discount.setDiscountQuantity("");
            }else {
                discount.setDiscountQuantity(""+i);
            }
            items.add(discount);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(CommonItemsViewActivity.this);
    }
}
