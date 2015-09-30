package com.example.aman.myapp1.activity;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.aman.myapp1.R;
import com.example.aman.myapp1.fragment.RealtimeUpdates;
import com.example.aman.myapp1.util.WaveyTextView;


public class MaterialDesign1 extends ActionBarActivity {


    AnimatedVectorDrawable searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_design1);

    }

}
