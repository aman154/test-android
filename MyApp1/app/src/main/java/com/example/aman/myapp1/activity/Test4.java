package com.example.aman.myapp1.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.aman.myapp1.R;

public class Test4 extends ActionBarActivity implements View.OnClickListener {

    private static final String TAG = "Test4";

    LinearLayout view1,view2,view3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test4);


        view1 = (LinearLayout) findViewById(R.id.test4_view1);
        view2 = (LinearLayout) findViewById(R.id.test4_view2);
        view3 = (LinearLayout) findViewById(R.id.test4_view3);

        view1.setOnClickListener(this);
        view2.setOnClickListener(this);
        view3.setOnClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test4, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        Log.i(TAG, "view_id"+v.getId());

        switch (id){

            case R.id.test4_view1:
                view1.setBackgroundColor(Test4.this.getResources().getColor(R.color.white));
                view1.setSelected(true);
                Log.i(TAG,"view1_setcolor"+" and "+"view1_selected");
                if(view2.isSelected()){
                    view2.setSelected(false);
                    view2.setBackgroundColor(Test4.this.getResources().getColor(R.color.background_floating_material_dark));
                 }else if(view3.isSelected()){
                    view3.setSelected(false);
                    view3.setBackgroundColor(Test4.this.getResources().getColor(R.color.bg_1));}
                break;

            case R.id.test4_view2:
                view2.setBackgroundColor(Test4.this.getResources().getColor(R.color.white));
                view2.setSelected(true);
                Log.i(TAG,"view2_setcolor"+" and "+"view2_selected");
                if(view1.isSelected()){
                    view1.setSelected(false);
                    view1.setBackgroundColor(Test4.this.getResources().getColor(R.color.message_not_received));
                  }else if(view3.isSelected()){
                    view3.setSelected(false);
                    view3.setBackgroundColor(Test4.this.getResources().getColor(R.color.bg_1));}
                break;

            case R.id.test4_view3:
                Log.i(TAG,"view3_setcolor"+" and "+"view3_selected");
                view3.setBackgroundColor(Test4.this.getResources().getColor(R.color.white));
                view3.setSelected(true);
                if(view2.isSelected()){
                    view2.setSelected(false);
                    view2.setBackgroundColor(Test4.this.getResources().getColor(R.color.background_floating_material_dark));
                  }else if(view3.isSelected()){
                    view3.setSelected(false);
                    view3.setBackgroundColor(Test4.this.getResources().getColor(R.color.bg_1));}
                break;

        }
    }
}
