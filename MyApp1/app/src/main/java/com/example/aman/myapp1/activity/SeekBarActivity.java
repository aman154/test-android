package com.example.aman.myapp1.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aman.myapp1.R;


public class SeekBarActivity extends ActionBarActivity {

    SeekBar seekBar;
    TextView t1,t2,t3,t4;
    int seekProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_bar);

        seekBar = (SeekBar) findViewById(R.id.seekbar);
        t1 = (TextView) findViewById(R.id.seekText1);
        t2 = (TextView) findViewById(R.id.seekText2);
        t3 = (TextView) findViewById(R.id.seekText3);
        t4 = (TextView) findViewById(R.id.seekText4);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                int id = seekProgress;
                switch (id){
                    case 0:
                        Toast.makeText(SeekBarActivity.this, "ANYTIME",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(SeekBarActivity.this, "SOMETIME",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(SeekBarActivity.this, "RARELY",Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(SeekBarActivity.this, "NEVER",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //add here your implementation
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                seekProgress = progress;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_seek_bar, menu);
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
}
