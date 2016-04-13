package com.example.aman.myapp1.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.View;

import com.example.aman.myapp1.R;

import java.util.ArrayList;
import java.util.List;

public class TestActionSend extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_action_send);



        findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*List<Intent> targetedShareIntents = new ArrayList<Intent>();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "some url link...");
                sendIntent.setType("text/plain");
                PackageManager pm = getPackageManager();
                List<ResolveInfo> activityList = pm.queryIntentActivities(sendIntent, 0);
                for (final ResolveInfo app : activityList) {
                    String packageName = app.activityInfo.packageName;

                    if (packageName.equals("com.whatsapp") || packageName.equals("com.facebook.katana")) {
                    Intent targetedShareIntent = new Intent(android.content.Intent.ACTION_SEND);
                    targetedShareIntent.setType("text/plain");
                    targetedShareIntent.putExtra(Intent.EXTRA_TEXT, "some url link...");
                    targetedShareIntent.setPackage(packageName);
                        targetedShareIntents.add(targetedShareIntent);
                    }
                }
                Intent intent = Intent.createChooser(sendIntent, "Share url");
                intent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[targetedShareIntents.size()]));
                startActivity(intent);*/
            }
        });
    }

    public void onShareClick(View v) {
        Intent emailIntent = new Intent();
        emailIntent.setAction(Intent.ACTION_SEND);
        // Native email client doesn't currently support HTML, but it doesn't hurt to try in case they fix it
        emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml("share_email_native"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "share_email_subject");
        emailIntent.setType("message/rfc822");

        PackageManager pm = getPackageManager();
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");


        Intent openInChooser = Intent.createChooser(emailIntent, "share_chooser_text");

        List<ResolveInfo> resInfo = pm.queryIntentActivities(sendIntent, 0);
        List<LabeledIntent> intentList = new ArrayList<LabeledIntent>();
        for (int i = 0; i < resInfo.size(); i++) {
            // Extract the label, append it, and repackage it in a LabeledIntent
            ResolveInfo ri = resInfo.get(i);
            String packageName = ri.activityInfo.packageName;
            if(packageName.contains("android.email")) {
                emailIntent.setPackage(packageName);
            } else if(packageName.contains("twitter") || packageName.contains("facebook") || packageName.contains("mms") || packageName.contains("android.gm")) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(packageName, ri.activityInfo.name));
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                if(packageName.contains("twitter")) {
                    intent.putExtra(Intent.EXTRA_TEXT, "share_twitter");
                } else if(packageName.contains("facebook")) {
                    // Warning: Facebook IGNORES our text. They say "These fields are intended for users to express themselves. Pre-filling these fields erodes the authenticity of the user voice."
                    // One workaround is to use the Facebook SDK to post, but that doesn't allow the user to choose how they want to share. We can also make a custom landing page, and the link
                    // will show the <meta content ="..."> text from that page with our link in Facebook.
                    intent.putExtra(Intent.EXTRA_TEXT, "share_facebook");
                } else if(packageName.contains("mms")) {
                    intent.putExtra(Intent.EXTRA_TEXT, "share_sms");
                } else if(packageName.contains("android.gm")) { // If Gmail shows up twice, try removing this else-if clause and the reference to "android.gm" above
                    intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml("share_email_gmail"));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "share_email_subject");
                    intent.setType("message/rfc822");
                }

                intentList.add(new LabeledIntent(intent, packageName, ri.loadLabel(pm), ri.icon));
            }
        }

        // convert intentList to array
        LabeledIntent[] extraIntents = intentList.toArray( new LabeledIntent[ intentList.size() ]);

        openInChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
        startActivity(openInChooser);
    }


}
