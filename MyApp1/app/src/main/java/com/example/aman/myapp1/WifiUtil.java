package com.example.aman.myapp1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by aman on 10/6/16.
 */
public class WifiUtil {
    private static final String TAG = "WifiActivity";

    private Context mContext;
    private WifiManager mWifiManager;
    private WifiReceiver mWifiReceiver;

    public WifiUtil(Context context, WifiScanListener wifiScanListener) {

        try {
            mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            mWifiReceiver = new WifiReceiver(wifiScanListener);
        } catch (Exception e) {
            Log.e(TAG, "Error in Wifi Constructor", e);
        }
    }

    public boolean isWifiConnected(Context context,String ssid){
        try {
            if (mWifiManager == null) {
                mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            }
            WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
            if (ssid == wifiInfo.getSSID() || wifiInfo.getSSID().equals(ssid)) {
                return true;
            }
        }catch (Exception e) {
            Log.e(TAG, "Error in comparing wifi's ", e);
            return false;
        }
        return false;
    }

    /**
     * Before use this method register WifiReceiver BroadCast Receiver in your activity and
     * Don't forget to unRegister WifiReceiver BroadCast Receiver in onPause of your activity.
     *
     * @param ssid
     * @param pwd
     */
    public void connectToWifi(List<ScanResult> scanResults, String ssid, String pwd, final ActionListener actionListener) {
        if (actionListener != null) {
            try {
                connectWifi(mWifiManager, makeWifiConfig(getWifiFromList(scanResults, ssid), pwd), actionListener);
            } catch (Exception e) {
                Log.e(TAG, "Error in connecting to wifi ", e);
            }
        } else {
            try {
                connectWifi(mWifiManager, makeWifiConfig(getWifiFromList(scanResults, ssid), pwd), new ActionListener() {
                    @Override
                    public void onSuccess() {
                        Log.i(TAG, "Wifi connected successfully");
                    }

                    @Override
                    public void onFailure(String msg) {
                        Log.i(TAG, msg);
                    }
                });
            } catch (Exception e) {
                Log.e(TAG, "Error in connecting to wifi ", e);
            }
        }
    }

    public void regWifiReceiver() {
        if (mContext != null && mWifiReceiver != null && mWifiManager != null) {
            try {
                if (!mWifiManager.isWifiEnabled()) {
                    mWifiManager.setWifiEnabled(true);
                }
                mContext.registerReceiver(mWifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
                mWifiManager.startScan();
            } catch (Exception e) {
                Log.e(TAG, "Error in regWifi receiver", e);
            }
        }
    }

    public void unRegWifiReceiver() {
        if (mContext != null && mWifiReceiver != null) {
            try {
                mContext.unregisterReceiver(mWifiReceiver);
            } catch (Exception e) {
                Log.e(TAG, "Error in regWifi receiver", e);
            }
        }
    }

    public interface ActionListener {
        void onSuccess();

        void onFailure(String msg);

    }

    public interface WifiScanListener {
        void onScanResults(List<ScanResult> scanResults);
    }


    //private methods and classes
    private static ScanResult getWifiFromList(List<ScanResult> scanResults, String ssid) {
        for (ScanResult scanResult : scanResults) {
            if (scanResult.SSID.equals(ssid)) {
                return scanResult;
            }
        }
        return null;
    }

    private static boolean isWifiAvailable(List<ScanResult> scanResults, String ssid) {
        if (scanResults == null || ssid == null) return false;

        for (ScanResult scanResult : scanResults) {
            if (scanResult.SSID.equals(ssid)) {
                return true;
            }
        }
        return false;
    }

    private static WifiConfiguration makeWifiConfig(ScanResult scanResult, String pwd) {
        if (scanResult == null || pwd == null)
            return null;

        Log.i("WifiActivity", "makeWifiConfig");
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            config.SSID = scanResult.SSID;
        } else {
            config.SSID = "\"" + scanResult.SSID + "\"";
        }

        if (scanResult.capabilities.contains("WEP")) {
            Log.i("WifiActivity", "makeWifiConfig  WEP");
            config.hiddenSSID = true;
            config.wepKeys[0] = pwd;
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            config.wepTxKeyIndex = 0;

        } else if (scanResult.capabilities.contains("WPA") || scanResult.capabilities.contains("WPA2") || scanResult.capabilities.contains("WPA/WPA2 PSK")) {
            Log.i("WifiActivity", "makeWifiConfig  PSK");
            config.preSharedKey = "\"" + pwd + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            config.status = WifiConfiguration.Status.ENABLED;
        } else {
            Log.i("WifiActivity", "makeWifiConfig  NONE");
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        }
        return config;
    }

    private static void connectWifi(WifiManager wifiManager, WifiConfiguration config, ActionListener actionListener) {
        Log.i("WifiActivity", "connectWifi");
        try {
            if (!wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(true);
            }
            int wcgID = wifiManager.addNetwork(config);
            if (wifiManager.enableNetwork(wcgID, true) && wifiManager.saveConfiguration() && wifiManager.reconnect()) {
                actionListener.onSuccess();
            } else {
                actionListener.onFailure("Failed to connect with wifi");
            }
        } catch (Exception e) {
            actionListener.onFailure("Error in connecting wifi " + e.getMessage());
            Log.e(TAG, "Error in connecting to wifi", e);
        }
    }

    /**
     * Register this receiver with this intent filter
     * registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
     */
    private class WifiReceiver extends BroadcastReceiver {
        private WifiScanListener scanListener;

        public WifiReceiver(WifiScanListener scanListener) {
            this.scanListener = scanListener;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("WifiUtil", "WifiReceiver onReceive");
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (wifiManager != null) {
                try {
                    List<ScanResult> scanResults = wifiManager.getScanResults();
                    scanListener.onScanResults(scanResults);

                } catch (Exception e) {
                }
            }
        }
    }


    //create wifi hotspot methods
    /**
     * Method to Change SSID and Password of Device Access Point
     *
     * @param SSID     a new SSID of your Access Point
     * @param passWord a new password you want for your Access Point password length should be greater or equal to 8 characters
     */
    public static WifiConfiguration createHotSpotConfig(String SSID, String passWord) {
        if (SSID == null && passWord == null) return null;
        Log.i(TAG, "createHotSpotConfig");
        WifiConfiguration netConfig = new WifiConfiguration();
        netConfig.allowedAuthAlgorithms.clear();
        netConfig.allowedGroupCiphers.clear();
        netConfig.allowedKeyManagement.clear();
        netConfig.allowedPairwiseCiphers.clear();
        netConfig.allowedProtocols.clear();

        try {
            netConfig.SSID = SSID;
            netConfig.preSharedKey = passWord;
            netConfig.hiddenSSID = true;
            netConfig.status = WifiConfiguration.Status.ENABLED;
            netConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            netConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            netConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            netConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            netConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            netConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            netConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        } catch (Exception e) {
            Log.e(TAG, "createHotSpotConfig", e);
        }

        return netConfig;
    }


    //private methods and classes

    /**
     * Method to turn ON/OFF a  Access Point
     *
     * @param mWifiManager
     * @param wifiConfig
     */
    public static void createHotSpot(WifiManager mWifiManager, WifiConfiguration wifiConfig) {
        try {
            Log.i(TAG, "createHotSpot");
            if (mWifiManager.isWifiEnabled()) {
                mWifiManager.setWifiEnabled(false);
            }
            Method m = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            Log.i(TAG, "createHotSpot invoke" + m.invoke(mWifiManager, wifiConfig, true));

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Error in creating HotSpot", e);
        }
    }
}
