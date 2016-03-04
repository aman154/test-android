package com.newone.activity;

import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newone.R;
import com.newone.adapter.HomeRListAdapter;
import com.newone.model.HomeResults;
import com.newone.model.MainResultsRoot;
import com.newone.util.AppConstants;
import com.newone.util.AppUtil;
import com.newone.util.NotifUtil;
import com.newone.util.OnItemClickListener;
import com.newone.util.UserProductUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements OnItemClickListener {

    private MainResultsRoot mainResultsRoot;
    private String localAddress;
    private int cartProductCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if(mainResultsRoot == null){ setResultData();}

        initHomeView();

        cartProductCount = UserProductUtil.getLocalProductCount(this);
    }

    public void initHomeAdd(){
        TextView location_text = (TextView) findViewById(R.id.hp_location_tv);
        Intent intent = this.getIntent();
        if (intent != null) {
            localAddress = intent.getStringExtra(AppConstants.CURRENT_ADDRESS_LOCALITY);
        }
        if (localAddress != null) {
            // location_text.setText(localAddress);
            SpannableString content = new SpannableString(localAddress);
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            location_text.setText(content);
        } else {
            location_text.setText("not found");
        }
        location_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, MapLocatorActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initHomeView() {
        Toolbar homeToolbar = (Toolbar) findViewById(R.id.hp_toolbar);
        homeToolbar.setTitle("");
        setSupportActionBar(homeToolbar);

        initHomeAdd();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.hp_main_rv);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        if (mainResultsRoot != null && mainResultsRoot.getHomeResults().size() > 0) {
            HomeRListAdapter adapter = new HomeRListAdapter(HomeActivity.this, mainResultsRoot, this);
            recyclerView.setAdapter(adapter);
        }

        LinearLayout hp_search_ll = (LinearLayout) findViewById(R.id.hp_search_ll);
        hp_search_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SearchActivity.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);

        MenuItem item = menu.findItem(R.id.action_cart);
        LayerDrawable icon = (LayerDrawable) item.getIcon();

        // Update LayerDrawable's BadgeDrawable
        NotifUtil.setBadgeCount(this, icon, cartProductCount);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the HomeActivity/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
      /*  if (id == R.id.action_settings) {
            return true;
        }
        */
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_cart:
                startActivity(new Intent(HomeActivity.this, CartActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(int id, View view, View view1, TextView textView, int position, RecyclerView.ViewHolder viewHolder) {
        if (id == AppConstants.HOME_LIST) {
            if(mainResultsRoot != null && mainResultsRoot.getHomeResults().size() >0) {
                String tg = mainResultsRoot.getHomeResults().get(position).getTg();
                String tt = mainResultsRoot.getHomeResults().get(position).getTt();
                String mpic = mainResultsRoot.getHomeResults().get(position).getMpic();

                Intent i = new Intent(HomeActivity.this, ProductListActivity.class);
                i.putExtra("tg", tg);
                i.putExtra("tt", tt);
                i.putExtra("mpic", mpic);
                String transitionName = this.getString(R.string.home_list_image_transition);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(HomeActivity.this, view, transitionName);
                ActivityCompat.startActivity(HomeActivity.this, i,
                        options.toBundle());
            }
        }
    }

    public void setResultData() {
        mainResultsRoot = new MainResultsRoot();
        HomeResults homeResults;
        List<HomeResults> homeResultsList = new ArrayList<HomeResults>();
        JSONObject itemObject;

        try {
            JSONObject result = new JSONObject(AppUtil.loadJSONFromAsset(this, "home_new.json"));
            JSONArray hpArray = result.getJSONArray("hp");
            for (int i = 0; i < hpArray.length(); i++) {
                homeResults = new HomeResults();
                itemObject = hpArray.getJSONObject(i);
                homeResults.setMpic(itemObject.getString("mpic"));
                homeResults.setTt(itemObject.getString("tt"));
                homeResults.setTg(itemObject.getString("tg"));
                homeResultsList.add(homeResults);
            }
            mainResultsRoot.setHomeResults(homeResultsList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setNotifCount(UserProductUtil.getLocalProductCount(this));
    }

    public void setNotifCount(int count) {
        cartProductCount = count;
        invalidateOptionsMenu();
    }
}
