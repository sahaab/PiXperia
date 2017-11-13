package com.android.launcher3;

/**
 * Created by sahaa_000 on 2017-11-13.
 */

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Permission;



public class HomeScreenLockManager {
    private static Method sGoToSleepMethod;
    private static HomeScreenLockManager sInstance;
    private static String sPowerPermission;
    private final ComponentName mAdminComponentName;
    private final Context mContext;
    private final DevicePolicyManager mDevicePolicyManager;
    private final PowerManager mPowerManager;

    static {
        initPowerManagerGoToSleep();
    }

    public static synchronized HomeScreenLockManager getInstance(Context context) {
        HomeScreenLockManager homeScreenLockManager;
        synchronized (HomeScreenLockManager.class) {
            if (sInstance == null) {
                sInstance = new HomeScreenLockManager(context.getApplicationContext());
            }
            homeScreenLockManager = sInstance;
        }
        return homeScreenLockManager;
    }

    public HomeScreenLockManager(Context context) {
        this.mContext = context.getApplicationContext();
        this.mAdminComponentName = getAdminComponentName(context);
        this.mPowerManager = ((PowerManager) this.mContext.getSystemService(Context.POWER_SERVICE));
        this.mDevicePolicyManager  = ((DevicePolicyManager) this.mContext.getSystemService(Context.DEVICE_POLICY_SERVICE));
        initPowerManagerGoToSleep();
    }

    private boolean isActiveAdmin() {
        return this.mDevicePolicyManager.isAdminActive(this.mAdminComponentName);
    }

    public boolean lockScreen() {
        if (hasPowerManagerSleepPermission(this.mContext)) {
            goToSleep();
            return true;
        } else if (!isActiveAdmin()) {
            return false;
        } else {
            this.mDevicePolicyManager.lockNow();
            return true;
        }
    }

    public static ComponentName getAdminComponentName(Context context) {
        return new ComponentName(context, HomeDeviceAdminReceiver.class);
    }

    public static void disableDoubleTapToSleep(Context context) {
        //UserSettings.writeValue(context, Setting.DOUBLE_TAP_TO_SLEEP, Source.USER, Boolean.valueOf(false));
    }

    private static boolean isActiveAdmin(Context context) {
        DevicePolicyManager dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        return dpm != null && dpm.isAdminActive(getAdminComponentName(context));
    }

    public static boolean hasPowerManagerSleepPermission(Context context) {
        return sPowerPermission != null && ContextCompat.checkSelfPermission(context, sPowerPermission) == 0;
    }

    public static boolean isScreenLockSupported(Context context) {
        return hasPowerManagerSleepPermission(context) || isActiveAdmin(context);
    }

    private void goToSleep() {
        //if (sGoToSleepMethod != null) {
            try {
                sGoToSleepMethod.invoke(this.mPowerManager, new Object[]{Long.valueOf(SystemClock.uptimeMillis())});
                return;
            } catch (IllegalAccessException e) {Log.d("Power Manager", "Failed, Illegal Access");
            } catch (InvocationTargetException e2) {Log.d("Power Manager", "Failed, Invocation Targer");
            } catch (SecurityException e3) {Log.d("Power Manager", "Failed, Security");
            }
        //}
        //PowerManager pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        //pm.goToSleep(SystemClock.uptimeMillis() + 1);

    }

    private static void initPowerManagerGoToSleep() {
        try {
            sPowerPermission = (String) Permission.class.getDeclaredField("DEVICE_POWER").get(null);
            sGoToSleepMethod = PowerManager.class.getMethod("goToSleep", new Class[]{Long.TYPE});
            Log.d("Power Manager", "Initialized");
            return;
        } catch (IllegalAccessException e) { Log.d("Power Manager", "Failed, Illegal Access");
        } catch (NoSuchFieldException e2) {Log.d("Power Manager", "Failed, No Field");
        } catch (NoSuchMethodException e3) {Log.d("Power Manager", "Failed, No Method");
        }
        sPowerPermission = null;
        sGoToSleepMethod = null;
    }
}
