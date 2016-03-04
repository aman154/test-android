package com.newone.activity;

import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.newone.R;
import com.newone.util.NotifUtil;
import com.newone.util.UserProductUtil;

public class SearchActivity extends AppCompatActivity {

    Toolbar searchToolbar;
    EditText search_et;
    int cartProductCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchToolbar = (Toolbar) findViewById(R.id.search_toolbar);
        search_et = (EditText) findViewById(R.id.search_et);
        searchToolbar.setTitleTextColor(SearchActivity.this.getResources().getColor(R.color.text_white));
        setSupportActionBar(searchToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(SearchActivity.this.getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

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
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;

            case R.id.action_cart:
                startActivity(new Intent(SearchActivity.this, CartActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
        /*int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {
            startActivity(new Intent(SearchActivity.this,CartActivity.class));
            return true;
        }


        return super.onOptionsItemSelected(item);*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setNotifCount(UserProductUtil.getLocalProductCount(this));
    }

    public void setNotifCount(int count){
        cartProductCount = count;
        invalidateOptionsMenu();
    }
}
