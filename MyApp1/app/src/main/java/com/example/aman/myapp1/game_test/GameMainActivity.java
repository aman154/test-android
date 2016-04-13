package com.example.aman.myapp1.game_test;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.example.aman.myapp1.R;

public class GameMainActivity extends ActionBarActivity implements View.OnClickListener {

    private Button left,right,top,bottom;
    private View mObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);

        initView();

        setClickListeners();
    }

    private void setClickListeners() {
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        top.setOnClickListener(this);
        bottom.setOnClickListener(this);
    }

    private void initView(){
        mObject = findViewById(R.id.movable_object);
        left = (Button) findViewById(R.id.left_);
        right = (Button) findViewById(R.id.right_);
        top = (Button) findViewById(R.id.top_);
        bottom = (Button) findViewById(R.id.bottom_);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.left_:

            case R.id.right_:

            case R.id.top_:

            case R.id.bottom_:

            default:
        }
    }
}
