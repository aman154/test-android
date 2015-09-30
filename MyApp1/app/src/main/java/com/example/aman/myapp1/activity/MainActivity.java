package com.example.aman.myapp1.activity;

import android.content.Intent;
import android.os.*;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.aman.myapp1.util.LevelView;
import com.example.aman.myapp1.R;


public class MainActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LevelView label = new LevelView(this);
        label.setText("NEW");
        label.setBackgroundColor(0xff03a9f4);
        label.setTargetView(findViewById(R.id.feature1), 5, LevelView.Gravity.LEFT_TOP);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId())
        {
            case R.id.action_search:
                Toast.makeText(this,"search",Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_refresh:
                Toast.makeText(this,"refresh",Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_settings:
                Toast.makeText(this, "setting", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    public void onButtonClick(View view)
    {
        int id = view.getId();
        switch (id){

            case R.id.feature1:
                startActivity(new Intent(getApplicationContext(),SearchActivity.class));
                break;

            case R.id.feature2:
                startActivity(new Intent(getApplicationContext(), ItemListActivity.class));
                break;

            case R.id.feature3:
                startActivity(new Intent(getApplicationContext(), TestActivity.class));
                break;

            case R.id.feature4:
                startActivity(new Intent(getApplicationContext(), Feature4.class));
                break;

            case R.id.feature5:
                startActivity(new Intent(getApplicationContext(), TabActivity.class));
                break;

            case R.id.feature6:
                startActivity(new Intent(getApplicationContext(), MapActivity.class));
                break;

            default:
                //Toast.makeText(this,"default",Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
