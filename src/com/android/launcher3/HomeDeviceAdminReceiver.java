package com.android.launcher3;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by sahaa_000 on 2017-11-13.
 */

public class HomeDeviceAdminReceiver extends DeviceAdminReceiver {
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
        Log.d("Home Device Admin", "Disabled");
        HomeScreenLockManager.disableDoubleTapToSleep(context);
    }
}
