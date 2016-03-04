package com.newone.util;

import android.app.ProgressDialog;
import android.content.Context;

import com.newone.R;

/**
 * Created by aman on 19/11/15.
 */
public class MyProgressDialog extends ProgressDialog {
    public MyProgressDialog(Context context) {
        super(context, R.style.progress_dialog_style);
    }

    public MyProgressDialog(Context context, int theme) {
        super(context, theme);
    }
}
