package com.example.aman.myapp1.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aman.myapp1.R;

import java.util.ArrayList;
import java.util.List;

public class Test15 extends ActionBarActivity {

    List<ApplicationInfo> installedApps;
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test15);

        //getInstallAppMethod1();
        installedApps = getInstallAppMethod2();

        et = (EditText) findViewById(R.id.test15_et);
        findViewById(R.id.test15_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pkgName = String.valueOf(et.getText());

                if (installedApps != null && installedApps.size() > 0 && pkgName != null && pkgName.length() > 0 && isAppInstalled(installedApps, pkgName)) {
                    Toast.makeText(Test15.this, pkgName + " installed ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Test15.this, pkgName + " is not installed ", Toast.LENGTH_LONG).show();
                }

            }
        });

        findViewById(R.id.test15_bt_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    final Dialog dialog = new Dialog(Test15.this);
                    dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
                    dialog.setContentView(R.layout.v4_paytm_registration_dialog);
                    dialog.setFeatureDrawable(Window.FEATURE_LEFT_ICON,getResources().getDrawable(R.drawable.whatsapp_bg_drawble));
                    dialog.setCancelable(true);
                    dialog.show();
                }
        });
    }

    private boolean isAppInstalled(List<ApplicationInfo> installedApps, String pkgName) {
        for (int i = 0; i < installedApps.size() - 1; i++) {
            ApplicationInfo ai = installedApps.get(i);
            if (ai.packageName.equalsIgnoreCase(pkgName)) {
                return true;
            }
        }
        return false;
    }

    private List<ApplicationInfo> getInstallAppMethod2() {
        PackageManager pm = getPackageManager();
        //TODO get apps in separate thread or do in background thread.
        List<ApplicationInfo> apps = pm.getInstalledApplications(0);
        List<ApplicationInfo> systemInstalledApps = new ArrayList<ApplicationInfo>();
        List<ApplicationInfo> userInstalledApps = new ArrayList<ApplicationInfo>();
        if (apps != null) {
            for (ApplicationInfo app : apps) {
                    //this flag to check for system app
                if ((app.flags & ApplicationInfo.FLAG_SYSTEM) == 1) {
                    //add system installed app
                    systemInstalledApps.add(app);
                } else {
                    //in this case, it should be a user-installed app
                    //add user installed app
                    userInstalledApps.add(app);
                }
            }
        }
        if (systemInstalledApps.size() > 0) {
            for (int i = 0; i < systemInstalledApps.size() - 1; i++) {
                ApplicationInfo ai = systemInstalledApps.get(i);
                Log.i("", i + "-app packageName name - " + ai.packageName);
            }
        }
        return userInstalledApps;
    }

    private void getInstallAppMethod1() {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> appList = getPackageManager().queryIntentActivities(intent, 0);
        if (appList != null && appList.size() > 0) {
            for (int i = 0; i < appList.size() - 1; i++) {
                ResolveInfo ri = appList.get(i);
                Log.i("", i + "-app packageName name - " + ri.activityInfo.packageName);
            }
        }
    }

    /**
     * for get Label and icon of application from ApplicationInfo use these methods
     * String label = (String)pm.getApplicationLabel(app);
     * Drawable icon = pm.getApplicationIcon(app);
     */


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test15, menu);
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
