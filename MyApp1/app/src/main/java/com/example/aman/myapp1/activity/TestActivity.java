package com.example.aman.myapp1.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aman.myapp1.R;
import com.example.aman.myapp1.app.AppUtil;
import com.example.aman.myapp1.fragment.Home_Tab;

public class TestActivity extends FragmentActivity {

    private TextView testText,test_tv;
    private EditText testEdit;
    private Button clickButton;
    LinearLayout linearLayout;

    String filterString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

      //  testText = (TextView) findViewById(R.id.test_text);
      //  testEdit = (EditText) findViewById(R.id.test_edit);
        clickButton =  (Button) findViewById(R.id.click_button);
        clickButton.setVisibility(View.VISIBLE);
   //     linearLayout = (LinearLayout) findViewById(R.id.test_main_ll);

       // test_tv = (TextView) findViewById(R.id.test_tv);


       /* test_tv = new TextView(this);
        test_tv.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT));
        test_tv.setText("test text for background color");
        test_tv.setTextSize(20);*/
       // test_tv.setBackgroundColor(getResources().getColor(R.color.message_not_received));

     // AppUtil.setViewBackColor(linearLayout,getResources().getColor(R.color.message_not_received));
    //  linearLayout.addView(AppUtil.setViewBackColor(this, test_tv, getResources().getColor(R.color.message_not_received)));
      //  linearLayout.addView(test_tv);

        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* filterString = testEdit.getText().toString();

                testText.setText(removeSpeChar(filterString));*/

                Home_Tab home_tab = new Home_Tab();

                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, home_tab,"searchFragment").commit();

                clickButton.setVisibility(View.INVISIBLE);



            }
        });

    }

    private String removeSpeChar(String str){

        String result;

       // result = str.replaceAll("[{(!$&+*%.#^;|@:,_<>?/=`~'-)}]","");
        result = str.replaceAll("[^a-zA-Z ]", "");

        return result;
    }


}
