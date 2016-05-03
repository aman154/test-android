package com.example.aman.myapp1.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aman.myapp1.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class PlacePickerActivity extends ActionBarActivity {

    TextView pick_detail;
    Button picker_button;
    private static final int REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_picker);

        pick_detail = (TextView) findViewById(R.id.detail_text);
        picker_button = (Button) findViewById(R.id.picker_button);

        picker_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
                    Intent intent = intentBuilder.build(PlacePickerActivity.this);

                    startActivityForResult(intent,REQUEST_CODE);

                }catch (GooglePlayServicesRepairableException e)
                {
                    GooglePlayServicesUtil.getErrorDialog(e.getConnectionStatusCode(),PlacePickerActivity.this,0);
                }catch (GooglePlayServicesNotAvailableException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_place_picker, menu);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE){

            if(resultCode == Activity.RESULT_OK){

                Place place = PlacePicker.getPlace(data,getApplicationContext());

                String placeId = place.getId();
                CharSequence name = place.getName();
                CharSequence address =place.getAddress();
                CharSequence phone = place.getPhoneNumber();
                String attribute = PlacePicker.getAttributions(data);

                pick_detail.setText(getString(R.string.detail_text,name,placeId,address,phone,attribute));


            }

        }
    }
}
