package com.ibusl.android.register.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ibusl.android.register.R;
import com.ibusl.android.register.adapter.NavDrawerAdapter;
import com.ibusl.android.register.adapter.RegisterFragmentPagerAdapter;
import com.ibusl.android.register.fragment.ItemsFragment;
import com.ibusl.android.register.fragment.TransactionsFragment;
import com.ibusl.android.register.fragment.ReportsFragment;
import com.ibusl.android.register.fragment.SettingsFragment;
import com.ibusl.android.register.fragment.AboutFragment;
import com.ibusl.android.register.fragment.RegisterKeypadFragment;
import com.ibusl.android.register.utilities.AndroidUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity implements RegisterKeypadFragment.OnRegisterKeypadFragmentInteractionListener {
    private static final String LOG_TAG = RegisterActivity.class.getSimpleName();
    @Bind(R.id.reg_toolbar)
    Toolbar toolbar;
    @Bind(R.id.reg_drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.reg_drawer_list_view)
    ListView listView;
    @Bind(R.id.reg_fragments_tab_layout)
    TabLayout regTabLayout;
    @Bind(R.id.reg_fragment_view_pager)
    ViewPager fragmentViewPager;
    @Bind(R.id.reg_sale_ll)
    LinearLayout regSaleLL;
    @Bind(R.id.reg_charge_ll)
    LinearLayout regChargeLL;
    @Bind(R.id.reg_sale_tv)
    TextView regSaleTextView;
    @Bind(R.id.reg_sale_count_tv)
    TextView regSaleCountTextView;
    @Bind(R.id.reg_charge_tv)
    TextView regChargeTextView;
    @Bind(R.id.reg_money_symbol_tv)
    TextView regMoneySymbolTextView;
    @Bind(R.id.reg_charge_amount_tv)
    TextView regChargeAmountTextView;

    private RegisterFragmentPagerAdapter fragmentPagerAdapter;
    private int count = 0;
    private double totalAmount = 0;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private int resentOpenPosition = -1;

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        Log.e(LOG_TAG, "onCreateView");
        return super.onCreateView(name, context, attrs);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(RegisterActivity.this);
        setSupportActionBar(toolbar);

        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        Point screenSize = AndroidUtil.getRealScreenSize();
        layoutParams.width = AndroidUtil.isTablet() ? AndroidUtil.dp(320) : Math.min(screenSize.x, screenSize.y) - AndroidUtil.dp(56);
        Log.i(LOG_TAG, "  layoutParams.width  -" + layoutParams.width);
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        Log.i(LOG_TAG, "  layoutParams.height  -" + layoutParams.height);
        listView.setLayoutParams(layoutParams);
        listView.setBackgroundColor(0xffffffff);
        listView.setDivider(null);
        listView.setDividerHeight(0);
        listView.setVerticalScrollBarEnabled(false);
        listView.setAdapter(new NavDrawerAdapter(RegisterActivity.this));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onDrawableListItemClick(position);
            }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        fragmentPagerAdapter = new RegisterFragmentPagerAdapter(getSupportFragmentManager());
        fragmentViewPager.setAdapter(fragmentPagerAdapter);
        regTabLayout.setupWithViewPager(fragmentViewPager);

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (drawerLayout == null) {
                    ButterKnife.bind(RegisterActivity.this);
                }
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_fragment_container);
                if (fragment != null){
                    Log.i(LOG_TAG,"fragment name"+fragment.getClass().getName());
                    updateTitle(fragment);
                }else {
                    if(getSupportActionBar() != null) getSupportActionBar().setTitle("Register");
                    resentOpenPosition = -1;
                }
                //TODO for testing
                int count  = getSupportFragmentManager().getBackStackEntryCount();
                Log.i(LOG_TAG,"count - "+count);
                if(count > 0){
                    for (int i=0; i<count; i++){
                        Log.i(LOG_TAG,"Fragment name - "+getSupportFragmentManager().getBackStackEntryAt(i).getName());
                    }
                }
            }
        });

    }

    private void updateTitle(Fragment fragment) {
        if (getSupportActionBar() != null) {
            String fragClassName = fragment.getClass().getName();
            if (fragClassName.equals(TransactionsFragment.class.getName())) {
                getSupportActionBar().setTitle("Transactions");
            } else if (fragClassName.equals(ReportsFragment.class.getName())) {
                getSupportActionBar().setTitle("Reports");
            } else if (fragClassName.equals(ItemsFragment.class.getName())) {
                getSupportActionBar().setTitle("Items");
            } else if (fragClassName.equals(SettingsFragment.class.getName())) {
                getSupportActionBar().setTitle("Settings");
            } else if (fragClassName.equals(AboutFragment.class.getName())) {
                getSupportActionBar().setTitle("About");
            }
        }

    }

    private void onDrawableListItemClick(int position) {
        Fragment fragment = null;
        Log.i(LOG_TAG, "onDrawableListItemClick" + "position - " + position);
        if(position == resentOpenPosition){
            closeDrawable();
        }else {
            if (position == 1) {
                closeDrawerAndPopBackStack(position);
            } else if (position == 2) {
                fragment =  TransactionsFragment.newInstance();
            } else if (position == 3) {
                fragment =  ReportsFragment.newInstance();
            } else if (position == 4) {
                fragment =  ItemsFragment.newInstance();
            } else if (position == 6) {
                fragment =  SettingsFragment.newInstance();
            } else if (position == 7) {
                fragment =  AboutFragment.newInstance();
            }
            if(fragment != null) {
                FragmentManager manager = closeDrawerAndPopBackStack(position);
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.main_fragment_container, fragment);
                transaction.addToBackStack("tag"+position);
                transaction.commit();
            }
        }
    }

    @NonNull
    private FragmentManager closeDrawerAndPopBackStack(int position) {
        closeDrawable();
        resentOpenPosition = position;
        FragmentManager manager = getSupportFragmentManager();
        if(manager.getBackStackEntryCount() > 0){
            manager.popBackStackImmediate();
        }
        return manager;
    }

    private void closeDrawable() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume");
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        Log.i(LOG_TAG, "onBackPressed");
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy");
        ButterKnife.unbind(RegisterActivity.class);
    }

    @Override
    public void OnRegisterKeypadNumberClick(double realAmount, double oldAmount, int salesCount) {
        if (totalAmount == 0) {
            regSaleCountTextView.setVisibility(View.VISIBLE);
            regChargeTextView.setVisibility(View.VISIBLE);
            regSaleTextView.setText("Current Sale");
        }
        if (salesCount == 1) {
            regChargeAmountTextView.setText(String.valueOf(realAmount));
            totalAmount = realAmount;
        } else {
            totalAmount = totalAmount - oldAmount;
            totalAmount = totalAmount + realAmount;
            regChargeAmountTextView.setText(String.valueOf(totalAmount));
        }
        count = salesCount;
        regSaleCountTextView.setText(String.valueOf(count));
    }

    @Override
    public void OnRegisterKeypadCancelClick(double amount, int salesCount) {
        if (salesCount == 0) {
            totalAmount = 0;
            count = 0;
            regSaleCountTextView.setVisibility(View.GONE);
            regChargeTextView.setVisibility(View.GONE);
            regSaleTextView.setText("No Sale");
            regChargeAmountTextView.setText("");
        } else {
            count = salesCount;
            totalAmount = totalAmount - amount;
            regChargeAmountTextView.setText(String.valueOf(totalAmount));
            regSaleCountTextView.setText(String.valueOf(count));
        }
    }

    @Override
    public void OnRegisterKeypadAddClick() {
    }
}
