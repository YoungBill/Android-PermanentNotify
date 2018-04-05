package com.android.remoteviews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class PermanentNotifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permanentnotify);

        PermanentNotifyManager.registerToolbarBroadcastReceiver(getApplicationContext());
        PermanentNotifyManager.createPermanentNotify(getApplicationContext());
    }
}
