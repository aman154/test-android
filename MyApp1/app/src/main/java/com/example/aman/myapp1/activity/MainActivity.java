package com.example.aman.myapp1.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.aman.myapp1.R;
import com.example.aman.myapp1.ViewPagerTest;
import com.example.aman.myapp1.demo.DemoMainActivity;
import com.example.aman.myapp1.game_test.GameMainActivity;
import com.example.aman.myapp1.gif_image.GifMainActivity;
import com.example.aman.myapp1.util.AlarmUtil;
import com.example.aman.myapp1.util.LevelView;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int REQ_CODE = 1221;


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
    protected void onResume() {
        super.onResume();
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
                startActivity(new Intent(getApplicationContext(),ViewPagerTest.class));
                break;

            case R.id.feature2:
                startActivity(new Intent(getApplicationContext(), ExpandableViewActivity.class));
                break;

            case R.id.feature3:
                startActivity(new Intent(getApplicationContext(), ItemListActivity.class));
                break;

            case R.id.feature4:
                startActivity(new Intent(getApplicationContext(), GifMainActivity.class));
                break;

            case R.id.feature5:
                startActivity(new Intent(getApplicationContext(), GameMainActivity.class));
                break;

            case R.id.feature6:
                startActivity(new Intent(getApplicationContext(), TestActivity.class));
                break;

            default:
                //Toast.makeText(this,"default",Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
