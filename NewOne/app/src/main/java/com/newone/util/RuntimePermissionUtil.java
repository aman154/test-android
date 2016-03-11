package com.newone.util;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by aman on 2/3/16.
 */
public class RuntimePermissionUtil {

    /**
     * Check that all given permissions have been granted by verifying that each entry in the
     * given array is of the value {@link PackageManager#PERMISSION_GRANTED}.
     *
     * Activity onRequestPermissionsResult(int, String[], int[])
     */
    public static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if(grantResults.length < 1){
            return false;
        }
        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    public static boolean checkRuntimeLocationPermission(Context context) {
        boolean locationRuntimePermission = false;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            locationRuntimePermission = false;
        }else {
            locationRuntimePermission = true;
        }

        return locationRuntimePermission;
    }

    public static void requestLocationPermission(final Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                || ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.ACCESS_COARSE_LOCATION)) {
            // Display a Dialog with an explanation and a button to trigger the request.
            runtimePermissionDialog(activity, null, "This app needs location permissions", "ok", new RuntimePermissionDialogClick() {
                @Override
                public void onClick() {
                    ActivityCompat.requestPermissions(activity, Constant.PERMISSIONS_LOCATION, Constant.REQUEST_LOCATION);
                }
            });
        }else {
            // Contact permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions(activity, Constant.PERMISSIONS_LOCATION, Constant.REQUEST_LOCATION);
        }
    }

    public static AlertDialog runtimePermissionDialog(Activity activity, String title, String message, String positive_button_title, final RuntimePermissionDialogClick runtimePermissionDialogClick) {
        AlertDialog dialog = null;
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            if(message != null) {
                builder.setMessage(message);
            }
            if(title != null){
                builder.setTitle(title);
            }
            builder.setPositiveButton(positive_button_title, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    runtimePermissionDialogClick.onClick();
                    dialog.dismiss();
                }
            });
            dialog = builder.create();
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dialog;
    }

    private interface RuntimePermissionDialogClick {
        void onClick();
    }
}
