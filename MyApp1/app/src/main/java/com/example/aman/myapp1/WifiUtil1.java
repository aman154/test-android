package com.example.aman.myapp1;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by aman on 16/6/16.
 */
public class WifiUtil1 {
    private static final String TAG = "WifiUtil1";

    private static boolean enableWifi(Context context) {
        boolean enabled = false;
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (wifiManager != null) {
                if (!wifiManager.isWifiEnabled()) {
                    Log.i(TAG, "Wifi is not enabled");
                    enabled = wifiManager.setWifiEnabled(true);
                    Log.i(TAG, "Wifi enabled:" + enabled);
                }
            }
        } catch (Exception e) {
        }
        return enabled;
    }


    /**
     * @param ssid
     * @param pwd
     */
    public static void connectToWifi(WifiManager wifiManager, List<ScanResult> scanResults, String ssid, String pwd, ActionListener actionListener) {
        if (actionListener != null) {
            try {
                connectWifi(wifiManager, makeWifiConfig(getWifiFromList(scanResults, ssid), pwd), actionListener);
            } catch (Exception e) {
                Log.e(TAG, "Error in connecting to wifi ", e);
            }
        } else {
            try {
                connectWifi(wifiManager, makeWifiConfig(getWifiFromList(scanResults, ssid), pwd), new ActionListener() {
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

    public interface ActionListener {
        void onSuccess();

        void onFailure(String msg);

    }


    //private methods
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

        Log.i(TAG, "makeWifiConfig");
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
            Log.i(TAG, "makeWifiConfig  WEP");
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
            Log.i(TAG, "makeWifiConfig  PSK");
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
            Log.i(TAG, "makeWifiConfig  NONE");
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        }
        return config;
    }

    private static void connectWifi(WifiManager wifiManager, WifiConfiguration config, ActionListener actionListener) {
        Log.i(TAG, "connectWifi");
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


    //create wifi hotspot methods

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
            Log.e(TAG, "createHotSpot", e);
        }
    }

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
}
