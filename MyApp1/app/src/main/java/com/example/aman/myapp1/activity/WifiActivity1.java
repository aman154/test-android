package com.example.aman.myapp1.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aman.myapp1.R;
import com.example.aman.myapp1.WifiUtil;
import com.example.aman.myapp1.WifiUtil1;

import java.util.List;

public class WifiActivity1 extends AppCompatActivity implements WifiUtil.ActionListener {
    private static final String TAG = "WifiActivity1";
    private static final int REQ_CODE = 1221;
    private static final int REQ_CODE1 = 1222;
    private WifiManager wifiManager;
    private WifiUtil wifiUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi1);

        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        findViewById(R.id.connect_to_wifi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (ActivityCompat.checkSelfPermission(WifiActivity1.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(WifiActivity1.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(WifiActivity1.this, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(WifiActivity1.this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(WifiActivity1.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.ACCESS_WIFI_STATE}, REQ_CODE);
                    }else {
                        startWifiDialog(WifiActivity1.this);
                    }
                    }else {
                    startWifiDialog(WifiActivity1.this);
                }
            }
        });


        findViewById(R.id.create_hotspot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (ActivityCompat.checkSelfPermission(WifiActivity1.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(WifiActivity1.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(WifiActivity1.this, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(WifiActivity1.this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(WifiActivity1.this, Manifest.permission.WRITE_SETTINGS) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(WifiActivity1.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.WRITE_SETTINGS}, REQ_CODE1);
                    }else {
                        createHotspotDialog(WifiActivity1.this);
                    }
                    }else {
                    createHotspotDialog(WifiActivity1.this);
                }
            }
        });
    }

    private void scanWifiAndConnect1(String ssid, String pwd) {
        try {
            if (wifiManager == null) {
                wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
            }

            if (!wifiManager.isWifiEnabled()) {
                Toast.makeText(this, "wifi is not enable", Toast.LENGTH_LONG).show();
                wifiManager.setWifiEnabled(true);
            }
            if (wifiManager.startScan()) {
                Log.i(TAG, "getScanResults size -" + wifiManager.getScanResults().size());
                WifiUtil1.connectToWifi(wifiManager, wifiManager.getScanResults(), ssid, pwd, null);
            }
        } catch (Exception e) {
        }
    }

    private void scanWifiAndConnect(final String ssid, final String pwd) {
        try {
            wifiUtil = new WifiUtil(this, new WifiUtil.WifiScanListener() {
                @Override
                public void onScanResults(List<ScanResult> scanResults) {
                    wifiUtil.connectToWifi(scanResults, ssid, pwd, new WifiUtil.ActionListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(WifiActivity1.this, "Connected", Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onFailure(String msg) {
                            Toast.makeText(WifiActivity1.this, msg, Toast.LENGTH_LONG).show();
                            return;
                        }
                    });
                    wifiUtil.unRegWifiReceiver();
                }
            });
            if (wifiUtil.isWifiConnected(WifiActivity1.this,ssid)) {
                Toast.makeText(WifiActivity1.this, "wifi is connected", Toast.LENGTH_LONG).show();

            }else {
                wifiUtil.regWifiReceiver();
            }
        } catch (Exception e) {
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQ_CODE:
                boolean is_per_gran = false;
                for (int i=0; i<grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        is_per_gran =true;
                    }
                }
                if (is_per_gran) {
                    startWifiDialog(WifiActivity1.this);
                }
            break;
            case REQ_CODE1:
                boolean is_per_gran1 = false;
                for (int i=0; i<grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        is_per_gran1 =true;
                    }
                }
                if (is_per_gran1) {
                    createHotspotDialog(WifiActivity1.this);
                }
        }
    }

    private void startWifiDialog(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.wifi_dialog1);
        dialog.setCancelable(false);
        final EditText text = (EditText) dialog.findViewById(R.id.wifi_edit);
        final EditText text1 = (EditText) dialog.findViewById(R.id.wifi_edit1);
        dialog.show();

        Button cancel_button = (Button) dialog.findViewById(R.id.wifi_cancel_bt);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button accept_button = (Button) dialog.findViewById(R.id.wifi_connect_bt);

        accept_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "accept_button");
                if (text != null && text1 != null && text1.getText().length() >= 8) {
                    dialog.dismiss();
                    scanWifiAndConnect(text.getText().toString(), text1.getText().toString());
                }
            }
        });
    }

    private void createHotspotDialog(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.wifi_dialog1);
        dialog.setCancelable(false);
        final EditText text = (EditText) dialog.findViewById(R.id.wifi_edit);
        final EditText text1 = (EditText) dialog.findViewById(R.id.wifi_edit1);
        dialog.show();

        Button cancel_button = (Button) dialog.findViewById(R.id.wifi_cancel_bt);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button accept_button = (Button) dialog.findViewById(R.id.wifi_connect_bt);

        accept_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "accept_button");
                if (text != null && text1 != null && text1.getText().length() >= 8) {
                    dialog.dismiss();
                    WifiUtil1.createHotSpot(wifiManager, WifiUtil.createHotSpotConfig(text.getText().toString(), text1.getText().toString()));
                }
            }
        });
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure(String msg) {

    }
}
