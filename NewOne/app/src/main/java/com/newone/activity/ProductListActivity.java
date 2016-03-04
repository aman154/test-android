package com.newone.activity;

import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.newone.R;
import com.newone.adapter.ProductActivityVPagerAdapter;
import com.newone.adapter.ProductRListAdapter;
import com.newone.model.Item;
import com.newone.model.MainResultsRoot;
import com.newone.model.ProductResults;
import com.newone.util.AppUtil;
import com.newone.util.NotifUtil;
import com.newone.util.UserProductUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private static final String TAG = "ProductListActivity";

    private TabLayout homeDetailTabs;
    private ViewPager hpDetailViewPager;
    private ProductActivityVPagerAdapter fragmentPagerAdapter;
    private RecyclerView hpDetailList;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView hpDetailHeaderImage;
    private Toolbar hpDetailToolbar;
    private ProductRListAdapter adapter;
    private String jsonObjectString;
    String home_tag, home_mpic, home_tittle;
    private LinearLayoutManager mLayoutManager;
    private MainResultsRoot mainResultsRoot;
    private JSONObject result;
    private Item item = new Item();
    private static int notifCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        Intent intent = getIntent();
        if (intent != null) {
            home_tittle = intent.getStringExtra("tt");
            home_tag = intent.getStringExtra("tg");
            home_mpic = intent.getStringExtra("mpic");
        }

        initViews();

        /*if(jsonObjectString != null && jsonObjectString.length() >0){

            Log.i(TAG,"from string json object");
            try{  result = new JSONObject(jsonObjectString);}catch (Exception e){e.printStackTrace();}
            item = getLocalData(position,result);
            paintPage(item);

        }else {
            Log.i(TAG,"from apputil and reload json from asset");
            try{  result = new JSONObject(AppUtil.loadJSONFromAsset(this,"pro.json"));}catch (Exception e){e.printStackTrace();}
            item = getLocalData(position,result);
        }*/


        notifCount = UserProductUtil.getLocalProductCount(this);

        if (mainResultsRoot == null) {
            setResults(home_tag);
        }

        paintPage(mainResultsRoot, home_mpic, home_tittle);

    }

    private void setResults(String tg) {
        mainResultsRoot = new MainResultsRoot();
        JSONObject object;
        ProductResults productResults;
        List<ProductResults> productResultsList = new ArrayList<>();
        List<String> pdts = new ArrayList<>();

        try {
            result = new JSONObject(AppUtil.loadJSONFromAsset(this, "pro_new1.json"));

            JSONArray array = result.getJSONArray("pds");

            for (int i = 0; i < array.length(); i++) {
                object = array.getJSONObject(i);
                if (object.getString("ptg").equals(tg)) {
                    productResults = new ProductResults();
                    productResults.setMpic(object.getString("mpic"));
                    productResults.setPt(object.getString("pt"));
                    productResults.setPd(object.getString("pd"));
                    productResults.setPc(object.getString("pc"));
                    productResults.setPtg(object.getString("ptg"));
                    productResults.setPstg(object.getString("pstg"));
                    productResults.setPmrp(object.getInt("pmrp"));
                    productResults.setQnt(object.getInt("pqnt"));
                    productResultsList.add(productResults);
                }
            }
            mainResultsRoot.setProductResults(productResultsList);

            JSONObject productType = result.getJSONObject("pdt");
            JSONArray pt = productType.getJSONArray(tg);
            for (int i = 0; i < pt.length(); i++) {
                String type = pt.getString(i);
                Log.i("home_detail_list", "Product type - " + type);
                pdts.add(type);
            }
            mainResultsRoot.setProductType(pdts);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void paintPage(MainResultsRoot mainResultsRoot, String url, String tittle) {
        hpDetailToolbar.setTitle(tittle);
        hpDetailToolbar.setTitleTextColor(getResources().getColor(R.color.text_white));
        hpDetailToolbar.setCollapsible(false);
        setSupportActionBar(hpDetailToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ProductListActivity.this.getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        collapsingToolbarLayout.setTitleEnabled(false);
        //setCollapsingToolbarLayoutTitle(tittle);

        Log.i("HomeRListAdapter", "url-" + url);

        if (url != null && url.length() > 0) {
            Picasso.with(ProductListActivity.this)
                    .load(url)
                    .placeholder(R.drawable.home_list_bg)
                    .error(R.drawable.home_list_bg)
                    .into(hpDetailHeaderImage);
        }

        fragmentPagerAdapter = new ProductActivityVPagerAdapter(this, getSupportFragmentManager(), mainResultsRoot, hpDetailViewPager);
        hpDetailViewPager.setAdapter(fragmentPagerAdapter);
        hpDetailViewPager.setCurrentItem(1);
        homeDetailTabs.setupWithViewPager(hpDetailViewPager);


       /* hpDetailList.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        hpDetailList.setLayoutManager(mLayoutManager);
        adapter = new ProductRListAdapter(ProductListActivity.this,mainResultsRoot,this);
        hpDetailList.setAdapter(adapter);*/
    }

    private void setCollapsingToolbarLayoutTitle(String title) {

        Log.i("HomeRListAdapter", "collapsingToolbarLayout-tittle-" + title);
        collapsingToolbarLayout.setTitle(title);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBarPlus1);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarPlus1);
    }

    public Item getLocalData(int position, JSONObject result) {
        Item item = new Item();
        try {

            JSONArray array = result.getJSONArray("hp");

            JSONObject itemObject = array.getJSONObject(position);

            item.setMpic(itemObject.getString("mpic"));
            item.setMtittle(itemObject.getString("tt"));
            item.setmCount(itemObject.getInt("tc"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }


    public void initViews() {

        hpDetailViewPager = (ViewPager) findViewById(R.id.hp_detail_viewpager);
        homeDetailTabs = (TabLayout) findViewById(R.id.homedetail_list_tabs);
        // hpDetailList = (RecyclerView) findViewById(R.id.hp_detail_lv);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.hp_detail_collapsing_toolbar);
        hpDetailHeaderImage = (ImageView) findViewById(R.id.hp_detail_page_iv);
        hpDetailToolbar = (Toolbar) findViewById(R.id.hp_detail_toolbar);
    }

   /* @Override
    public void onItemClick(int id,View view, View view1,TextView textView,int position, RecyclerView.ViewHolder viewHolder) {
        ProductResults productResult;
        productResult = mainResultsRoot.getProductResults().get(position);
        String proCode = productResult.getPc();
        if(id == AppConstants.PRO_ADD) {
            productResult.setQnt(AppConstants.PRO_ADD);
            view.setVisibility(View.GONE);
            view1.setVisibility(View.VISIBLE);
            textView.setText(String.valueOf(AppConstants.PRO_ADD));
            UserProductUtil.addProductToCart(this, productResult);
            Snackbar.make(view, " Item added to your cart " , Snackbar.LENGTH_SHORT).show();
            setNotifCount(++notifCount);
        }else if(id == AppConstants.PRO_INCR){
            if(UserProductUtil.isProductBought(this,proCode)) {
                int qunt = UserProductUtil.getProductQunt(this, proCode);
                if (qunt >= AppConstants.MIN_ORDER_NUMBER && qunt < AppConstants.MAX_ORDER_NUMBER) {
                    textView.setText(String.valueOf(++qunt));
                    UserProductUtil.incrProductQunt(this, productResult.getPc());
                }
            }
        }else if(id == AppConstants.PRO_DECR){
            Log.i("home_detail_list","decr on click listener");
            if(UserProductUtil.isProductBought(this,proCode)) {
                int qunt = UserProductUtil.getProductQunt(this,proCode);
                if (qunt > AppConstants.MIN_ORDER_NUMBER && qunt <= AppConstants.MAX_ORDER_NUMBER) {
                    Log.i("home_detail_list", "decr product details - product-id" + productResult.getPc() + "," + "product-qunt" + qunt);
                    textView.setText(String.valueOf(--qunt));
                    UserProductUtil.decrProductQunt(this, productResult.getPc());
                } else if (qunt == AppConstants.MIN_ORDER_NUMBER) {
                    Log.i("home_detail_list", "removing product details - product-id" + productResult.getPc() + "," + "product-qunt" + qunt);
                    UserProductUtil.removeProductFromCart(this, productResult.getPc());
                    view.setVisibility(View.VISIBLE);
                    view1.setVisibility(View.GONE);
                    setNotifCount(--notifCount);
                }
            }
        }

    }*/

    public void setNotifCount(int count) {
        notifCount = count;
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_list_detail, menu);


        // Get the notifications MenuItem and
        // its LayerDrawable (layer-list)
        MenuItem item = menu.findItem(R.id.action_cart);
        LayerDrawable icon = (LayerDrawable) item.getIcon();

        // Update LayerDrawable's BadgeDrawable
        NotifUtil.setBadgeCount(this, icon, notifCount);

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
            case R.id.action_settings:
                return true;

            case R.id.action_cart:
                startActivity(new Intent(ProductListActivity.this, CartActivity.class));
                return true;

            case R.id.action_search:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setNotifCount(UserProductUtil.getLocalProductCount(this));
    }

    //for setting color of collapsing toolbar layout dynamically from the color of background image in layout.
    /*Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
        @SuppressWarnings("ResourceType")
        @Override
        public void onGenerated(Palette palette) {
            int vibrantColor = palette.getVibrantColor(R.color.primary_500);
            int vibrantDarkColor = palette.getDarkVibrantColor(R.color.primary_700);
            collapsingToolbarLayout.setContentScrimColor(vibrantColor);
            collapsingToolbarLayout.setStatusBarScrimColor(vibrantDarkColor);
        }
    });*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
