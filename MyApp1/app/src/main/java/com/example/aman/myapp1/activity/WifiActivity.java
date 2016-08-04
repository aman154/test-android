package com.example.aman.myapp1.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aman.myapp1.R;
import com.example.aman.myapp1.WifiUtil;

import java.util.ArrayList;
import java.util.List;

public class WifiActivity extends AppCompatActivity {
    private static final String TAG = "WifiActivity";
    private static final int REQ_CODE = 1221;
    private WifiManager wifiManager;
    private WifiReceiver wifiReceiver;
    private ListView listView;
    private List<ScanResult> scanResultList = new ArrayList<>();
    private WifiListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.wifi_list);
        adapter = new WifiListAdapter(this, scanResultList);
        listView.setAdapter(adapter);

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_SETTINGS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CHANGE_WIFI_STATE) && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_SETTINGS)) {
                    Toast.makeText(this, "Location permissions required", Toast.LENGTH_LONG).show();
                }
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.WRITE_SETTINGS}, REQ_CODE);
            } else {
                Log.i(TAG, "onCreate location permissions else");
                startWifiScan();
            }
        } else {
            startWifiScan();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (adapter != null) {
                    ScanResult scanResult = adapter.getItem(position);
                    createDialog(WifiActivity.this, scanResult);
                }
            }
        });

        findViewById(R.id.start_hotspot_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog1(WifiActivity.this);
            }
        });
    }

    private void createDialog(Context context, final ScanResult scanResult) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.wifi_dialog);
        dialog.setCancelable(false);
        final EditText text = (EditText) dialog.findViewById(R.id.wifi_edit);
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
                dialog.dismiss();
                if (text != null && scanResult != null) {
                    Log.i(TAG, "accept_button1");
                    Log.i(TAG, "text -" + text.getText().toString());
                    /*WifiUtil.connectWifi(wifiManager, WifiUtil.makeWifiConfig(scanResult, text.getText().toString().trim()), new WifiUtil.ActionListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(WifiActivity.this,"Connected to wifi",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure() {
                            Toast.makeText(WifiActivity.this,"Problem in Connecting to wifi",Toast.LENGTH_SHORT).show();
                        }
                    });*/
                }
            }
        });
    }

    private void createDialog1(Context context) {
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
                    WifiUtil.createHotSpot(wifiManager, WifiUtil.createHotSpotConfig(text.getText().toString(), text1.getText().toString()));
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "WifiActivity onResume");
        if (wifiReceiver != null) {
            registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQ_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "onRequestPermissionsResult location permissions");
                    startWifiScan();
                } else {
                    Toast.makeText(this, "Location permissions not granted", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "WifiActivity onPause");
        if (wifiReceiver != null) {
            Log.i(TAG, "WifiActivity onPause1");
            unregisterReceiver(wifiReceiver);
        }
    }

    private void startWifiScan() {
        try {
            wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

            if (!wifiManager.isWifiEnabled()) {
                Toast.makeText(this, "wifi is not enable", Toast.LENGTH_LONG).show();
                wifiManager.setWifiEnabled(true);
            }
            wifiReceiver = new WifiReceiver();
            registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
            wifiManager.startScan();
        } catch (Exception e) {
        }
    }

    private class WifiReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "WifiReceiver onReceive");
            scanResultList = wifiManager.getScanResults();
            if (scanResultList != null) {
                Log.i(TAG, "WifiReceiver  scanResultList size - " + scanResultList.size());
            }

           /* if (scanResultList != null && scanResultList.size() >0){
                for (int i=0; i<scanResultList.size()-1; i++){
                    ScanResult scanResult = scanResultList.get(i);
                    Log.i(TAG,"onReceive scanResult - "+scanResult.toString());
                    //WifiConfiguration wifiConfiguration = wifiManager.getMatchingWifiConfig(scanResult);
                    try {
                        Class c;
                        c = Class.forName("android.net.wifi.WifiManager");
                        Method m = c.getMethod("getMatchingWifiConfig", ScanResult.class);
                        WifiConfiguration wifiConfiguration = (WifiConfiguration) m.invoke(wifiManager, scanResult);
                        Log.i(TAG,"onReceive wifiConfiguration - "+wifiConfiguration.toString());
                        Log.i(TAG,"onReceive wifiConfiguration preSharedKey - "+wifiConfiguration.preSharedKey);
                        Log.i(TAG,"onReceive wifiConfiguration password - "+wifiConfiguration.enterpriseConfig.getPassword());
                    }catch (Exception e){
                        Log.e(TAG,"onReceive",e);
                        e.printStackTrace();
                    }
                }
            }*/

            if (adapter == null) {
                adapter = new WifiListAdapter(WifiActivity.this, scanResultList);
                listView.setAdapter(adapter);
            } else {
                adapter.setmScanResultList(scanResultList);
            }
        }
    }

    private class WifiListAdapter extends BaseAdapter {
        private Context mContext;
        private List<ScanResult> mScanResultList;
        private LayoutInflater layoutInflater;

        public WifiListAdapter(Context context, List<ScanResult> scanResultList) {
            Log.i(TAG, "WifiListAdapter");
            mContext = context;
            mScanResultList = scanResultList;

            try {
                layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            } catch (Exception e) {
            }
        }

        public void setmScanResultList(List<ScanResult> scanResultList) {
            Log.i(TAG, "WifiListAdapter  setmScanResultList");
            this.mScanResultList = scanResultList;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            if (mScanResultList != null && mScanResultList.size() > 0) {
                Log.i(TAG, "getCount mScanResultList size -" + mScanResultList.size());
                return mScanResultList.size();
            } else {
                return 0;
            }
        }

        @Override
        public ScanResult getItem(int position) {
            if (mScanResultList != null) {
                return mScanResultList.get(position);
            } else {
                return null;
            }
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            ScanResult scanResult = null;
            if (convertView == null) {
                try {
                    viewHolder = new ViewHolder();
                    convertView = layoutInflater.inflate(R.layout.wifi_list_item, parent, false);
                    convertView.setTag(viewHolder);
                    viewHolder.wifi_list_item_text = (TextView) convertView.findViewById(R.id.wifi_list_item_text);
                    viewHolder.wifi_list_item_text1 = (TextView) convertView.findViewById(R.id.wifi_list_item_text1);

                } catch (Exception e) {
                }
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            try {
                scanResult = getItem(position);
                if (viewHolder != null && scanResult != null) {
                    Log.i(TAG, "getView scanResult -" + scanResult.toString());
                    if (viewHolder.wifi_list_item_text != null) {
                        viewHolder.wifi_list_item_text.setText(scanResult.SSID);
                    }
                }

            } catch (Exception e) {
            }
            return convertView;
        }

        private class ViewHolder {
            TextView wifi_list_item_text, wifi_list_item_text1;
        }
    }

}
