package com.newone.activity;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.newone.R;
import com.newone.adapter.ProductRListAdapter;
import com.newone.model.MainResultsRoot;
import com.newone.model.ProductResults;
import com.newone.util.AppConstants;
import com.newone.util.OnItemClickListener;
import com.newone.util.UserProductUtil;

import java.util.List;

public class CartActivity extends AppCompatActivity implements OnItemClickListener {

    Toolbar cartItemsToolbar;
    TextView cart_user_bill_tv;
    Button cart_checkout_bt;
    RecyclerView cartRecyclerView;
    FrameLayout emp_framelayout;
    LinearLayoutManager mLayoutManager;
    ProductRListAdapter adapter;
    MainResultsRoot mainResultsRoot;
    boolean localPro = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_item);

        cart_user_bill_tv = (TextView) findViewById(R.id.cart_user_bill_tv);
        cart_checkout_bt = (Button) findViewById(R.id.cart_checkout_bt);
        cartItemsToolbar = (Toolbar) findViewById(R.id.cart_items_toolbar);
        cartRecyclerView = (RecyclerView) findViewById(R.id.cart_items_rv);
        emp_framelayout = (FrameLayout) findViewById(R.id.empty_fragment_view);

        setSupportActionBar(cartItemsToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(CartActivity.this.getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        cartItemsToolbar.setTitleTextColor(CartActivity.this.getResources().getColor(R.color.text_white));

        setLocalData();


        if(!localPro){
            Log.i("cart","cart not have any items");
            emp_framelayout.setVisibility(View.VISIBLE);
        }else {

            Log.i("cart","cart have items");
            emp_framelayout.setVisibility(View.GONE);
            cartRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
            cartRecyclerView.setLayoutManager(mLayoutManager);
            Log.i("cart", "cart have items count - " + mainResultsRoot.getProductResults().size());
            adapter = new ProductRListAdapter(CartActivity.this,mainResultsRoot,this);
            cartRecyclerView.setAdapter(adapter);

            setUserBill();
        }
    }

    private void setUserBill(){
        double userBill;
        userBill = UserProductUtil.calculateProductsBill(this);
       // String billText = "Your approx bill is - "+userBill;
        cart_user_bill_tv.setText(String.valueOf(userBill));
    }


    private void setLocalData(){
        mainResultsRoot = new MainResultsRoot();
        if(UserProductUtil.getLocalProductCount(this) > 0){
            Log.i("cart","setting data ...");
            localPro = true;
            List<ProductResults> productResultsList;

            productResultsList = UserProductUtil.getUserProducts(this);

            mainResultsRoot.setProductResults(productResultsList);
        }else {
            localPro = false;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cart_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the HomeActivity/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    @Override
    public void onItemClick(int viewId, View view, View view1, TextView textView, int position, RecyclerView.ViewHolder viewHolder) {
        ProductResults productResult;
        productResult = mainResultsRoot.getProductResults().get(position);
        String proCode = productResult.getPc();
        if(viewId == AppConstants.PRO_ADD) {
            productResult.setQnt(AppConstants.PRO_ADD);
            view.setVisibility(View.GONE);
            view1.setVisibility(View.VISIBLE);
            textView.setText(String.valueOf(AppConstants.PRO_ADD));
            UserProductUtil.addProductToCart(this, productResult);
            setUserBill();
            adapter.notifyDataSetChanged();
            Snackbar.make(view, "Item added to the cart ", Snackbar.LENGTH_SHORT).show();
        }else if(viewId == AppConstants.PRO_INCR){
            if(UserProductUtil.isProductBought(this,proCode)) {
                int qunt = UserProductUtil.getProductQunt(this, proCode);
                if (qunt >= AppConstants.MIN_ORDER_NUMBER && qunt < AppConstants.MAX_ORDER_NUMBER) {
                    textView.setText(String.valueOf(++qunt));
                    UserProductUtil.incrProductQunt(this, productResult.getPc());
                    setUserBill();
                }
            }
        }else if(viewId == AppConstants.PRO_DECR){
            Log.i("home_detail_list", "decr on click listener");
            if(UserProductUtil.isProductBought(this,proCode)) {
                int qunt = UserProductUtil.getProductQunt(this,proCode);
                if (qunt > AppConstants.MIN_ORDER_NUMBER && qunt <= AppConstants.MAX_ORDER_NUMBER) {
                    Log.i("home_detail_list", "decr product details - product-id" + productResult.getPc() + "," + "product-qunt" + qunt);
                    textView.setText(String.valueOf(--qunt));
                    UserProductUtil.decrProductQunt(this, productResult.getPc());
                    setUserBill();
                } else if (qunt == AppConstants.MIN_ORDER_NUMBER) {
                    Log.i("home_detail_list", "removing product details - product-id" + productResult.getPc() + "," + "product-qunt" + qunt);
                    UserProductUtil.removeProductFromCart(this, productResult.getPc());
                    adapter.notifyDataSetChanged();
                    view.setVisibility(View.VISIBLE);
                    view1.setVisibility(View.GONE);
                    setUserBill();
                }
            }
        }

    }
}
